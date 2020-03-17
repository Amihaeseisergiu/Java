package com.mycompany.laborator5;

public class InvalidCatalogException extends Exception {

    public InvalidCatalogException() {
        super("Invalid catalog file. ");
    }

    public InvalidCatalogException(String msg) {
        super(msg);
    }
}
