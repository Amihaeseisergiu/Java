package com.mycompany.laborator5;

public class InvalidPathException extends Exception {

    public InvalidPathException() {
        super("Invalid path. ");
    }

    public InvalidPathException(Exception ex) {
        super("Invalid path. ", ex);
    }
}
