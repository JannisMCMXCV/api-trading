package de.mcmxcv.apitrading.exchange.instrument;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import de.mcmxcv.apitrading.currency.HybridCurrency;

public class PriceTest {
	@Test
	public void price() {
		BigDecimal buy = BigDecimal.ONE;
		BigDecimal sell = buy.subtract(new BigDecimal("0.05"));
		HybridCurrency euro = HybridCurrency.of("EUR");
		
		Price price = new Price(buy, sell, euro);
		
		assertEquals(buy,  price.buy());
		assertEquals(sell,  price.sell());
		assertEquals(euro,  price.currency());
		assertTrue(euro.isFiat());
	}
}
