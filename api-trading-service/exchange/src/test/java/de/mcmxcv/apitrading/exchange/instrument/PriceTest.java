package de.mcmxcv.apitrading.exchange.instrument;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.Currency;

import org.junit.jupiter.api.Test;

public class PriceTest {
	@Test
	public void price() {
		BigDecimal buy = BigDecimal.ONE;
		BigDecimal sell = buy.subtract(new BigDecimal("0.05"));
		Currency euro = Currency.getInstance("EUR");
		
		Price price = new Price(buy, sell, euro);
		
		assertEquals(buy,  price.buy());
		assertEquals(sell,  price.sell());
		assertEquals(euro,  price.currency());
	}
}
