package com.amihaeseisergiu.serverapplication;

import java.util.ArrayList;
import java.util.List;

public class Board {
    
    private List<ClientThread> players = new ArrayList<>();
    private Game game;
    private int playerTurn;
    
    public Board(ClientThread player)
    {
        players.add(player);
        player.setBoard(this);
    }
    
    public void addPlayer(ClientThread player)
    {
        getPlayers().add(player);
        player.setBoard(this);
    }
    
    public boolean canMove(ClientThread player)
    {
        return getPlayers().get(getPlayerTurn()) == player;
    }
    
    public void takeMove(ClientThread player)
    {
        setPlayerTurn(getPlayers().indexOf(player));
        
        setPlayerTurn((getPlayerTurn() + 1) % getPlayers().size());
    }

    /**
     * @return the players
     */
    public List<ClientThread> getPlayers() {
        return players;
    }

    /**
     * @param players the players to set
     */
    public void setPlayers(List<ClientThread> players) {
        this.players = players;
    }

    /**
     * @return the game
     */
    public Game getGame() {
        return game;
    }

    /**
     * @param game the game to set
     */
    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * @return the playerTurn
     */
    public int getPlayerTurn() {
        return playerTurn;
    }

    /**
     * @param playerTurn the playerTurn to set
     */
    public void setPlayerTurn(int playerTurn) {
        this.playerTurn = playerTurn;
    }
}
