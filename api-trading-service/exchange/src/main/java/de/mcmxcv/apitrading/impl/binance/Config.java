package de.mcmxcv.apitrading.impl.binance;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.MessageFormat;

import de.mcmxcv.apitrading.config.Configuration;

public class Config implements Configuration{
	private final static String DEFAULT_PATH = "binance.properties";
	
	public Config() {
		try {
			loadProperties();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void loadProperties() throws IOException {
		loadProperties(DEFAULT_PATH);
	}
	
	@Override
	public String getProperty(String key) {
		return properties.getProperty(key);
	}
	
	public String getBaseUrl() {
		return getProperty("baseUrl");	
	}
	
	public String getApiKey() {
		return getProperty("apiKey");
	}

	public byte[] getSecret() {		
		return !getProperty("secretKey").isBlank()?  getProperty("secretKey").getBytes(StandardCharsets.UTF_8) : getSecretFromFile(); 
	}

	private byte[] getSecretFromFile() {
		String filePath = getFilePath();
        try {
			return Files.readAllBytes(Paths.get(filePath));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
	}

	private String getFilePath() {
		String filePath = getProperty("secretKeyPath");
		if (filePath.startsWith("~")) {
			return MessageFormat.format("{0}{1}", System.getProperty("user.home"), filePath.substring(1));
		}
		return filePath;
	}
}
