package de.mcmxcv.apitrading.currency;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Currency;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HybridCurrencyTest {
	private static final Logger logger = LoggerFactory.getLogger(HybridCurrencyTest.class);
	
	@BeforeEach
    public void setUp() {
        HybridCurrency.clearCache();
    }

    @Test
    public void testOf_withFiatCurrencyCode() {
        HybridCurrency usd = HybridCurrency.of("USD");
        assertTrue(usd.isFiat());
        assertEquals("USD", usd.getCurrencyCode());
        assertNotNull(usd.toString());
        logger.info(usd.toString());
    }

    @Test
    public void testOf_withCryptoCurrencyCode() {
        HybridCurrency btc = HybridCurrency.of("BTC");
        assertFalse(btc.isFiat());
        assertEquals("BTC", btc.getCurrencyCode());
        assertNotNull(btc.toString());
        logger.info(btc.toString());
    }

    @Test
    public void testOf_withCurrencyInstance() {
        Currency usdCurrency = Currency.getInstance("USD");
        HybridCurrency usd = HybridCurrency.of(usdCurrency);
        assertTrue(usd.isFiat());
        assertEquals("USD", usd.getCurrencyCode());
        assertNotNull(usd.toString());
    }

    @Test
    public void testIsFiat_withFiatCurrencyCode() {
        assertTrue(HybridCurrency.isFiat("USD"));
    }

    @Test
    public void testIsFiat_withCryptoCurrencyCode() {
        assertFalse(HybridCurrency.isFiat("BTC"));
    }

    @Test
    public void testCachingMechanism() {
        HybridCurrency usd1 = HybridCurrency.of("USD");
        HybridCurrency usd2 = HybridCurrency.of("USD");
        assertSame(usd1, usd2);
    }

    @Test
    public void testEqualsAndHashCode() {
        HybridCurrency usd1 = HybridCurrency.of("USD");
        HybridCurrency usd2 = HybridCurrency.of("USD");
        HybridCurrency btc = HybridCurrency.of("BTC");

        assertEquals(usd1, usd2);
        assertNotEquals(usd1, btc);

        assertEquals(usd1.hashCode(), usd2.hashCode());
        assertNotEquals(usd1.hashCode(), btc.hashCode());
    }
}
