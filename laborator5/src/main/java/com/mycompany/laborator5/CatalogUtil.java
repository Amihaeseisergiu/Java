package com.mycompany.laborator5;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URI;
import java.nio.file.Paths;

public class CatalogUtil {
    
    public static void save(Catalog catalog) throws IOException
    {
        try(var oos = new ObjectOutputStream(new FileOutputStream(catalog.getPath())))
        {
            oos.writeObject(catalog);
        }
    }
    
    public static void saveInPlainText(Catalog catalog) throws IOException
    {
        File file = new File(catalog.getPath());
        file.mkdir();
        file = new File(catalog.getPath() + "/" + catalog.getName());
        file.mkdir();
        for(Document doc : catalog.getDocuments())
        {
            file = new File(catalog.getPath() + "/" + catalog.getName() + "/" + doc.getId());
            file.mkdir();
            file = new File(catalog.getPath() + "/" + catalog.getName() + "/" + doc.getId() + "/" + doc.getName());
            file.createNewFile();
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(doc.getLocation() + "\n");
                for(var entry : doc.getTags().entrySet())
                {
                    writer.write(entry.getKey() + "\n");
                    writer.write(entry.getValue().toString() + "\n");
                }
            }
        }
    }
    
    public static Catalog loadFromPlainText(String path) throws FileNotFoundException, IOException, DuplicateNameException
    {
        File file = new File(path);
        File[] files = file.listFiles();
        Catalog catalog = new Catalog(files[0].getName(), path);
        
        file = new File(files[0].getAbsolutePath());
        files = file.listFiles();
        
        for(File f : files)
        {
            File[] content = f.listFiles();
            try (BufferedReader reader = new BufferedReader(new FileReader(content[0]))) {
                String line = reader.readLine();
                Document doc = new Document(f.getName(), content[0].getName(), line);
                
                while(line != null)
                {
                    line = reader.readLine();
                    String key = line;
                    line = reader.readLine();
                    String value = line;
                    if(key != null && value != null)
                        doc.addTag(key, value);
                }
                catalog.add(doc);
            }
        }
        
        return catalog;
    }
    
    public static Catalog load(String path) throws InvalidCatalogException, IOException, ClassNotFoundException, InvalidPathException
    {
        try {
            Paths.get(path);
        } catch(Exception ex)
        {
            throw new InvalidPathException(ex);
        }
        
        Catalog cat;
        try(var ois = new ObjectInputStream(new FileInputStream(path)))
        {
            cat = (Catalog) ois.readObject();
        }
        
        if(cat == null) throw new InvalidCatalogException();
        return cat;
    }
    
    public static void view(Document doc) throws IOException, InvalidURLException
    {
        Desktop desktop = Desktop.getDesktop();
        
        try {
            (new java.net.URL(doc.getLocation())).openStream().close();
        } catch(Exception ex)
        {
            throw new InvalidURLException(ex);
        }
        
        URI myURI = URI.create(doc.getLocation());
        desktop.browse(myURI);
    }
}
