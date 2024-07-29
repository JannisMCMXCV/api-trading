package de.mcmxcv.apitrading.fixture;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mcmxcv.apitrading.exchange.Exchange;
import de.mcmxcv.apitrading.exchange.instrument.Instrument;
import de.mcmxcv.apitrading.exchange.instrument.InstrumentDetails;

public class ExchangeFixture {
	private final static Logger logger = LoggerFactory.getLogger(ExchangeFixture.class);
	public Map<String, Instrument> knownInstruments = new HashMap<>();
	
	public ExchangeFixture() {
		registerDefaultInstruments();
	}
	
	public ExchangeFixture(Instrument... expectedInstruments) {
		this();
		this.registerInstruments(expectedInstruments);
	}
	
	public void registerInstruments(Instrument... expectedInstruments) {
		Arrays.stream(expectedInstruments).forEach(this::registerInstrument);
	}

	private void registerDefaultInstruments() {
		Instrument random = InstrumentFixture.getDummyInstrumentWithRandomPriceDevelopment(new InstrumentDetails("default_random", null, null));
		Instrument increasing = InstrumentFixture.getDummyInstrumentWithIncreasingPriceDevelopment(new InstrumentDetails("default_increasing", null, null));
		Instrument decreasing = InstrumentFixture.getDummyInstrumentWithDecreasingPriceDevelopment(new InstrumentDetails("default_decreasing", null, null));
		
		registerInstruments(random, increasing, decreasing);
	}
	
	public void registerInstrument(Instrument instrument) {
		if(knownInstruments.containsKey(instrument.details().id())) {
			logger.warn("Instrument with id {} already exists in known instruments of this ExchangeFixture. Nothing will be added", instrument.details().id());
			return;
		}
		this.knownInstruments.put(instrument.details().id(), instrument);
	}

	public Exchange getExchange() {
		Exchange exchange = Mockito.spy(Exchange.class);
		
		return exchange;
	}
}
