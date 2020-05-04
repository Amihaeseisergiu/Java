package com.amihaeseisergiu.clientapplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import javafx.application.Application;
import javafx.stage.Stage;

public class GameClient extends Application {

    PrintWriter out;
    BufferedReader in;
    Socket socket;
    
    public GameClient() throws IOException
    {
        String serverAddress = "127.0.0.1";
        int PORT = 8100;
        socket = new Socket(serverAddress, PORT);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }
    
    public static void main(String[] args) throws IOException
    {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        
        StartScreen scrn = new StartScreen(stage, in, out);
    }
}
