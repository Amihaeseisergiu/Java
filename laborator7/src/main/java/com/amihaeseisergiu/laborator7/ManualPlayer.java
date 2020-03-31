package com.amihaeseisergiu.laborator7;

import java.util.concurrent.atomic.AtomicInteger;
import javafx.application.Platform;

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
                    
                    Platform.runLater(() -> {
                        board.getGame().gameScreen.disableAvailableTokens = false;
                        board.getGame().gameScreen.modifyAvailableTokens();
                        board.getGame().gameScreen.modifyCurrentTokens(this);
                    });
                    
                    wait = new AtomicInteger(-1);
                    while(wait.get() == -1 && !board.getGame().ended()) {}
                    
                    if(wait.get() != -1)
                    {
                        int token = board.getTokens().get(wait.get()).getNumber();
                    
                        Platform.runLater(() -> {
                            board.getGame().gameScreen.pressedButtonIndex = -1;
                        });

                        board.takeMove(this, wait.get(), 0);

                        System.out.println(this + " has extracted the token " + token);
                    }
                    board.getGame().getTimeKeeper().setDisplayTime(true);
                    board.notifyAll();
                }
                else
                {
                    try {
                        board.wait();
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }
}
