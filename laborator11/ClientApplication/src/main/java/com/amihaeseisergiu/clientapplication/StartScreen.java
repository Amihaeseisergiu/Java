package com.amihaeseisergiu.clientapplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class StartScreen {
 
    Scene scene;
    Stage stage;
    PrintWriter out;
    BufferedReader in;
    Thread commandsReader;
    
    public StartScreen(Stage stage, BufferedReader in, PrintWriter out)
    {
        this.stage = stage;
        this.in = in;
        this.out = out;
        
        BorderPane pane = new BorderPane();
        pane.setPadding(new Insets(5,5,5,5));
        
        Label topLabel = new Label("Gomoku");
        topLabel.setStyle("-fx-font: 20 arial;");
        HBox topHBox = new HBox(topLabel);
        topHBox.setSpacing(10);
        topHBox.setPadding(new Insets(5,5,5,5));
        topHBox.setAlignment(Pos.CENTER);
        
        Label nrRooms = new Label("Rooms: 0");
        ScrollPane centerScrollPane = new ScrollPane();
        VBox centerScrollPaneVBox = new VBox();
        centerScrollPane.setContent(centerScrollPaneVBox);
        centerScrollPane.setPadding(new Insets(5,5,5,5));
        centerScrollPane.setFitToWidth(true); 
        centerScrollPaneVBox.getChildren().add(nrRooms);
        centerScrollPaneVBox.setSpacing(10);
        centerScrollPaneVBox.setPadding(new Insets(5,5,5,5));
        centerScrollPaneVBox.setAlignment(Pos.TOP_CENTER);
        
        Button refresh = new Button("Refresh");
        HBox bottomHBox = new HBox(refresh);
        bottomHBox.setSpacing(10);
        bottomHBox.setPadding(new Insets(5,5,5,5));
        bottomHBox.setAlignment(Pos.CENTER);
        
        Button createGame = new Button("Create Game");
        VBox leftVBox = new VBox(createGame);
        leftVBox.setSpacing(10);
        leftVBox.setPadding(new Insets(5,5,5,5));
        leftVBox.setAlignment(Pos.TOP_CENTER);
        createGame.setMaxWidth(Double.MAX_VALUE);
        
        pane.setTop(topHBox);
        pane.setCenter(centerScrollPane);
        pane.setLeft(leftVBox);
        pane.setBottom(bottomHBox);
        scene = new Scene(pane, 800, 600);
        
        createGame.setOnAction(e -> {
            sendToServer("create game");
        });
        
        refresh.setOnAction(e -> {
            sendToServer("refresh");
        });
        
        stage.setOnCloseRequest(e -> {
            sendToServer("exit");
            System.exit(0);
        });
        
        commandsReader = new Thread(() -> {
            sendToServer("refresh");
            while(true)
            {
                try {
                    String response = in.readLine();
                    System.out.println(response + " from start");
                    
                    String[] split = null;
                    if(response != null)
                        split = response.trim().split("\\s+");
                    
                    if(split != null && split[0].equals("lobbylist"))
                    {
                        AtomicInteger rooms = new AtomicInteger(Integer.valueOf(split[1]));
                        Platform.runLater(() -> {
                            centerScrollPaneVBox.getChildren().clear();
                            centerScrollPaneVBox.getChildren().add(nrRooms);
                            nrRooms.setText("Rooms: " + rooms.get());
                            
                            for(int i = 0; i < rooms.get(); i++)
                            {
                                Label roomName = new Label("Lobby " + (i+1));
                                Button joinBtn = new Button("Join");
                                BorderPane roomPane = new BorderPane();
                                roomPane.setLeft(roomName);
                                roomPane.setRight(joinBtn);
                                roomPane.setPadding(new Insets(5,5,5,5));
                                roomPane.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
                                + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
                                + "-fx-border-radius: 5;" + "-fx-border-color: black;");

                                centerScrollPaneVBox.getChildren().add(roomPane);
                                
                                joinBtn.setOnAction(e -> {
                                    sendToServer("join game " + centerScrollPaneVBox.getChildren().indexOf(roomPane));
                                });
                            }
                            
                        });
                    }
                    else if(split != null && split[0].equals("joinedgame"))
                    {
                        AtomicInteger lobbyId = new AtomicInteger(Integer.valueOf(split[1]));
                        Platform.runLater(() -> {
                        
                            LobbyScreen lobby = new LobbyScreen(stage, in, out, lobbyId.get(), true);
                        });
                        return;
                    }
                    else if(split != null && split[0].equals("gamecreated"))
                    {
                        AtomicInteger lobbyId = new AtomicInteger(Integer.valueOf(split[1]));
                        Platform.runLater(() -> {
                        
                            LobbyScreen lobby = new LobbyScreen(stage, in, out, lobbyId.get(), false);
                        });
                        return;
                    }
                } catch (IOException ex) {
                    Logger.getLogger(StartScreen.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
        commandsReader.start();
        
        stage.setScene(scene);
        stage.show();
        refresh.fire();
    }
    
    public void sendToServer(String message)
    {
        out.println(message);
    }
    
    public Scene getScene()
    {
        return this.scene;
    }
}
