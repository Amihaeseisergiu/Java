package com.amihaeseisergiu.laborator7;

public class Token implements Comparable<Token> {
    
    private int number;
    private boolean blank;

    public Token(int number)
    {
        this.number = number;
    }
    
    public Token(boolean isBlanks)
    {
        this.blank = true;
    }
    
    /**
     * @return the number
     */
    public int getNumber() {
        return number;
    }

    /**
     * @param number the number to set
     */
    public void setNumber(int number) {
        this.number = number;
    }

    /**
     * @return the blank
     */
    public boolean isBlank() {
        return blank;
    }

    /**
     * @param blank the blank to set
     */
    public void setBlank(boolean blank) {
        this.blank = blank;
    }

    @Override
    public int compareTo(Token t) {
        if(this.number < t.getNumber()) return -1;
        if(this.number > t.getNumber()) return 1;
        return 0;
    }
    
}
