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
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class GameScreen {
    
    Scene scene;
    Stage stage;
    PrintWriter out;
    BufferedReader in;
    Thread commandsReader;
    
    public GameScreen(Stage stage, BufferedReader in, PrintWriter out, int playerId)
    {
        this.stage = stage;
        this.in = in;
        this.out = out;
        
        BorderPane pane = new BorderPane();
        pane.setPadding(new Insets(5,5,5,5));
        
        Label topLabel = new Label();
        if(playerId == 0)
            topLabel.setText("Your turn");
        else topLabel.setText("Other player's turn");
        
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
        GridPane gameBoard = new GridPane();

        commandsReader = new Thread(() -> {
            while(true)
            {
                try {
                    String response = in.readLine();
                    System.out.println(response + " from game");
                    
                    String[] split = null;
                    if(response != null)
                        split = response.trim().split("\\s+");
                    
                    if(split != null && split[0].equals("acceptedmove"))
                    {
                        AtomicInteger icopy = new AtomicInteger(Integer.valueOf(split[1]));
                        AtomicInteger jcopy = new AtomicInteger(Integer.valueOf(split[2]));
                        AtomicInteger turn = new AtomicInteger(Integer.valueOf(split[3]));
                        
                        Platform.runLater(() -> {
                            StackPane stackPane = ((StackPane) getNodeFromGridPane(gameBoard, icopy.get(), jcopy.get()));
                            Circle circle = new Circle();
                            circle.setRadius(((Region)stackPane.getChildren().get(0)).getWidth()/2 - 1);
                            if(turn.get() == 1)
                                circle.setFill(Color.BLACK);
                            else circle.setFill(Color.WHITE);
                            circle.setStroke(Color.BLACK);
                            
                            stackPane.getChildren().add(circle);
                            if(turn.get() == playerId)
                                topLabel.setText("Your turn");
                            else topLabel.setText("Other player's turn");
                        });
                    }
                    else if(split != null && split[0].equals("gameended"))
                    {
                        AtomicInteger winner = new AtomicInteger(Integer.valueOf(split[1]));
                        
                        Platform.runLater(() -> {
                            WinnerScreen ws = new WinnerScreen(stage, in, out, winner.get());
                        });
                        return;
                    }
                } catch (IOException ex) {
                    Logger.getLogger(StartScreen.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
        commandsReader.start();

        for (int i = 0; i < 15; i++)
        {
            for (int j = 0; j < 15; j++)
            {

                Region tile = new Region();
                tile.setPrefSize(65, 65);
                tile.setStyle("-fx-background-color: #3b3b3b, white ;\n" +
                "    -fx-background-insets: 0, 2 ;   ");

                StackPane stackPane = new StackPane(tile);
                gameBoard.add(stackPane, j, i);
                GridPane.setHgrow(stackPane, Priority.ALWAYS);

                AtomicInteger icopy = new AtomicInteger(i);
                AtomicInteger jcopy = new AtomicInteger(j);
                
                tile.setOnMouseClicked(e -> {
                    sendToServer("move " + icopy.get() + " " + jcopy.get());
                });
            }
        }
        
        centerScrollPane.setContent(gameBoard);
        pane.setCenter(centerScrollPane);
        pane.setTop(topHBox);
        
        scene = new Scene(pane, 800, 600);
        stage.setScene(scene);
        stage.show();
    }
    
    private Node getNodeFromGridPane(GridPane gridPane, int row, int col) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                return node;
            }
        }
        return null;
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
