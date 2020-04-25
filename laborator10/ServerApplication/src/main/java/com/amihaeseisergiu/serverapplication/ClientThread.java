package com.amihaeseisergiu.serverapplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientThread extends Thread {

    private Socket socket = null;
    private final ServerSocket serverSocket;
    BufferedReader in;
    PrintWriter out;
    private GameServer server;
    private Board board;

    public ClientThread(Socket socket, ServerSocket serverSocket, GameServer server)
    {
        this.socket = socket;
        this.serverSocket = serverSocket;
        this.server = server;
    }

    @Override
    public void run() {
        try
        {
            in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());
            
            while(true)
            {
                String request = in.readLine();
                System.out.println(request);
                String[] splitRequest = request.trim().split("\\s+");

                String response;
                
                if(splitRequest[0].equals("stop"))
                {
                    serverSocket.close();
                    response = "Server stopped";
                    out.println(response);
                    out.flush();
                    break;
                }
                else if(splitRequest[0].equals("refresh"))
                {
                    response = "lobbylist " + String.valueOf(server.getGames().size());
                    out.println(response);
                    out.flush();
                }
                else if(splitRequest.length > 1 && splitRequest[0].equals("create") && splitRequest[1].equals("game"))
                {
                    if(board != null)
                    {
                        response = "You're already in a lobby";
                        out.println(response);
                        out.flush();
                    }
                    else
                    {
                        server.getGames().add(new Game(new Board(this)));
                        response = "gamecreated " + String.valueOf(server.getGames().size());
                        out.println(response);
                        out.flush();
                    }
                }
                else if(splitRequest.length > 1 && splitRequest[0].equals("join") && splitRequest[1].equals("game"))
                {
                    if(Integer.valueOf(splitRequest[2]) < 1 || Integer.valueOf(splitRequest[2]) > server.getGames().size())
                    {
                        response = "Invalid game room id";
                        out.println(response);
                        out.flush();
                    }
                    else if(server.getGames().get(Integer.valueOf(splitRequest[2]) - 1).getBoard().getPlayers().size() < 2)
                    {
                        if(server.getGames().get(Integer.valueOf(splitRequest[2]) - 1).getBoard().getPlayers().indexOf(this) != -1)
                        {
                            response = "You're already in this lobby";
                            out.println(response);
                            out.flush();
                        }
                        else
                        {
                            server.getGames().get(Integer.valueOf(splitRequest[2]) - 1).getBoard().addPlayer(this);
                            board.getGame().setStarted(true);
                            response = "joinedgame " + splitRequest[2];
                            out.println(response);
                            out.flush();
                            
                            for(int i = 0; i < server.getGames().get(Integer.valueOf(splitRequest[2]) - 1).getBoard().getPlayers().size(); i++)
                            {
                                if(server.getGames().get(Integer.valueOf(splitRequest[2]) - 1).getBoard().getPlayers().get(i) != this)
                                {
                                    server.getGames().get(Integer.valueOf(splitRequest[2]) - 1).getBoard().getPlayers().get(i).getOut().println("userjoined");
                                    server.getGames().get(Integer.valueOf(splitRequest[2]) - 1).getBoard().getPlayers().get(i).getOut().flush();
                                }
                            }
                        }
                    }
                    else
                    {
                        response = "Game full";
                        out.println(response);
                        out.flush();
                    }
                }
                else if(splitRequest[0].equals("leave"))
                {
                    
                    for(int i = 0; i < board.getPlayers().size(); i++)
                    {
                        if(board.getPlayers().get(i) != this)
                        {
                            board.getPlayers().get(i).getOut().println("userleft " + board.getPlayers().indexOf(this));
                            board.getPlayers().get(i).getOut().flush();
                        }
                    }
                    
                    board.getPlayers().remove(this);
                    
                    if(board.getPlayers().isEmpty())
                    {
                        server.getGames().remove(board.getGame());
                    }
                    board = null;
                }
                else if(splitRequest[0].equals("exit"))
                    break;
                else
                {
                    response = "Server received the request " + request;
                    out.println(response);
                    out.flush();
                }
            }
        } catch (IOException e)
        {
            System.err.println("Communication error... " + e);
        } finally
        {
            try
            {
                socket.close();
            } catch (IOException e)
            {
                System.err.println(e);
            }
        }
    }
    
    public void setBoard(Board board)
    {
        this.board = board;
    }
    
    public BufferedReader getIn()
    {
        return in;
    }
    
    public PrintWriter getOut()
    {
        return out;
    }
}
