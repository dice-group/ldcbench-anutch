package org.dice_research.nutch.process;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class NutchProcess {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(NutchProcess.class);

    
    private File file_dir;
    protected ProcessBuilder processBuilder;
    
    public NutchProcess(String base_dir) {
    	file_dir = new File(base_dir);
	}
    
    protected String getBaseDir() {
        return file_dir.getAbsolutePath();
    }
    
    public abstract void prepareProcess();

    public void startProcess() {
        try {
            Process process = processBuilder.inheritIO().start();
    
            StringBuilder output = new StringBuilder();
    
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));
    
            String line;
            while ((line = reader.readLine()) != null) {
                
                output.append(line + "\n");
            }
            LOGGER.info(output.toString());
            int exitVal = process.waitFor();
            if (exitVal == 0) {
                System.out.println("Success!");
            } else {
                System.out.println("Error!");
            }
        }catch(IOException | InterruptedException e) {
            
        }
    }

}
