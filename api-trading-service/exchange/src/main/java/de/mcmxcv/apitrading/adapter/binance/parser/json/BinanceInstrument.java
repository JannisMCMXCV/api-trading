package de.mcmxcv.apitrading.adapter.binance.parser.json;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.mcmxcv.apitrading.adapter.binance.Binance;
import de.mcmxcv.apitrading.currency.HybridCurrency;
import de.mcmxcv.apitrading.exchange.Exchange;
import de.mcmxcv.apitrading.exchange.instrument.Instrument;
import de.mcmxcv.apitrading.exchange.instrument.Price;

public record BinanceInstrument(String json, Binance binance) implements BinanceResponse{
	public Instrument parseInstrument() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			JsonNode root = mapper.readTree(json);
			JsonNode data = getDataNode(mapper, root);
			
			String symbol = getSymbol(data);
			String name = getName(data);
			boolean tradable = getTradable(data);
			BigDecimal margin = getMargin(data);
			BigDecimal minQuantity = getMinQuantity(data);
			BigDecimal maxQuantity = getMaxQuantity(data);
			Map<Instant, Price> currentPrice = getCurrentPrice(data);
			
			return new Instrument(binance, symbol, name, tradable, margin, minQuantity,maxQuantity, currentPrice);
			
		} catch (JsonMappingException e) {
			e.printStackTrace(); // TODO: better error handling
		} catch (JsonProcessingException e) {
			e.printStackTrace(); // TODO: better error handling
		}
		return null;
	}

	private JsonNode getDataNode(ObjectMapper mapper, JsonNode root)
			throws JsonProcessingException, JsonMappingException {
		List<String> dataStr = root.findValuesAsText("data");
		JsonNode data = mapper.readTree(dataStr.getFirst());
		return data;
	}

	private String getSymbol(JsonNode data) {
		JsonNode symbol = data.findValue("symbol");
		return symbol.asText();
	}
	
	private String getName(JsonNode data) {
		return getSymbol(data);
	}

	private boolean getTradable(JsonNode data) {
		JsonNode status = data.findValue("status");
		return status.asText().equals("TRADING");
	}

	private BigDecimal getMargin(JsonNode data) {
		JsonNode allowed = data.findValue("isMarginTradingAllowed");
		if(allowed.asBoolean()) {
			// TODO: getMargin();
		}
		return BigDecimal.ONE; //if there is no Margin, Margin is 1.
	}

	private BigDecimal getMinQuantity(JsonNode data) {
		JsonNode min = data.findValue("minQty");
		return new BigDecimal(min.asText());
	}

	private BigDecimal getMaxQuantity(JsonNode data) {
		JsonNode max = data.findValue("maxQty");
		return new BigDecimal(max.asText());
	}

	private Map<Instant, Price> getCurrentPrice(JsonNode data) {
		Instant timestamp = getTimeStamp(data);

//		TODO: richtigen Preis ermitteln!
		Price price = new Price(BigDecimal.ZERO, BigDecimal.ZERO, HybridCurrency.of(getSymbol(data)));
		Map<Instant, Price> priceMap = new HashMap<>();
		priceMap.put(timestamp, price);
		return priceMap;
	}

	private Instant getTimeStamp(JsonNode data) {
		JsonNode serverTimeNode = data.findValue("serverTime");
		long serverTime = serverTimeNode.asLong();
		return Instant.ofEpochMilli(serverTime);
	}

	@Override
	public int parseUsedWeight() {
		
		return 0;
	}

	@Override
	public int parseUsedWeight1m() {
		// TODO Auto-generated method stub
		return 0;
	}
}
