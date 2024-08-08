package de.mcmxcv.apitrading.impl.binance;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.time.Instant;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import de.mcmxcv.apitrading.client.Http;
import de.mcmxcv.apitrading.exchange.Direction;
import de.mcmxcv.apitrading.exchange.Exchange;
import de.mcmxcv.apitrading.exchange.account.Account;
import de.mcmxcv.apitrading.exchange.instrument.Instrument;
import de.mcmxcv.apitrading.exchange.instrument.Price;
import de.mcmxcv.apitrading.exchange.position.Position;

public class Binance implements Exchange {
	private final Http client;

	private final static Config config = new Config();

	public static void main(String... authClient) throws IOException, InterruptedException {
		Binance binance = new Binance();
		
		Map<String, String> params = new LinkedHashMap<>();
		params.put("omitZeroBalances", "true");
		params.put("recvWindow", "1000");
		params.put("timestamp", String.valueOf(System.currentTimeMillis()));
		
		String paramsUnsigned = binance.convertMapToString(params);
		
		params.put("signature", binance.sign(paramsUnsigned));
		
		Map<String, String> headers = new HashMap<>();
		headers.put("X-MBX-APIKEY", config.getApiKey());
		
		HttpResponse<String> bittebitte = binance.client.get(MessageFormat.format("{0}/v3/account", config.getBaseUrl()), params, headers);
		
		System.out.println(bittebitte.body());
		System.out.println(new String(config.getSecret(), StandardCharsets.UTF_8));
	}
	
	private String convertMapToString(Map<String, String> map) {
        return map.entrySet()
                  .stream()
                  .map(entry -> {
                	  try {
                		  return URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8.name()) + "=" + URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8.name());
                	  } catch (UnsupportedEncodingException e) {
                		  throw new RuntimeException(e);
                	  }
                  })
                  .collect(Collectors.joining("&"));
    }

	private String sign(String message) {
        try { 
            byte[] keyBytes = config.getSecret();
            
            SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "HmacSHA256");
            
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(secretKeySpec);
            
            byte[] rawHmac = mac.doFinal(message.getBytes(StandardCharsets.UTF_8));
            
            return bytesToHex(rawHmac);
    
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
	}
	
	private String bytesToHex(byte[] bytes) {
	    StringBuilder result = new StringBuilder();
	    
	    for (byte b : bytes) {
	        result.append(String.format("%02x", b));
	    }
	    return result.toString();
	}


	public Binance() {
		client = new Http();
	}

	@Override
	public Instrument getInstrument(String instrumentId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Instrument> getInstruments() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Instrument> getInstruments(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isTradable(String instrument) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isShortable(String instrument) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Price getCurrentPrice(String instrument) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Account getAccount() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Instant[] getTradingHours(String instrumentId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Position openPosition(String instrumentId, BigDecimal quantity, Direction direction) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean closePosition(String positionId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Collection<String> getOpenPositions() {
		// TODO Auto-generated method stub
		return null;
	}

}
