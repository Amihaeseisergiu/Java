package com.amihaeseisergiu.serverapplication;

import java.util.ArrayList;
import java.util.List;

public class Board {
    
    private List<ClientThread> players = new ArrayList<>();
    private Game game;
    private int playerTurn;
    private int[][] board = new int[15][15];
    private boolean active = true;
    
    public Board(ClientThread player)
    {
        players.add(player);
        player.setBoard(this);
        
        for(int i = 0; i < 15; i++)
        {
            for(int j = 0; j < 15; j++)
            {
                board[i][j] = -1;
            }
        }
    }
    
    public void addPlayer(ClientThread player)
    {
        getPlayers().add(player);
        player.setBoard(this);
    }
    
    public synchronized boolean canMove(ClientThread player)
    {
        return getPlayers().get(getPlayerTurn()) == player;
    }
    
    public synchronized void takeMove(ClientThread player, int i, int j)
    {
        setPlayerTurn(getPlayers().indexOf(player));
        
        board[i][j] = getPlayerTurn();
        
        if(checkWin(board[i][j], i, j)) active = false;
        
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
    public synchronized int getPlayerTurn() {
        return playerTurn;
    }

    /**
     * @param playerTurn the playerTurn to set
     */
    public void setPlayerTurn(int playerTurn) {
        this.playerTurn = playerTurn;
    }

    /**
     * @return the board
     */
    public int[][] getBoard() {
        return board;
    }

    /**
     * @param board the board to set
     */
    public void setBoard(int[][] board) {
        this.board = board;
    }

    /**
     * @return the active
     */
    public synchronized boolean isActive() {
        return active;
    }
    
    public boolean checkWin(int turn, int row, int col)
    {
        int dirX[] = new int[]{1, 0, 1, 1};
        int dirY[] = new int[]{0, 1, 1, -1};
        int r, c;

        for(int k = 0; k < dirX.length; k++)
        {
            int ct = 1;
            r = row + dirX[k];
            c = col + dirY[k];
            while ( r >= 0 && r < 13 && c >= 0 && c < 13 && board[r][c] == turn )
            {
               ct++;
               r += dirX[k];
               c += dirY[k];
            }

            r = row - dirX[k];
            c = col - dirY[k];
            while ( r >= 0 && r < 13 && c >= 0 && c < 13 && board[r][c] == turn )
            {
               ct++;
               r -= dirX[k];
               c -= dirY[k];
            }
            
            if(ct == 5) return true;
        }
        
        return false;
    }
}
