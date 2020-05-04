package com.amihaeseisergiu.serverapplication;

import java.time.LocalDateTime;

public class ErrorResponse {
 
    private LocalDateTime time;
    private String message;
    
    public ErrorResponse(String message)
    {
        this.message = message;
    }

    /**
     * @return the time
     */
    public LocalDateTime getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTimestamp(LocalDateTime time) {
        this.time = time;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }
    
    
}
