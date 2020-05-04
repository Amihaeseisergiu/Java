package com.amihaeseisergiu.clientapplication;

import java.io.BufferedReader;
import java.io.PrintWriter;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class WinnerScreen {
    
    Scene scene;
    Stage stage;
    PrintWriter out;
    BufferedReader in;
    
    public WinnerScreen(Stage stage, BufferedReader in, PrintWriter out, int winner)
    {
        this.stage = stage;
        this.in = in;
        this.out = out;
        
        BorderPane pane = new BorderPane();
        pane.setPadding(new Insets(5,5,5,5));
        
        Label topLabel = new Label("Player " + winner + " wins the game!");
        topLabel.setStyle("-fx-font: 20 arial;");
        HBox topHBox = new HBox(topLabel);
        topHBox.setSpacing(10);
        topHBox.setPadding(new Insets(5,5,5,5));
        topHBox.setAlignment(Pos.CENTER);
        
        Button back = new Button("Back to menu");
        HBox centerHBox = new HBox(back);
        centerHBox.setSpacing(10);
        centerHBox.setPadding(new Insets(5,5,5,5));
        centerHBox.setAlignment(Pos.TOP_CENTER);
        
        pane.setTop(topHBox);
        pane.setCenter(centerHBox);
        
        scene = new Scene(pane, 800, 600);
        stage.setScene(scene);
        stage.show();
        
        back.setOnAction(e -> {
            sendToServer("destroylobby");
            StartScreen scrn = new StartScreen(stage, in, out);
        });
    }
    
    public void sendToServer(String message)
    {
        out.println(message);
    }
}
