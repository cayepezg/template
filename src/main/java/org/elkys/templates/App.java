package org.elkys.templates;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elkys.core.Process;

/**
 *
 */
public class App {
	
    public static void main(String[] args ) throws Exception {
        
    	String jsonTemplate = App.getJsonFile(args[0]);
    	String jsonPayload = App.getJsonFile(args[1]);
    	
    	String jsonResult = Process.modifyJson(jsonTemplate, jsonPayload);
    	
    	JsonNode jsonNodeResult = (new ObjectMapper()).readTree(jsonResult);
    	System.err.println(jsonNodeResult.toPrettyString());
    	
    }
    
    /**
     * Solo para fectos de pruebas con archivos locales.
     * @param filePath
     * @return
     * @throws IOException
     */
    private static String getJsonFile(String filePath) throws IOException {
        try {
        	return new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            throw e;
        }
    }
    
    
    
    
}
