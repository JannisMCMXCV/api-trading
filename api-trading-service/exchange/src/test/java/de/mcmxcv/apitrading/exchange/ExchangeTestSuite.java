package de.mcmxcv.apitrading.exchange;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import de.mcmxcv.apitrading.exchange.instrument.Instrument;
import de.mcmxcv.apitrading.exchange.instrument.Price;

public abstract class ExchangeTestSuite {
	
	public abstract Exchange getExchange();
	public abstract String getSymbol();
	
	@Test
	public void getInstrument() {
		Instrument instrument = getExchange().getInstrument(getSymbol());
		System.out.println("Test Instrument: " + instrument);
		assertNotNull(instrument);
	}
	
	@Test
	void getInstrumentPrice() {
		Price price = getExchange().getInstrumentPrice(getSymbol());
		System.out.println("Test Price: " + price);
		assertTrue(price.buy().compareTo(BigDecimal.ZERO) > 0);
		assertTrue(price.sell().compareTo(BigDecimal.ZERO) > 0);
	}
}
