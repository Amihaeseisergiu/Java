package com.mycompany.laborator5;

public class InvalidCommandException extends Exception {

    public InvalidCommandException()
    {
        super("Invalid command. ");
    }
    
    public InvalidCommandException(String msg) {
        super(msg);
    }
}
