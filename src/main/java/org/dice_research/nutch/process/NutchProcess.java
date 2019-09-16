package org.dice_research.nutch.process;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public abstract class NutchProcess {
    
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
