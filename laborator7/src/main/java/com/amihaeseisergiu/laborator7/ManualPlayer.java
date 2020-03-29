package com.amihaeseisergiu.laborator7;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ManualPlayer extends Player {
    
    public ManualPlayer(String name, int progressionSize)
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
                    board.getGame().getTimeKeeper().setDisplayTime(false);
                    
                    System.out.println("It's " + this + "'s turn. Available tokens: ");
                    System.out.println(board.printTokens());
                    
                    Scanner scan = new Scanner(System.in);
                    
                    int pick = scan.nextInt();
                    while(pick >= board.getTokens().size() || pick < 0)
                    {
                        System.out.println("Token does not exist. Please pick another one: ");
                        pick = scan.nextInt();
                    }
                    int token = board.getTokens().get(pick).getNumber();
                    
                    board.takeMove(this, pick, 0);
                    
                    System.out.println(this + " has extracted the token " + token);
                    board.getGame().getTimeKeeper().setDisplayTime(true);
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
