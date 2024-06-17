package de.mcmxcv.apitrading.exchange.instrument;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Test;

import io.reactivex.rxjava3.core.Observable;

public class TestInstrument {
	@Test
	public void instrument() {
		String instrumentId = "test";
		String instrumentName = "testInstrument";
		
		Price price1 = new Price(BigDecimal.ONE, BigDecimal.ONE, Currency.getInstance("EUR"));
		Price price2 = new Price(BigDecimal.TWO, BigDecimal.TWO, Currency.getInstance("EUR"));
		Price price3 = new Price(BigDecimal.TEN, BigDecimal.TEN, Currency.getInstance("EUR"));
		List<Price> prices = List.of(price1, price2,price3);
	
		Instrument instrument = new Instrument("test", "testInstrument", Observable.create(e -> prices.forEach(e::onNext)));
		
		List<Price> receivedPrices = new LinkedList<>();
		instrument.price().subscribe(receivedPrices::add);
		assertTrue(receivedPrices.contains(price1));
		assertTrue(receivedPrices.contains(price2));
		assertTrue(receivedPrices.contains(price3));
		
		assertEquals(instrumentId, instrument.id());
		assertEquals(instrumentName, instrument.name());
	}
}
