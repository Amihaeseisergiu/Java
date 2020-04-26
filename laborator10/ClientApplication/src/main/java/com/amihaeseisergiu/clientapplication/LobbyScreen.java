package com.amihaeseisergiu.clientapplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LobbyScreen {
    
    Scene scene;
    Stage stage;
    PrintWriter out;
    BufferedReader in;
    Thread commandsReader;
    
    public LobbyScreen(Stage stage, BufferedReader in, PrintWriter out, int lobbyId, boolean joined)
    {
        this.stage = stage;
        this.in = in;
        this.out = out;
        AtomicInteger playerId = new AtomicInteger();
        
        BorderPane pane = new BorderPane();
        pane.setPadding(new Insets(5,5,5,5));
        
        Label topLabel = new Label("Lobby " + lobbyId);
        topLabel.setStyle("-fx-font: 20 arial;");
        HBox topHBox = new HBox(topLabel);
        topHBox.setSpacing(10);
        topHBox.setPadding(new Insets(5,5,5,5));
        topHBox.setAlignment(Pos.CENTER);
        
        ScrollPane centerScrollPane = new ScrollPane();
        VBox centerScrollPaneVBox = new VBox();
        centerScrollPane.setContent(centerScrollPaneVBox);
        centerScrollPane.setPadding(new Insets(5,5,5,5));
        centerScrollPane.setFitToWidth(true); 
        centerScrollPaneVBox.setSpacing(10);
        centerScrollPaneVBox.setPadding(new Insets(5,5,5,5));
        centerScrollPaneVBox.setAlignment(Pos.TOP_CENTER);
        
        Button leave = new Button("Leave");
        Button startGame = new Button("Start Game");
        startGame.setVisible(false);
        BorderPane bottomHBox = new BorderPane();
        bottomHBox.setLeft(leave);
        bottomHBox.setRight(startGame);
        bottomHBox.setPadding(new Insets(5,5,5,5));
        
        pane.setTop(topHBox);
        pane.setCenter(centerScrollPane);
        pane.setBottom(bottomHBox);
        scene = new Scene(pane, 800, 600);
        
        if(joined)
        {
            playerId.set(1);
            startGame.setVisible(true);
            for(int i = 0; i < 2; i++)
            {
                Label player = new Label("Player " + (i+1));
                if(i == 1)
                    player = new Label("Player " + (i+1) + " (You)");
                HBox playerHBox = new HBox(player);
                playerHBox.setSpacing(10);
                playerHBox.setPadding(new Insets(5,5,5,5));
                playerHBox.setAlignment(Pos.CENTER);
                playerHBox.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
                + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
                + "-fx-border-radius: 5;" + "-fx-border-color: black;");
                centerScrollPaneVBox.getChildren().add(playerHBox);
            }
        }
        else
        {
            playerId.set(0);
            Label player1 = new Label("Player 1 (You)");
            HBox player1HBox = new HBox(player1);
            player1HBox.setSpacing(10);
            player1HBox.setPadding(new Insets(5,5,5,5));
            player1HBox.setAlignment(Pos.CENTER);
            player1HBox.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
            + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
            + "-fx-border-radius: 5;" + "-fx-border-color: black;");
            centerScrollPaneVBox.getChildren().add(player1HBox);
        }
        
        commandsReader = new Thread(() -> {
            while(true)
            {
                try {
                    String response = in.readLine();
                    System.out.println(response + " from lobby");
                    
                    String[] split = null;
                    if(response != null)
                        split = response.trim().split("\\s+");
                    
                    if(split != null && split[0].equals("userjoined"))
                    {
                        Platform.runLater(() -> {
                            startGame.setVisible(true);
                            Label player2 = new Label("Player 2");
                            HBox player2HBox = new HBox(player2);
                            player2HBox.setSpacing(10);
                            player2HBox.setPadding(new Insets(5,5,5,5));
                            player2HBox.setAlignment(Pos.CENTER);
                            player2HBox.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
                            + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
                            + "-fx-border-radius: 5;" + "-fx-border-color: black;");
                            centerScrollPaneVBox.getChildren().add(player2HBox);
                        });
                    }
                    else if(split != null && split[0].equals("userleft"))
                    {
                        AtomicInteger index = new AtomicInteger(Integer.valueOf(split[1]));
                        Platform.runLater(() -> {
                            startGame.setVisible(false);
                            centerScrollPaneVBox.getChildren().remove(index.get());
                            
                            for(int i = 0; i < centerScrollPaneVBox.getChildren().size(); i++)
                            {
                                playerId.set(i);
                                ((Label)((HBox) centerScrollPaneVBox.getChildren().get(i)).getChildren().get(0)).setText("Player " + (i+1) + " (You)");
                            }
                        });
                    }
                    else if(split != null && split[0].equals("startgame"))
                    {
                        Platform.runLater(() -> {
                            GameScreen gs = new GameScreen(stage, in, out, playerId.get());
                        });
                        return;
                    }
                } catch (IOException ex) {
                    Logger.getLogger(StartScreen.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
        commandsReader.start();
        
        leave.setOnAction(e -> {
            commandsReader.stop(); //imi place sa traiesc periculos
            sendToServer("leave");
            StartScreen scrn = new StartScreen(stage, in, out);
        });
        
        startGame.setOnAction(e -> {
            sendToServer("startgame");
        });
        
        stage.setOnCloseRequest(e -> {
            sendToServer("leave");
            sendToServer("exit");
            System.exit(0);
        });
        
        stage.setScene(scene);
        stage.show();
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
