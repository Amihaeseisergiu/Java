package com.amihaeseisergiu.laborator7;

public class Main {
    
    public static void main(String args[]) throws InterruptedException
    {
        Board board = new Board(20);
        board.setPlayers(new SmartPlayer("Sergiu",4),
                         new ManualPlayer("Ana",4),
                         new RandomPlayer("Gigel",4));
        Game game = new Game(board);
        game.start();
    }
}
