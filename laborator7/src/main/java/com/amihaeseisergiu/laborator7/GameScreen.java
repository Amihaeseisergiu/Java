package com.amihaeseisergiu.laborator7;

import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GameScreen {
    
    Scene scene;
    Stage stage;
    Game game;
    List<Label> playerTurnLabels = new ArrayList<>();
    List<Button> availableTokensBtn = new ArrayList<>();
    List<Label> currentTokensLabels = new ArrayList<>();
    List<Label> currentLongProgLabels = new ArrayList<>();
    ScrollPane playerTurnScrollPane;
    ScrollPane availableTokensScrollPane;
    HBox availableTokensHBox;
    HBox currentTokensHBox;
    HBox currentLongProgHBox;
    Label timeLabel = new Label("Time Remaining: 10 s");
    boolean disableAvailableTokens = true;
    int pressedButtonIndex = -1;
    
    public GameScreen(Stage stage, Game game)
    {
        this.stage = stage;
        this.game = game;
        BorderPane pane = new BorderPane();
        pane.setPadding(new Insets(5,5,5,5));
        
        for(Player p : game.getBoard().getPlayers())
        {
            playerTurnLabels.add(new Label(p.getName()));
        }
        VBox playerTurnVBox = new VBox();
        playerTurnVBox.setSpacing(10);
        playerTurnVBox.setPadding(new Insets(5,5,5,5));
        playerTurnVBox.setAlignment(Pos.TOP_CENTER);
        playerTurnScrollPane = new ScrollPane();
        HBox leftHBox = new HBox(playerTurnScrollPane);
        playerTurnVBox.getChildren().addAll(playerTurnLabels);
        playerTurnScrollPane.setContent(playerTurnVBox);
        playerTurnScrollPane.setPrefWidth(getMaxWidth()+80);
        playerTurnScrollPane.setFitToWidth(true);
        playerTurnScrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
        playerTurnScrollPane.setVbarPolicy(ScrollBarPolicy.NEVER);
        pane.setLeft(leftHBox);
        modifyPlayerTurn(0);
        
        availableTokensHBox = new HBox();
        availableTokensHBox.setSpacing(10);
        availableTokensHBox.setPadding(new Insets(5,5,5,5));
        availableTokensHBox.setAlignment(Pos.TOP_CENTER);
        availableTokensScrollPane = new ScrollPane();
        HBox topHBox = new HBox(availableTokensScrollPane);
        topHBox.setAlignment(Pos.CENTER);
        availableTokensHBox.getChildren().addAll(availableTokensBtn);
        availableTokensScrollPane.setContent(availableTokensHBox);
        availableTokensScrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
        availableTokensScrollPane.setVbarPolicy(ScrollBarPolicy.NEVER);
        VBox topVBox = new VBox();
        Label availableTokensLabel = new Label("Available Tokens:");
        availableTokensLabel.setStyle("-fx-font: 20 arial;");
        topVBox.getChildren().addAll(availableTokensLabel, topHBox);
        topVBox.setAlignment(Pos.TOP_CENTER);
        pane.setTop(topVBox);
        modifyAvailableTokens();
        
        currentTokensHBox = new HBox();
        currentTokensHBox.getChildren().addAll(currentTokensLabels);
        currentTokensHBox.setSpacing(10);
        currentTokensHBox.setPadding(new Insets(5,5,5,5));
        currentTokensHBox.setAlignment(Pos.TOP_CENTER);
        currentLongProgHBox = new HBox();
        currentLongProgHBox.getChildren().addAll(currentLongProgLabels);
        ScrollPane currentTokensScrollPane = new ScrollPane();
        currentTokensScrollPane.setContent(currentTokensHBox);
        currentTokensScrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
        currentTokensScrollPane.setVbarPolicy(ScrollBarPolicy.NEVER);
        currentLongProgHBox.setSpacing(10);
        currentLongProgHBox.setPadding(new Insets(5,5,5,5));
        currentLongProgHBox.setAlignment(Pos.TOP_CENTER);
        ScrollPane currentLongProgScrollPane = new ScrollPane();
        currentLongProgScrollPane.setContent(currentLongProgHBox);
        currentLongProgScrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
        currentLongProgScrollPane.setVbarPolicy(ScrollBarPolicy.NEVER);
        Label currentTokensLabel = new Label("Current Tokens:");
        currentTokensLabel.setStyle("-fx-font: 15 arial;");
        Label currentLongProgLabel = new Label("Current Longest Progression:");
        currentLongProgLabel.setStyle("-fx-font: 15 arial;");
        VBox centerFHVBox = new VBox(currentTokensLabel, currentTokensScrollPane);
        centerFHVBox.setAlignment(Pos.TOP_CENTER);
        VBox centerSHVBox = new VBox(currentLongProgLabel, currentLongProgScrollPane);
        centerSHVBox.setAlignment(Pos.TOP_CENTER);
        VBox centerVBox = new VBox(centerFHVBox, centerSHVBox);
        centerVBox.setAlignment(Pos.TOP_CENTER);
        centerVBox.setSpacing(50);
        pane.setCenter(centerVBox);
        
        HBox bottomHBox = new HBox();
        bottomHBox.getChildren().addAll(currentTokensLabels);
        bottomHBox.setSpacing(10);
        bottomHBox.setPadding(new Insets(5,5,5,5));
        bottomHBox.setAlignment(Pos.TOP_CENTER);
        timeLabel.setStyle("-fx-font: 12 arial;");
        bottomHBox.getChildren().addAll(timeLabel);
        pane.setBottom(bottomHBox);

        scene = new Scene(pane, 800, 600);
        stage.setScene(scene);
        stage.show();
        
        stage.setOnCloseRequest(e -> {
            game.setStopped(true);
        });
    }
    
    public void modifyPlayerTurn(int index)
    {
        for(Label l : playerTurnLabels)
        {
            l.setStyle("");
        }
        playerTurnLabels.get(index).setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
            + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
            + "-fx-border-radius: 5;" + "-fx-border-color: black;");
        ensureVisible(playerTurnScrollPane, playerTurnLabels.get(index));
    }
    
    public void modifyAvailableTokens()
    {
        availableTokensHBox.getChildren().clear();
        availableTokensBtn.clear();

        for (Token t : game.getBoard().getTokens()) {
            Button b = t.isBlank() ? new Button("?") : new Button(String.valueOf(t.getNumber()));
            availableTokensBtn.add(b);
            disableAvailableTokensBtns(disableAvailableTokens);
            b.setOnAction(e -> {
                System.out.println(availableTokensBtn.indexOf(b));
                Thread thread = new Thread(() -> {
                    game.getBoard().getPlayers().get(game.getBoard().getPlayerTurn()).wait.set(availableTokensBtn.indexOf(b));
                });
                thread.start();
            });
        }
        availableTokensHBox.getChildren().addAll(availableTokensBtn);
    }
    
    public void modifyCurrentTokens(Player p)
    {
        currentTokensHBox.getChildren().clear();
        currentTokensLabels.clear();
        for(Token t : p.getTokens())
        {
            Label l = new Label(t.isBlank() ? "?" : String.valueOf(t.getNumber()));
            l.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
            + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
            + "-fx-border-radius: 5;" + "-fx-border-color: black;");
            currentTokensLabels.add(l);
        }
        currentTokensHBox.getChildren().addAll(currentTokensLabels);
        
        currentLongProgHBox.getChildren().clear();
        currentLongProgLabels.clear();
        for(Token t : p.getLongestProgression())
        {
            Label l = new Label(t.isBlank() ? "?" : String.valueOf(t.getNumber()));
            l.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
            + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
            + "-fx-border-radius: 5;" + "-fx-border-color: black;");
            currentLongProgLabels.add(l);
        }
        currentLongProgHBox.getChildren().addAll(currentLongProgLabels);
    }
    
    public void disableAvailableTokensBtns(boolean state)
    {
        for(Button b : availableTokensBtn)
        {
            b.setDisable(state);
        }
    }
    
    public void modifyTime(double time)
    {
        timeLabel.setText("Time Remaining: " + time + " s");
    }
    
    private static void ensureVisible(ScrollPane scrollPane, Node node)
    {
        Bounds viewport = scrollPane.getViewportBounds();
        double contentHeight = scrollPane.getContent().localToScene(scrollPane.getContent().getBoundsInLocal()).getHeight();
        double nodeMinY = node.localToScene(node.getBoundsInLocal()).getMinY();
        double nodeMaxY = node.localToScene(node.getBoundsInLocal()).getMaxY();

        double vValueDelta = 0;
        double vValueCurrent = scrollPane.getVvalue();

        if (nodeMaxY < 0) {
            vValueDelta = (nodeMinY - viewport.getHeight()) / contentHeight;
        } else if (nodeMinY > viewport.getHeight()) {
            vValueDelta = (nodeMinY + viewport.getHeight()) / contentHeight;
        }
        scrollPane.setVvalue(vValueCurrent + vValueDelta);
    }
    
    public double getMaxWidth()
    {
        double max = -1;
        for(Label l : playerTurnLabels)
        {
            if(l.getBoundsInLocal().getWidth() > max) max = l.getBoundsInLocal().getWidth();
        }
        return max;
    }
    
    public Scene getScene()
    {
        return this.scene;
    }
}
