package com.amihaeseisergiu.laborator7;

import java.util.List;
import java.util.stream.Collectors;

public class Game {
    
    private Board board;
    private final TimeKeeper timeKeeper;
    private Player winner;
    private boolean stop = false;
    
    public Game(Board board)
    {
        this.board = board;
        this.timeKeeper = new TimeKeeper(this);
        board.setGame(this);
    }
    
    public boolean ended()
    {
        if(stop) return true;
        return winner != null;
    }
    
    public void start() throws InterruptedException
    {
        List<Thread> threads = board.getPlayers().stream()
            .map(player -> new Thread(player))
            .collect(Collectors.toList());
        threads.add(new Thread(getTimeKeeper()));

        threads.forEach(Thread::start);
        for (Thread thread : threads) {
            thread.join();
        }
        
        if(winner != null)
        {
            List<Token> longest = winner.getLongestProgression();
            StringBuilder builder = new StringBuilder();
            for(Token t : longest)
            {
                builder.append(t.getNumber());
                builder.append(" ");
            }
            System.out.println("The Winner is " + winner + " with a score of " + longest.size() + " | Tokens: " + winner.printTokens() + "| Longest Progression: " + builder.toString());
        }
        else
        {
            boolean hasWinner = false;
            for(Player p : board.getPlayers()) //winner didn't manage to announce he'd won
            {
                if(p.getLongestProgression().size() == p.getProgressionSize())
                {
                    List<Token> longest = p.getLongestProgression();
                    StringBuilder builder = new StringBuilder();
                    for(Token t : longest)
                    {
                        builder.append(t.getNumber());
                        builder.append(" ");
                    }
                    System.out.println("The Winner is " + p + " with a score of " + longest.size() + " | Tokens: " + p.printTokens() + "| Longest Progression: " + builder.toString());
                    hasWinner = true;
                    break;
                }
            }
            if(!hasWinner)
            {
                System.out.println("No one managed to finish. Final scores:");
                for(Player p : board.getPlayers())
                {
                    List<Token> longest = p.getLongestProgression();
                    StringBuilder builder = new StringBuilder();
                    for(Token t : longest)
                    {
                        builder.append(t.getNumber());
                        builder.append(" ");
                    }
                    System.out.println("Player " + p + " with a score of " + longest.size() + " | Tokens: " + p.printTokens() + "| Longest Progression: " + builder.toString());
                }
            }
        }
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
     * @return the winner
     */
    public Player getWinner() {
        return winner;
    }

    public synchronized void setWinner(Player player) {
        if(winner == null) 
            this.winner = player;
    }
    
    /**
     * @param stop the stop to set
     */
    public void setStopped(boolean stop) {
        this.stop = stop;
    }

    /**
     * @return the timeKeeper
     */
    public TimeKeeper getTimeKeeper() {
        return timeKeeper;
    }
    
}
