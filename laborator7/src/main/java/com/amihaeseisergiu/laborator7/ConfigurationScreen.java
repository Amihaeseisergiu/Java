package com.amihaeseisergiu.laborator7;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ConfigurationScreen {
    
    Scene scene;
    Stage stage;
    Label boardSizeLabel = new Label("Board Size:");
    Label boardBlanksLabel = new Label("Nr Blanks:");
    Label nrPlayers = new Label("Number Of Players: 0");
    TextField boardSizeField = new TextField("20");
    TextField boardBlanksField = new TextField("2");
    Slider boardSizeSlider = new Slider(1, 100, 1);
    Slider boardBlanksSlider = new Slider(0, 100, 1);
    Button randomPlayerBtn = new Button("Random Player");
    Button manualPlayerBtn = new Button("Manual Player");
    Button smartPlayerBtn = new Button("Smart player");
    Button proceedBtn = new Button("Proceed");
    
    List<Player> players = new ArrayList<>();
    
    public ConfigurationScreen(Stage stage)
    {
        this.stage = stage;
        BorderPane pane = new BorderPane();
        pane.setPadding(new Insets(5,5,5,5));
        
        HBox topHBox = new HBox(boardSizeLabel, boardSizeField, boardSizeSlider, boardBlanksLabel, boardBlanksField, boardBlanksSlider);
        topHBox.setSpacing(10);
        topHBox.setPadding(new Insets(5,5,5,5));
        topHBox.setAlignment(Pos.CENTER);
        
        
        HBox bottomHBox = new HBox(proceedBtn);
        bottomHBox.setSpacing(10);
        bottomHBox.setPadding(new Insets(5,5,5,5));
        bottomHBox.setAlignment(Pos.CENTER);
        proceedBtn.setVisible(false);
        
        VBox leftVBox = new VBox(randomPlayerBtn, manualPlayerBtn, smartPlayerBtn);
        leftVBox.setSpacing(10);
        leftVBox.setPadding(new Insets(5,5,5,5));
        leftVBox.setAlignment(Pos.TOP_CENTER);
        randomPlayerBtn.setMaxWidth(Double.MAX_VALUE);
        manualPlayerBtn.setMaxWidth(Double.MAX_VALUE);
        smartPlayerBtn.setMaxWidth(Double.MAX_VALUE);
        
        ScrollPane centerScrollPane = new ScrollPane();
        VBox centerScrollPaneVBox = new VBox();
        centerScrollPane.setContent(centerScrollPaneVBox);
        centerScrollPane.setPadding(new Insets(5,5,5,5));
        centerScrollPane.setFitToWidth(true); 
        centerScrollPaneVBox.getChildren().add(nrPlayers);
        centerScrollPaneVBox.setSpacing(10);
        centerScrollPaneVBox.setPadding(new Insets(5,5,5,5));
        centerScrollPaneVBox.setAlignment(Pos.TOP_CENTER);
        
        pane.setTop(topHBox);
        pane.setLeft(leftVBox);
        pane.setCenter(centerScrollPane);
        pane.setBottom(bottomHBox);
        scene = new Scene(pane, 800, 600);
        
        boardSizeSlider.valueProperty().addListener((ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) -> {
            boardSizeField.setText(String.valueOf(newValue.intValue()));
        });
        
        boardSizeSlider.valueProperty().addListener((obs, oldval, newVal) -> boardSizeSlider.setValue(Math.round(newVal.intValue())));
        
        boardBlanksSlider.valueProperty().addListener((ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) -> {
            boardBlanksField.setText(String.valueOf(newValue.intValue()));
        });
        
        boardBlanksSlider.valueProperty().addListener((obs, oldval, newVal) -> boardBlanksSlider.setValue(Math.round(newVal.intValue())));

        randomPlayerBtn.setOnAction(e -> {
            Player player = new RandomPlayer(String.valueOf(players.size() + 1), 5);
            players.add(player);
            proceedBtn.setVisible(true);
            nrPlayers.setText("Number Of Players: " + players.size());
            
            Label type = new Label("Random Player:");
            TextField name = new TextField(String.valueOf(players.size()));
            Label progSizeLabel = new Label("Progression Size:");
            TextField progSizeField = new TextField("5");
            Slider progSizeSlider = new Slider(1, 100, 1);
            Button removeBtn = new Button("x");
            
            HBox rpHBox = new HBox(type, name, progSizeLabel, progSizeField, progSizeSlider, removeBtn);
            rpHBox.setSpacing(10);
            rpHBox.setPadding(new Insets(5,5,5,5));
            rpHBox.setAlignment(Pos.CENTER);
            rpHBox.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
            + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
            + "-fx-border-radius: 5;" + "-fx-border-color: black;");
            
            centerScrollPaneVBox.getChildren().add(rpHBox);
            
            name.setOnAction(event -> {
                player.setName(name.getText());
            });
            
            progSizeField.setOnAction(event -> {
                player.progressionSize = Integer.valueOf(progSizeField.getText());
            });
            
            progSizeSlider.valueProperty().addListener((ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) -> {
                progSizeField.setText(String.valueOf(newValue.intValue()));
                player.progressionSize = newValue.intValue();
            });
        
            progSizeSlider.valueProperty().addListener((obs, oldval, newVal) -> progSizeSlider.setValue(Math.round(newVal.doubleValue())));
        
            removeBtn.setOnAction(event -> {
                centerScrollPaneVBox.getChildren().remove(rpHBox);
                players.remove(player);
                nrPlayers.setText("Number Of Players: " + players.size());
                if(players.isEmpty())
                    proceedBtn.setVisible(false);
            });
        });
        
        manualPlayerBtn.setOnAction(e -> {
            Player player = new ManualPlayer(String.valueOf(players.size() + 1), 5);
            players.add(player);
            proceedBtn.setVisible(true);
            nrPlayers.setText("Number Of Players: " + players.size());
            
            Label type = new Label("Manual Player:");
            TextField name = new TextField(String.valueOf(players.size()));
            Label progSizeLabel = new Label("Progression Size:");
            TextField progSizeField = new TextField("5");
            Slider progSizeSlider = new Slider(1, 100, 1);
            Button removeBtn = new Button("x");
            
            HBox rpHBox = new HBox(type, name, progSizeLabel, progSizeField, progSizeSlider, removeBtn);
            rpHBox.setSpacing(10);
            rpHBox.setPadding(new Insets(5,5,5,5));
            rpHBox.setAlignment(Pos.CENTER);
            rpHBox.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
            + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
            + "-fx-border-radius: 5;" + "-fx-border-color: black;");
            
            centerScrollPaneVBox.getChildren().add(rpHBox);
            
            name.setOnAction(event -> {
                player.setName(name.getText());
            });
            
            progSizeField.setOnAction(event -> {
                player.progressionSize = Integer.valueOf(progSizeField.getText());
            });
            
            progSizeSlider.valueProperty().addListener((ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) -> {
                progSizeField.setText(String.valueOf(newValue.intValue()));
                player.progressionSize = newValue.intValue();
            });
        
            progSizeSlider.valueProperty().addListener((obs, oldval, newVal) -> progSizeSlider.setValue(Math.round(newVal.doubleValue())));
        
            removeBtn.setOnAction(event -> {
                centerScrollPaneVBox.getChildren().remove(rpHBox);
                players.remove(player);
                nrPlayers.setText("Number Of Players: " + players.size());
                if(players.isEmpty())
                    proceedBtn.setVisible(false);
            });
        });
        
        smartPlayerBtn.setOnAction(e -> {
            Player player = new RandomPlayer(String.valueOf(players.size() + 1), 5);
            players.add(player);
            proceedBtn.setVisible(true);
            nrPlayers.setText("Number Of Players: " + players.size());
            
            Label type = new Label("Smart Player:");
            TextField name = new TextField(String.valueOf(players.size()));
            Label progSizeLabel = new Label("Progression Size:");
            TextField progSizeField = new TextField("5");
            Slider progSizeSlider = new Slider(1, 100, 1);
            Button removeBtn = new Button("x");
            
            HBox rpHBox = new HBox(type, name, progSizeLabel, progSizeField, progSizeSlider, removeBtn);
            rpHBox.setSpacing(10);
            rpHBox.setPadding(new Insets(5,5,5,5));
            rpHBox.setAlignment(Pos.CENTER);
            rpHBox.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
            + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
            + "-fx-border-radius: 5;" + "-fx-border-color: black;");
            
            centerScrollPaneVBox.getChildren().add(rpHBox);
            
            name.setOnAction(event -> {
                player.setName(name.getText());
            });
            
            progSizeField.setOnAction(event -> {
                player.progressionSize = Integer.valueOf(progSizeField.getText());
            });
            
            progSizeSlider.valueProperty().addListener((ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) -> {
                progSizeField.setText(String.valueOf(newValue.intValue()));
                player.progressionSize = newValue.intValue();
            });
        
            progSizeSlider.valueProperty().addListener((obs, oldval, newVal) -> progSizeSlider.setValue(Math.round(newVal.doubleValue())));
        
            removeBtn.setOnAction(event -> {
                centerScrollPaneVBox.getChildren().remove(rpHBox);
                players.remove(player);
                nrPlayers.setText("Number Of Players: " + players.size());
                if(players.isEmpty())
                    proceedBtn.setVisible(false);
            });
        });

        proceedBtn.setOnAction(e -> {
            
            boolean stop = false;
            
            for(Player p : players)
            {
                if(p.progressionSize > Integer.valueOf(boardSizeField.getText()))
                {
                    Alert alert = new Alert(AlertType.WARNING, "Player " + p.getName() + " has a progression higher than the board size. Continue?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
                    alert.showAndWait();
                    if(alert.getResult() == ButtonType.NO)
                        stop = true;
                }
                else if(p.progressionSize == 0)
                {
                    Alert alert = new Alert(AlertType.ERROR, "Player " + p.getName() + " has the progression size set to 0.", ButtonType.CLOSE);
                    alert.showAndWait();
                    stop = true;
                }
                else if(p.getName().isBlank())
                {
                    Alert alert = new Alert(AlertType.ERROR, "A player doesn't have his name set.", ButtonType.CLOSE);
                    alert.showAndWait();
                    stop = true;
                }
            }
            
            if(Integer.valueOf(boardSizeField.getText()) == 0)
            {
                Alert alert = new Alert(AlertType.ERROR, "Board size set to 0.", ButtonType.CLOSE);
                alert.showAndWait();
                stop = true;
            }
            if(Integer.valueOf(boardBlanksField.getText()) > Integer.valueOf(boardSizeField.getText()))
            {
                Alert alert = new Alert(AlertType.ERROR, "Nr of blanks higher than board size.", ButtonType.CLOSE);
                alert.showAndWait();
                stop = true;
            }
            
            if(!stop)
            {
                Board board = new Board(Integer.valueOf(boardSizeField.getText()), Integer.valueOf(boardBlanksField.getText()));
                Player[] pl = new Player[players.size()];
                players.toArray(pl);
                board.setPlayers(pl);
                Game game = new Game(board);
                GameScreen gameScreen = new GameScreen(stage, game);
                game.gameScreen = gameScreen;
                Task task = new Task<Void>() {
                    @Override
                    public Void call() {
                        try {
                            game.start();
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                        return null;
                    }
                };
                new Thread(task).start();
            }
        });
    }
    
    public Scene getScene()
    {
        return this.scene;
    }
}
