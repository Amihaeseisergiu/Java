package com.mycompany.laborator5;

public class ReportHtmlCommand implements Command {

    Catalog catalog;
    StringBuilder html;
    
    public ReportHtmlCommand(Catalog catalog)
    {
        this.catalog = catalog;
    }
    
    @Override
    public void execute() {
        html = new StringBuilder();
        html.append( "<!doctype html>\n" );
        html.append( "<html lang='en'>\n" );

        html.append( "<head>\n" );
        html.append( "<meta charset='utf-8'>\n" );
        html.append( "<title>Contents of the catalog</title>\n" );
        html.append( "</head>\n\n" );

        html.append( "<body>\n" );
        html.append( "<h1>List of Documents</h1>\n" );

        html.append( "<ul>\n" );

        for ( Document doc : catalog.getDocuments() ) {
            html.append("<li>").append(doc.toString()).append("</li>\n");
        }
        html.append( "</ul>\n" );
        html.append( "</body>\n\n" );

        html.append( "</html>" );
        
        System.out.println(html.toString());
    }
    
    @Override
    public String toString()
    {
        return html.toString();
    }
}
