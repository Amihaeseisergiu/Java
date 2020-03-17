package com.mycompany.laborator5;

public class DuplicateNameException extends Exception {

    public DuplicateNameException() {
        super("Duplicate name. ");
    }

    public DuplicateNameException(String msg) {
        super(msg);
    }
}
