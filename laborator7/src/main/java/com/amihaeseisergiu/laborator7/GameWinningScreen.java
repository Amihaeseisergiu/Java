package com.amihaeseisergiu.laborator7;

import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GameWinningScreen {
    
    Game game;
    Stage stage;
    Scene scene;
    Label winningLabel;
    List<VBox> winnersVBoxes = new ArrayList<>();
    
    public GameWinningScreen(Game game, Stage stage, List<Player> players)
    {
        this.game = game;
        this.stage = stage;
        
        BorderPane pane = new BorderPane();
        HBox topHBox = new HBox();
        topHBox.setSpacing(10);
        topHBox.setPadding(new Insets(5,5,5,5));
        topHBox.setAlignment(Pos.TOP_CENTER);
        winningLabel = new Label();
        winningLabel.setStyle("-fx-font: 20 arial;");
        topHBox.getChildren().add(winningLabel);
        pane.setTop(topHBox);
        
        if(players.size() > 1)
            winningLabel.setText("It's a tie.");
        else
            winningLabel.setText("The winner is...");
        for(Player p : players)
        {
            VBox winnerVBox = new VBox();
            winnerVBox.setSpacing(10);
            winnerVBox.setPadding(new Insets(5,5,5,5));
            winnerVBox.setAlignment(Pos.TOP_CENTER);

            Label winnerLabel = new Label(p.getName() + " with a score of " + p.getLongestProgression().size());
            winnerLabel.setStyle("-fx-font: 18 arial;");
            Label tokensLabel = new Label("Tokens:");
            Label longProgLabel = new Label("Longest Progression:");
            List<Label> tokensLabels = new ArrayList<>();
            List<Label> longProgLabels = new ArrayList<>();
            HBox tokensHBox = new HBox();
            HBox longProgHBox = new HBox();
            ScrollPane tokensScrollPane = new ScrollPane();
            tokensScrollPane.setContent(tokensHBox);
            tokensScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            tokensScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            ScrollPane longProgScrollPane = new ScrollPane();
            longProgScrollPane.setContent(longProgHBox);
            longProgScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            longProgScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

            for(Token t : p.getTokens())
            {
                Label l = new Label(String.valueOf(t.isBlank() ? "?" : t.getNumber()));
                l.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
                + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
                + "-fx-border-radius: 5;" + "-fx-border-color: black;");
                tokensLabels.add(l);
            }
            tokensHBox.getChildren().addAll(tokensLabels);
            
            for(Token t : p.getLongestProgression())
            {
                Label l = new Label(String.valueOf(t.isBlank() ? "?" : t.getNumber()));
                l.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
                + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
                + "-fx-border-radius: 5;" + "-fx-border-color: black;");
                longProgLabels.add(l);
            }
            longProgHBox.getChildren().addAll(longProgLabels);

            winnerVBox.getChildren().addAll(winnerLabel, tokensLabel, tokensScrollPane, longProgLabel, longProgScrollPane);
            winnerVBox.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
                + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
                + "-fx-border-radius: 5;" + "-fx-border-color: black;");
            winnersVBoxes.add(winnerVBox);
        }
        
        VBox centerVBox = new VBox();
        centerVBox.setSpacing(10);
        centerVBox.setPadding(new Insets(5,5,5,5));
        centerVBox.setAlignment(Pos.TOP_CENTER);
        ScrollPane centerScrollPane = new ScrollPane();
        VBox winnersVBox = new VBox();
        winnersVBox.setSpacing(10);
        winnersVBox.setPadding(new Insets(5,5,5,5));
        winnersVBox.setAlignment(Pos.TOP_CENTER);
        winnersVBox.getChildren().addAll(winnersVBoxes);
        centerScrollPane.setContent(winnersVBox);
        centerScrollPane.setFitToWidth(true);
        centerScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        centerScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        centerVBox.getChildren().addAll(centerScrollPane);
        pane.setCenter(centerVBox);
        
        scene = new Scene(pane, 800, 600);
        stage.setScene(scene);
        stage.show();
    }
}
