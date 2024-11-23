package de.mcmxcv.apitrading.adapter.binance.parser.json;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.mcmxcv.apitrading.currency.HybridCurrency;
import de.mcmxcv.apitrading.exchange.instrument.Price;

public record BinanceSpotPrice(String json) implements BinanceResponse {
	public Price parsePrice() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			JsonNode root = mapper.readTree(json);
			JsonNode data = getDataNode(mapper, root);
			BigDecimal price = getPrice(data);
			HybridCurrency currency = getCurrency(data);
			return new Price(price, price, currency);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private JsonNode getDataNode(ObjectMapper mapper, JsonNode root)
			throws JsonProcessingException, JsonMappingException {
		List<String> dataStr = root.findValuesAsText("data");
		JsonNode data = mapper.readTree(dataStr.getFirst());
		return data;
	}
	
	private BigDecimal getPrice(JsonNode data) {
		JsonNode price = data.findValue("price");
		return new BigDecimal(price.asText());
	}
	
	private HybridCurrency getCurrency(JsonNode data) {
		JsonNode symbol = data.findValue("symbol");
		return HybridCurrency.of(symbol.asText());
	}
	
	@Override
	public int parseUsedWeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int parseUsedWeight1m() {
		// TODO Auto-generated method stub
		return 0;
	}

}
