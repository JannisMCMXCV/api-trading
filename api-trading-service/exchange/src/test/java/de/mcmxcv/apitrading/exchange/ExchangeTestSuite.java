package de.mcmxcv.apitrading.exchange;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import de.mcmxcv.apitrading.exchange.instrument.Instrument;

public abstract class ExchangeTestSuite {
	
	public abstract Exchange getExchange();
	public abstract String getSymbol();
	
	@Test
	public void getInstrument() {
		Instrument instrument = getExchange().getInstrument(getSymbol());
		assertNotNull(instrument);
	}
}
