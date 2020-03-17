package com.mycompany.laborator5;

import java.io.IOException;

public class Main {
    
    public static void main(String args[]) throws IOException, InvalidCatalogException, ClassNotFoundException, InvalidCommandException, DuplicateNameException, InvalidURLException, InvalidPathException
    {
        Main app = new Main();
        Shell shell = new Shell();
        shell.start();
        //app.testCreateSave2();
        //app.testCreateSave();
        //app.testLoadView();
    }
    
    private void testCreateSave() throws IOException, DuplicateNameException
    {
        Catalog catalog = new Catalog("Java Resources", "d:/java/catalog.ser");
        
        Document doc = new Document("java1", "Java Course1", 
        "https://profs.info.uaic.ro/~acf/java/slides/en/intro_slide_en.pdf");
        
        doc.addTag("type", "Slides");
        catalog.add(doc);
        
        CatalogUtil.save(catalog);
    }
    
    private void testCreateSave2() throws IOException, DuplicateNameException, InvalidURLException
    {
        Catalog catalog = new Catalog("Java Resources", "d:/java/catalog.ser");
        
        Document doc = new Document("java1", "Java Course1", 
        "https://profs.info.uaic.ro/~acf/java/slides/en/intro_slide_en.pdf");
        
        doc.addTag("type", "Slides");
        catalog.add(doc);
        
        CatalogUtil.saveInPlainText(catalog);
        
        catalog = CatalogUtil.loadFromPlainText("d:/java/catalog.ser");
        doc = catalog.findById("java1");
        CatalogUtil.view(doc);
    }
    
    private void testLoadView() throws IOException, InvalidCatalogException, ClassNotFoundException, InvalidURLException, InvalidPathException
    {
        Catalog catalog = CatalogUtil.load("d:/java/catalog.ser");
        Document doc = catalog.findById("java1");
        CatalogUtil.view(doc);
    }
}
