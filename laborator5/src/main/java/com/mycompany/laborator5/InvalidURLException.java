package com.mycompany.laborator5;

public class InvalidURLException extends Exception {

    public InvalidURLException() {
        super("Invalid url. ");
    }

    public InvalidURLException(Exception ex) {
        super("Invalid url. ", ex);
    }
}
