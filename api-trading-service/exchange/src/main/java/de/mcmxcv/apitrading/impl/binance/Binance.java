package de.mcmxcv.apitrading.impl.binance;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.text.MessageFormat;
import java.time.Instant;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mcmxcv.apitrading.client.Http;
import de.mcmxcv.apitrading.exchange.Direction;
import de.mcmxcv.apitrading.exchange.Exchange;
import de.mcmxcv.apitrading.exchange.account.Account;
import de.mcmxcv.apitrading.exchange.account.AccountSnapshot;
import de.mcmxcv.apitrading.exchange.instrument.Instrument;
import de.mcmxcv.apitrading.exchange.instrument.InstrumentSnapshot;
import de.mcmxcv.apitrading.exchange.instrument.Price;
import de.mcmxcv.apitrading.exchange.position.Position;
import de.mcmxcv.apitrading.impl.binance.account.AccountParser;

public class Binance implements Exchange {
	private static final Logger logger = LoggerFactory.getLogger(Binance.class);
	
	private static final String API_VERION = "v3";
	private static final String ACCOUNT_PATH = "account";
	
	private static final String API_KEY_KEY = "X-MBX-APIKEY";
	
	
	private final Http client;

	private final static Config config = new Config();

	public static void main(String... authClient) throws IOException, InterruptedException {
		Binance binance = new Binance();
		AccountSnapshot account = binance.getAccountSnapshot();
		Arrays.stream(account.balances()).forEach(balance -> System.out.println(balance.available()));
	}
	
	private String mapToString(Map<String, String> map) {
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
		// TODO: This Method currently only signs Ed25519 keys. 
        try { 
            byte[] keyBytes = config.getSecret();
            
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);

            KeyFactory keyFactory = KeyFactory.getInstance("Ed25519");
            PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

            Signature signature = Signature.getInstance("Ed25519");
            signature.initSign(privateKey);
            signature.update(message.getBytes(StandardCharsets.US_ASCII));

            byte[] signedMessage = signature.sign();
            return Base64.getEncoder().encodeToString(signedMessage);
    
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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

	@Override
	public Instrument getInstrumentSnapshot(String instrumentId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<InstrumentSnapshot> getInstrumentSnapshots() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<InstrumentSnapshot> getInstrumentSnapshots(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AccountSnapshot getAccountSnapshot() {
		Map<String, String> params = new LinkedHashMap<>();
		params.put("omitZeroBalances", "true");
		params.put("recvWindow", "1500");
		params.put("timestamp", String.valueOf(System.currentTimeMillis()));	
		
		signParams(params);
		
		Map<String, String> headers = new HashMap<>();
		headers.put(API_KEY_KEY, config.getApiKey());
		
		try {
			HttpResponse<String> accountResponse = client.get(MessageFormat.format("{0}/v3/account", config.getBaseUrl()), params, headers);
			String jsonResponse = accountResponse.body();
			return AccountParser.parseAccount(jsonResponse);
		} catch (IOException | InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	private void signParams(Map<String, String> params) {
		String paramsUnsigned = mapToString(params);
		params.put("signature", sign(paramsUnsigned));
	}

}
