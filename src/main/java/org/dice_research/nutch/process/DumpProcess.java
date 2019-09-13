package org.dice_research.nutch.process;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.jena.graph.Triple;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.system.StreamRDF;
import org.apache.jena.riot.system.StreamRDFBase;
import org.apache.jena.sparql.core.Quad;
import org.dice_research.ldcbench.sink.SparqlBasedSink;
import org.dice_research.ldcbench.util.uri.Constants;
import org.dice_research.ldcbench.util.uri.CrawleableUri;

public class DumpProcess extends NutchProcess{
    
    private SparqlBasedSink sink;
    
    private List<Lang> listLangs = new ArrayList<Lang>();
    
    
    public DumpProcess(String sparqlUrl, String user, String passwd) {
      sink = SparqlBasedSink.create(sparqlUrl, user,
              passwd);
      
      listLangs.add(Lang.NT);
      listLangs.add(Lang.NQUADS);
      listLangs.add(Lang.RDFJSON);
      listLangs.add(Lang.RDFTHRIFT);
      listLangs.add(Lang.RDFXML);
      listLangs.add(Lang.JSONLD);
      listLangs.add(Lang.TRIG);
      listLangs.add(Lang.TRIX);
      listLangs.add(Lang.TTL);
      listLangs.add(Lang.TURTLE);

      sink.deleteTriples();
    }

    @Override
    public void prepareProcess() {
        String dump_cmd = getBaseDir() + "/nutch dump -flatdir -segment "+ getBaseDir() +"/crawled/segments -outputDir "+
                getBaseDir()+ "/dump/"; 
        
        processBuilder = new ProcessBuilder();
        processBuilder.command("bash","-c",dump_cmd);
        
    }
    
    @Override
    public void startProcess() {
        // TODO Auto-generated method stub
        super.startProcess();
        
        storeTriples();
    }
    
    private  void storeTriples() {
        
       List<File> listFiles = listFilesForFolder(new File(getBaseDir()+"/dump/"));
       

       
       try {
        CrawleableUri curi = new CrawleableUri(new URI("http://nutch.dice-group/" + System.currentTimeMillis()));
        curi.addData(Constants.UUID_KEY, UUID.randomUUID().toString());
        sink.openSinkForUri(curi);
        StreamRDF filtered = new FilterSinkRDF(curi, sink);
        for(File file: listFiles) {
            System.out.println("File: " + file.getName());
            for (Lang l : listLangs) {
                System.out.println(" -> " + l.getName() );
                try {
                    RDFDataMgr.parse(filtered, file.getAbsolutePath(), l);
                    break;
                } catch (Exception e) {
                }
            }
        }
        sink.closeSinkForUri(curi);
    } catch (URISyntaxException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
       
       
        
    }
    
    private List<File> listFilesForFolder(final File folder) {
        List<File> listFiles = new ArrayList<File>();
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            } else {
                listFiles.add(fileEntry);
            }
        }
        return listFiles;
    }
    
    
    public static class FilterSinkRDF extends StreamRDFBase{
        private CrawleableUri  curi;
        private SparqlBasedSink sink;

        public FilterSinkRDF(CrawleableUri curi, SparqlBasedSink sink) {
            this.curi = curi;
            this.sink = sink;
        }

        @Override
        public void triple(Triple triple) {
            sink.addTriple(curi, triple);
        }

        @Override
        public void quad(Quad quad) {
            sink.addTriple(curi, quad.asTriple());
        }

    }

}
