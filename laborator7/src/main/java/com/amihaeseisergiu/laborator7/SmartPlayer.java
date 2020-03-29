package com.amihaeseisergiu.laborator7;

import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SmartPlayer extends Player {

    public SmartPlayer(String name, int progressionSize)
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
                    System.out.println("It's " + this + "'s turn. Available tokens: ");
                    System.out.println(board.printTokens());

                    int pick = findBestOption();

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
    
    private int findBestOption()
    {
        Random rand = new Random();
        
        List<Token> prog = getLongestProgression();
        if(prog.size() <= 2) return rand.nextInt(board.getTokens().size());
        
        int diff = prog.get(prog.size() - 1).getNumber() - prog.get(prog.size() - 2).getNumber();
        
        Token highestExtending = null;
        int nrNotExtending = -1;
        Token highestNonExtending = null;
        int nrNotExtending2 = -1;
        for(Token t : board.getTokens())
        {
            int cont = 0;
            for(Player p : board.getPlayers())
            {
                List<Token> temp = p.getLongestProgression();
                if(temp.get(temp.size() - 1).getNumber() - temp.get(temp.size() - 2).getNumber() == t.getNumber() - temp.get(temp.size() - 1).getNumber())
                    cont++;
            }
            if(t.getNumber() - prog.get(prog.size() - 1).getNumber() == diff)
            {
                if(cont > nrNotExtending)
                {
                    nrNotExtending = cont;
                    highestExtending = t;
                }
            }
            else
            {
                if(cont > nrNotExtending2)
                {
                    nrNotExtending2 = cont;
                    highestNonExtending = t;
                }
            }
        }
        
        if(highestExtending != null) return board.getTokens().indexOf(highestExtending);
        else if(highestNonExtending != null) return board.getTokens().indexOf(highestNonExtending);
        
        return rand.nextInt(board.getTokens().size());
    }
}
