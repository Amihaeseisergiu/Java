package com.amihaeseisergiu.laborator7;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class Player implements Runnable {
    
    protected String name;
    protected List<Token> tokens = new ArrayList<>();
    protected int progressionSize;
    protected Board board;
    public AtomicInteger wait;

    public List<Token> getLongestProgression() 
    {
        List<Token> longestProg = new ArrayList<>();
        
        if(tokens.size() <= 2)
        {
            longestProg.addAll(tokens);
            return longestProg;
        }
        longestProg.add(tokens.get(0));
        longestProg.add(tokens.get(1));
        
        int n = tokens.size();
        
        for(int i = 0; i < n; i++)
            for(int j = 0; j < n; j++)
            {
                if(j != i)
                {
                    List<Token> temp = new ArrayList<>();
                    temp.add(tokens.get(i));
                    temp.add(tokens.get(j));
                    
                    if(tokens.get(i).isBlank() && !tokens.get(j).isBlank())
                    {
                        for(int l = 1; l <= board.initialSize; l++)
                        {
                            temp = new ArrayList<>();
                            temp.add(tokens.get(i));
                            temp.add(tokens.get(j));
                            if(tokens.get(j).getNumber() != l)
                            {
                                int last = tokens.get(j).getNumber();
                                int diff = tokens.get(j).getNumber() - l;
                                for(int k = 0; k < n; k++)
                                {
                                    if(k!=i && k!=j)
                                    {
                                        if(tokens.get(k).isBlank())
                                        {
                                            temp.add(tokens.get(k));
                                            last = last + diff;
                                        }
                                        else if((tokens.get(k).getNumber() - last) == diff)
                                        {
                                            temp.add(tokens.get(k));
                                            last = tokens.get(k).getNumber();
                                        }
                                    }
                                }
                                if(temp.size() > longestProg.size())
                                {
                                    longestProg = new ArrayList<>();
                                    longestProg.addAll(temp);
                                }
                            }
                        }
                    }
                    else if(tokens.get(j).isBlank() && !tokens.get(i).isBlank())
                    {
                        for(int l = 1; l <= board.initialSize; l++)
                        {
                            temp = new ArrayList<>();
                            temp.add(tokens.get(i));
                            temp.add(tokens.get(j));
                            if(l != tokens.get(i).getNumber())
                            {
                                int last = l;
                                int diff = l - tokens.get(i).getNumber();
                                for(int k = 0; k < n; k++)
                                {
                                    if(k!=i && k!=j)
                                    {
                                        if(tokens.get(k).isBlank())
                                        {
                                            temp.add(tokens.get(k));
                                            last = last + diff;
                                        }
                                        else if((tokens.get(k).getNumber() - last) == diff)
                                        {
                                            temp.add(tokens.get(k));
                                            last = tokens.get(k).getNumber();
                                        }
                                    }
                                }
                                if(temp.size() > longestProg.size())
                                {
                                    longestProg = new ArrayList<>();
                                    longestProg.addAll(temp);
                                }
                            }
                        }
                    }
                    else if(tokens.get(i).isBlank() && tokens.get(j).isBlank())
                    {
                        for(int l = 1; l <= board.initialSize; l++)
                            for(int m = 1+1; m <= board.initialSize; m++)
                            {
                                temp = new ArrayList<>();
                                temp.add(tokens.get(i));
                                temp.add(tokens.get(j));
                                if(m != l)
                                {
                                    int last = m;
                                    int diff = m - l;
                                    for(int k = 0; k < n; k++)
                                    {
                                        if(k!=i && k!=j)
                                        {
                                            if(tokens.get(k).isBlank())
                                            {
                                                temp.add(tokens.get(k));
                                                last = last + diff;
                                            }
                                            else if((tokens.get(k).getNumber() - last) == diff)
                                            {
                                                temp.add(tokens.get(k));
                                                last = tokens.get(k).getNumber();
                                            }
                                        }
                                    }
                                    if(temp.size() > longestProg.size())
                                    {
                                        longestProg = new ArrayList<>();
                                        longestProg.addAll(temp);
                                    }
                                }
                            }
                    }
                    else
                    {
                        int last = tokens.get(j).getNumber();
                        int diff = tokens.get(j).getNumber() - tokens.get(i).getNumber();
                        for(int k = 0; k < n; k++)
                        {
                            if(k!=i && k!=j)
                            {
                                if(tokens.get(k).isBlank())
                                {
                                    temp.add(tokens.get(k));
                                    last = last + diff;
                                }
                                else if((tokens.get(k).getNumber() - last) == diff)
                                {
                                    temp.add(tokens.get(k));
                                    last = tokens.get(k).getNumber();
                                }
                            }
                        }
                        if(temp.size() > longestProg.size())
                        {
                            longestProg = new ArrayList<>();
                            longestProg.addAll(temp);
                        }
                    }
                }
            }
        
        return longestProg;  
    }
    
    public String printTokens()
    {
        StringBuilder builder = new StringBuilder();
        
        getTokens().stream().map((t) -> {
            builder.append(t.getNumber());
            return t;
        }).forEachOrdered((_item) -> {
            builder.append(" ");
        });
        return builder.toString();
    }

    public String getName() {
        return name;
    }

    public List<Token> getTokens() {
        return tokens;
    }

    public int getProgressionSize() {
        return progressionSize;
    }

    Board getBoard() {
        return board;
    }

    void setName(String name) {
        this.name = name;
    }

    void setTokens(List<Token> tokens) {
        this.tokens = tokens;
    }
    
    public void setBoard(Board board)
    {
        this.board = board;
    }
    
    @Override
    public String toString()
    {
        return this.getName();
    }
}
