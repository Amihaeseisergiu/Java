package com.mycompany.laborator5;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ViewCommand implements Command {
    
    Catalog catalog;
    String id;
    
    public ViewCommand(String id, Catalog catalog)
    {
        this.catalog = catalog;
        this.id = id;
    }

    @Override
    public void execute() {
        Document doc = catalog.findById(id);
        try {
            CatalogUtil.view(doc);
        } catch (IOException ex) {
            Logger.getLogger(ViewCommand.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidURLException ex) {
            Logger.getLogger(ViewCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
