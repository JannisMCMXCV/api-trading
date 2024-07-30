package de.mcmxcv.apitrading.exchange.instrument;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import de.mcmxcv.apitrading.exchange.Exchange;

public class InstrumentDetailsTest {
	@Test
	public void instrumentDetails() {
		String id = "test";
		String name = "testname";
		BigDecimal margin = new BigDecimal("0.2");
		
		InstrumentDetails details = new InstrumentDetails(id, name, margin);
		assertEquals(id, details.id());
		assertEquals(name, details.name());
		assertEquals(margin, details.margin());
	}
}
