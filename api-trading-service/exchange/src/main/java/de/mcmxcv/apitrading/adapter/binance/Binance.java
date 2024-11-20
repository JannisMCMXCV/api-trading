package de.mcmxcv.apitrading.adapter.binance;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.net.http.HttpHeaders;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.text.MessageFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.binance.connector.client.SpotClient;
import com.binance.connector.client.impl.SpotClientImpl;
import com.binance.connector.client.utils.ParameterChecker;
import com.binance.connector.client.utils.signaturegenerator.Ed25519SignatureGenerator;

import de.mcmxcv.apitrading.client.RestClient;
import de.mcmxcv.apitrading.exchange.Exchange;
import de.mcmxcv.apitrading.exchange.account.Account;
import de.mcmxcv.apitrading.exchange.account.AccountSnapshot;
import de.mcmxcv.apitrading.exchange.instrument.Instrument;
import de.mcmxcv.apitrading.exchange.instrument.InstrumentDetails;
import de.mcmxcv.apitrading.exchange.instrument.Price;
import de.mcmxcv.util.Utils;

@SuppressWarnings("unused")
public class Binance implements Exchange {
	private static final Logger logger = LoggerFactory.getLogger(Binance.class);


	private final SpotClientImpl binanceClient;

	private final static Config config = new Config();

	public static void main(String... authClient) throws IOException, InterruptedException {
		Binance binance = new Binance();
		binance.getInstrument("BNBUSDT");
	}

	public Binance() {
		try {
			binanceClient = new SpotClientImpl(config.getApiKey(),
					new Ed25519SignatureGenerator(config.getSecretPath()), config.getBaseUrl());
		} catch (IOException e) {
			throw new RuntimeException("could not read from Secret Key.", e);
		}
		binanceClient.setShowLimitUsage(true);
	}

	@Override
	public Instrument getInstrument(String symbol) {
		
		 Map<String, Object> parameters = new LinkedHashMap<>();
	        parameters.put("symbol", "BNBUSDT");

	    String response = Utils.tryFixCorruptedJson(Utils.tryFixCorruptedJson(binanceClient.createMarket().exchangeInfo(parameters)) );
		System.out.println(response);
		return null;
	}

	@Override
	public String[] getSymbols() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getSymbols(String contains) {
		// TODO Auto-generated method stub
		return null;
	}




}
