package com.mycompany.laborator5;

public class ListCommand implements Command {
    
    Catalog catalog;
    
    public ListCommand(Catalog catalog)
    {
        this.catalog = catalog;
    }

    @Override
    public void execute() {
        for(var doc : catalog.getDocuments())
        {
            System.out.println(doc.getId());
        }
    }
}
