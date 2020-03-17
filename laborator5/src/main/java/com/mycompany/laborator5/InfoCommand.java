package com.mycompany.laborator5;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.txt.TXTParser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

public class InfoCommand implements Command {

    String path;
    
    public InfoCommand(String path)
    {
        this.path = path;
    }
    
    @Override
    public void execute() {
        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        FileInputStream inputstream = null;
          try {
              inputstream = new FileInputStream(new File(path));
          } catch (FileNotFoundException ex) {
              Logger.getLogger(InfoCommand.class.getName()).log(Level.SEVERE, null, ex);
          }
        ParseContext pcontext=new ParseContext();

        TXTParser TextParser = new TXTParser();
          try {
              TextParser.parse(inputstream, handler, metadata,pcontext);
          } catch (IOException | SAXException | TikaException ex) {
              Logger.getLogger(InfoCommand.class.getName()).log(Level.SEVERE, null, ex);
          }
        System.out.println("Contents of the document:" + handler.toString());
        System.out.println("Metadata of the document:");
        String[] metadataNames = metadata.names();

        for(String name : metadataNames) {
           System.out.println(name + " : " + metadata.get(name));
        }
   }
}
