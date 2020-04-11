package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigurationFile {
    
    private static String implementation = null;
    
    private ConfigurationFile()
    {
        try (InputStream input = new FileInputStream("src/main/resources/config.properties")) {

            Properties prop = new Properties();
            prop.load(input);

            implementation = prop.getProperty("implementation");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public static String getImplementation()
    {
        if(implementation == null)
        {
            ConfigurationFile cf = new ConfigurationFile();
            return implementation;
        }
        
        return implementation;
    }
}
