package com.mycompany.laborator5;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoadCommand implements Command {

    private final String path;
    Shell sh;
    
    public LoadCommand(String path, Shell sh)
    {
        this.path = path;
        this.sh = sh;
    }
    
    @Override
    public void execute() {
        try {
            sh.setCatalog(CatalogUtil.loadFromPlainText(path));
        } catch (IOException ex) {
            Logger.getLogger(LoadCommand.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DuplicateNameException ex) {
            Logger.getLogger(LoadCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
