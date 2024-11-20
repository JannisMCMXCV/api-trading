package de.mcmxcv.apitrading.adapter.binance;

import java.io.FileReader;
import java.io.IOException;
import java.text.MessageFormat;

import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;

import de.mcmxcv.apitrading.config.Configuration;

public class Config implements Configuration{
	private final static String DEFAULT_PATH = "binance/binance.properties";
	
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
		// TODO: currently only Ed25519 Keys keys are supported!
		return getSecretFromFile(); 
	}

	private byte[] getSecretFromFile() {
		String filePath = getSecretPath();
		try (PemReader pemReader = new PemReader(new FileReader(filePath))) {
            PemObject pemObject = pemReader.readPemObject();
            return pemObject.getContent();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public String getSecretPath() {
		String filePath = getProperty("secretKeyPath");
		if (filePath.startsWith("~")) {
			return MessageFormat.format("{0}{1}", System.getProperty("user.home"), filePath.substring(1));
		}
		return filePath;
	}
}
