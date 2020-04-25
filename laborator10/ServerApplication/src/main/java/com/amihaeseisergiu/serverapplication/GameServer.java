package com.amihaeseisergiu.serverapplication;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameServer {

    private static final int PORT = 8100;
    private CopyOnWriteArrayList<Game> games = new CopyOnWriteArrayList<>();
    
    public GameServer() throws IOException
    {
        ServerSocket serverSocket = null;
        try
        {
            serverSocket = new ServerSocket(PORT);
            while (true)
            {
                System.out.println("Waiting for a client ...");
                Socket socket = serverSocket.accept();

                new ClientThread(socket, serverSocket, this).start();
            }
        } catch (IOException e)
        {
            if (serverSocket != null && !serverSocket.isClosed()) {
                try {
                    serverSocket.close();
                } catch (IOException e2)
                {
                    e2.printStackTrace(System.err);
                }
            }
        } finally
        {
            serverSocket.close();
        }
    }

    public static void main(String[] args) throws IOException
    {
        GameServer server = new GameServer();
    }

    /**
     * @return the games
     */
    public CopyOnWriteArrayList<Game> getGames() {
        return games;
    }

    /**
     * @param games the games to set
     */
    public void setGames(CopyOnWriteArrayList<Game> games) {
        this.games = games;
    }
}
