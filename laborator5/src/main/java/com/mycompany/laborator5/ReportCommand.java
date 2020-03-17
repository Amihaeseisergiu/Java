package com.mycompany.laborator5;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReportCommand implements Command {

    Catalog catalog;
    
    public ReportCommand(Catalog catalog)
    {
        this.catalog = catalog;
    }
    
    @Override
    public void execute() {
        try {
            Configuration cfg = new Configuration(Configuration.VERSION_2_3_29);
            try {
                cfg.setDirectoryForTemplateLoading(new File("D:\\Facultate\\Anul II\\semestrul 2\\PA\\laborator5"));
            } catch (IOException ex) {
                Logger.getLogger(ReportCommand.class.getName()).log(Level.SEVERE, null, ex);
            }
            cfg.setDefaultEncoding("UTF-8");
            
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            cfg.setLogTemplateExceptions(false);
            cfg.setWrapUncheckedExceptions(true);
            cfg.setFallbackOnNullLoopVariable(false);
            
            Map<Document, Map<String, Object>> documents = new HashMap();
            for(var entry : catalog.getDocuments())
            {
                documents.put(entry, entry.getTags());
            }
            List<Map<Document, Map<String, Object>>> convertedMap = new ArrayList(documents.entrySet());
            Map<String, Object> root = new HashMap<>();
            root.put("catalog", catalog.getName());
            root.put("convertedMap", convertedMap);
            
            Template temp = cfg.getTemplate("test.ftlh");
            Writer out = new OutputStreamWriter(System.out);
            temp.process(root, out);
            System.out.println();
            
        } catch (MalformedTemplateNameException ex) {
            Logger.getLogger(ReportCommand.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException | TemplateException ex) {
            Logger.getLogger(ReportCommand.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ReportCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}