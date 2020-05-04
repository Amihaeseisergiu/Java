package com.amihaeseisergiu.serverapplication;

public class Game {
    
    private Board board;
    private boolean stop;
    private boolean started;
    private int id;
    
    public Game(Board board)
    {
        this.board = board;
        board.setGame(this);
    }
    
    /**
     * @return the board
     */
    public Board getBoard() {
        return board;
    }

    /**
     * @param board the board to set
     */
    public void setBoard(Board board) {
        this.board = board;
    }

    /**
     * @param stop the stop to set
     */
    public synchronized void setStopped(boolean stop) {
        this.stop = stop;
    }
    
    public boolean getStarted()
    {
        return started;
    }
    
    public synchronized void setStarted(boolean started) {
        this.started = started;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }
}
