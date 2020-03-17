package com.mycompany.laborator5;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Shell {
    
    private Scanner scan;
    private Catalog catalog;
    
    public Shell()
    {
        scan = new Scanner(System.in);
    }
    
    public void start() throws InvalidCommandException
    {
        while(true)
        {
            String text = getScan().nextLine();
            List<String> arguments = getArguments(text);

            if(arguments.get(0).equals("exit"))
                break;
            
            Command command;
            switch(arguments.get(0))
            {
                case "load" :
                    command = new LoadCommand(arguments.get(1), this);
                    command.execute();
                    break;
                case "list" :
                    command = new ListCommand(getCatalog());
                    command.execute();
                    break;
                case "view" :
                    command = new ViewCommand(arguments.get(1), getCatalog());
                    command.execute();
                    break;
                case "reporthtml" :
                    command = new ReportHtmlCommand(getCatalog());
                    command.execute();
                    break;
                case "info" :
                    command = new InfoCommand(arguments.get(1));
                    command.execute();
                    break;
                case "report" :
                    switch(arguments.get(1))
                    {
                        case "html" :
                            command = new ReportCommand(catalog);
                            command.execute();
                            break;
                    }
                    break;
                default: 
                    throw new InvalidCommandException();
            }
        }
    }
    
    private List<String> getArguments(String text)
    {
        List<String> args = new ArrayList<>();
        
        for(String val : text.split(" "))
            args.add(val);
        
        return args;
    }

    /**
     * @return the scan
     */
    public Scanner getScan() {
        return scan;
    }

    /**
     * @param scan the scan to set
     */
    public void setScan(Scanner scan) {
        this.scan = scan;
    }

    /**
     * @return the catalog
     */
    public Catalog getCatalog() {
        return catalog;
    }

    /**
     * @param catalog the catalog to set
     */
    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }
}
