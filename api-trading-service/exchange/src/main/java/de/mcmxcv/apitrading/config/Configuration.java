package de.mcmxcv.apitrading.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public interface Configuration {
	Properties properties = new Properties();
	
	void loadProperties() throws IOException; 
	
    String getProperty(String key);
    
    default public void loadProperties(String filePath) throws IOException {
    	try (InputStream input = getClass().getClassLoader().getResourceAsStream(filePath)){
    		if (input == null) {
    			throw new IOException(String.format("File not found: %s", filePath));
    		}
    		properties.load(input);
    	}
    }
}
