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
        
        String base_dir = System.getenv("NUTCH_BASE_DIR");  
        
        int num_rounds = Integer.parseInt(System.getenv("NUM_ROUNDS"));

        
        List<String> listUris = new ArrayList<String>();
        listUris.add("https://dbpedia.org/resource/Moscow");

        CrawlerProcess p = new CrawlerProcess(listUris,base_dir,num_rounds);
        p.prepareProcess();
        p.startProcess();

        DumpProcess d = new DumpProcess(sparqlEndpoint, sparqlUser, sparqlPasswd,base_dir);
        d.prepareProcess();
        d.startProcess();

    }
    
    
    

}
