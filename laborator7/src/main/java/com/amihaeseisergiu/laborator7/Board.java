package com.amihaeseisergiu.laborator7;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javafx.application.Platform;

public class Board {
    
    private List<Token> tokens = new ArrayList<>();
    private List<Player> players;
    private Game game;
    private int playerTurn;
    int initialSize;
    
    public Board(int n, int nrBlanks)
    {
        initialSize = n;
        
        for(int i = 1; i <= n - nrBlanks; i++)
        {
            tokens.add(new Token(i));
        }
        for(int i = 0; i < nrBlanks; i++)
        {
            tokens.add(new Token(true));
        }
        
        Collections.shuffle(tokens);
    }
    
    void setPlayers(Player...players)
    {
        this.players = Arrays.asList(players);
        this.getPlayers().forEach((Player player) -> player.setBoard(this));
        setPlayerTurn(0);
    }
    
    public boolean canMove(Player player)
    {
        return getPlayers().get(getPlayerTurn()) == player;
    }
    
    public boolean isActive()
    {
        if(tokens.isEmpty() || game.ended())
        {
            return false;
        }
        
        return true;
    }
    
    public void takeMove(Player player, int index, int thinkingTime) //thinking time in seconds
    {
        setPlayerTurn(getPlayers().indexOf(player));
        
        Token token = getTokens().get(index);
        getTokens().remove(token);
        player.getTokens().add(token);
        
        if(player.getLongestProgression().size() == player.getProgressionSize())
        {
            getGame().setWinner(player);
        }
        
        try {
            Thread.sleep(thinkingTime * 1000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        
        setPlayerTurn((getPlayerTurn() + 1) % getPlayers().size());
        Platform.runLater(() -> game.gameScreen.modifyPlayerTurn(playerTurn));
    }

    /**
     * @return the tokens
     */
    public List<Token> getTokens() {
        return tokens;
    }

    /**
     * @param tokens the tokens to set
     */
    public void setTokens(List<Token> tokens) {
        this.tokens = tokens;
    }

    /**
     * @return the players
     */
    public List<Player> getPlayers() {
        return players;
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
    
    public String printTokens()
    {
        StringBuilder builder = new StringBuilder();
        
        tokens.stream().map((t) -> {
            builder.append(t.getNumber());
            return t;
        }).forEachOrdered((_item) -> {
            builder.append(" ");
        });
        return builder.toString();
    }
}
