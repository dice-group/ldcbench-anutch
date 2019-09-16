package org.dice_research.nutch.process;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

public class CrawlerProcess extends NutchProcess{
    
    private int num_rounds = 3;
    
    
    public CrawlerProcess(List<String> listUris,String base_dir,int num_rounds) {
    	super(base_dir);
    	this.num_rounds = num_rounds;
        generateSeeds(listUris);
    }
    

    @Override
    public void prepareProcess() {
        
        String crawl_cmd = getBaseDir() + "/crawl -s " 
                + getBaseDir() + "/urls/ "
                +  getBaseDir() + "/crawled " + String.valueOf(num_rounds);
        
        processBuilder = new ProcessBuilder();
        processBuilder.command("bash","-c",crawl_cmd);
    }
    
    
    private void generateSeeds(List<String> listUris) {
        try {
            File file = new File(getBaseDir() + "/urls/seed.txt");
            FileOutputStream fos = new FileOutputStream(file);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
            for(String uri: listUris) {
                bw.write(uri);
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    
    



}
