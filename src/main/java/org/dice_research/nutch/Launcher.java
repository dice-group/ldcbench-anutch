package org.dice_research.nutch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.dice_research.nutch.process.CrawlerProcess;
import org.dice_research.nutch.process.DumpProcess;

public class Launcher {

    public static void main(String[] args) throws IOException, InterruptedException {
        
        String sparqlEndpoint = System.getenv("SPARQL_URL");
        String sparqlUser = System.getenv("SPARQL_USER");
        String sparqlPasswd = System.getenv("SPARQL_PASSWD");
        

        
        List<String> listUris = new ArrayList<String>();
        listUris.add("https://dbpedia.org/resource/Moscow");
        listUris.add("https://mcloud.de/export/datasets/BA85C835-F36E-446D-A527-7B41A9E18407");

        CrawlerProcess p = new CrawlerProcess(listUris);
        p.prepareProcess();
        p.startProcess();

        DumpProcess d = new DumpProcess(sparqlEndpoint, sparqlUser, sparqlPasswd);
        d.prepareProcess();
        d.startProcess();

    }
    
    
    

}
