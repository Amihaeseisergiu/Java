package com.amihaeseisergiu.laborator7;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RandomPlayer extends Player {
    
    public RandomPlayer(String name, int progressionSize)
    {
        this.name = name;
        this.progressionSize = progressionSize;
    }
    
    @Override
    public void run()
    {
        synchronized(board)
        {
            while(board.isActive())
            {
                if(board.canMove(this))
                {
                    System.out.println("It's " + this + "'s turn. Available tokens:");
                    System.out.println(board.printTokens());
                    
                    Random rand = new Random();
                    
                    int pick = rand.nextInt(board.getTokens().size());
                    int token = board.getTokens().get(pick).getNumber();
                    
                    board.takeMove(this, pick, 1);
                    
                    System.out.println(this + " has extracted the token " + token);
                    board.notifyAll();
                }
                else
                {
                    try {
                        board.wait();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }

}
