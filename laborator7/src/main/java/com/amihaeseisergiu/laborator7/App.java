package com.amihaeseisergiu.laborator7;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage)
    {
        ConfigurationScreen config = new ConfigurationScreen(stage);
        stage.setScene(config.getScene());
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}