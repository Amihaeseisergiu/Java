package com.amihaeseisergiu.laborator7;

public class TimeKeeper implements Runnable {

    private final long TIME_TO_STOP = 100; //in seconds
    private final long TIME_INTERVAL = 1; //in seconds
    private long startTime;
    private boolean displayedOnce;
    private boolean displayTime;
    private final Game game;
    
    public TimeKeeper(Game game)
    {
        this.game = game;
        displayTime = true;
    }
    
    @Override
    public void run()
    {
        startTime = System.currentTimeMillis();
        long currentTimeCopy = 0;
        while(game.getBoard().isActive())
        {
            long currentTime = (System.currentTimeMillis() - startTime) / 1_000;
            if(currentTime > TIME_TO_STOP)
            {
                game.setStopped(true);
            }
            else if(currentTime%TIME_INTERVAL == 0 && !displayedOnce)
            {
                if(displayTime)
                    System.out.println("Elapsed Time: " + String.valueOf(currentTime) + " s. Time left: " + (TIME_TO_STOP - currentTime));
                displayedOnce = true;
                currentTimeCopy = currentTime;
            }
            else if(currentTime%TIME_INTERVAL == 0 && displayedOnce && currentTime != currentTimeCopy)
            {
                displayedOnce = false;
            }
        }
    }

    /**
     * @param displayTime the displayTime to set
     */
    public void setDisplayTime(boolean displayTime) {
        this.displayTime = displayTime;
    }
    
}
