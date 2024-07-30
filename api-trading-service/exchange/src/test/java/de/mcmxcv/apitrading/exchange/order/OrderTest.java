package de.mcmxcv.apitrading.exchange.order;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import de.mcmxcv.apitrading.exchange.instrument.Instrument;
import de.mcmxcv.apitrading.exchange.instrument.InstrumentDetails;
import de.mcmxcv.apitrading.exchange.instrument.Price;
import de.mcmxcv.apitrading.exchange.order.LongMarketOrder.Builder;
import de.mcmxcv.apitrading.exchange.riskcontrol.InterpretationInfo;
import de.mcmxcv.apitrading.exchange.riskcontrol.RiskControl;
import de.mcmxcv.apitrading.fixture.ExchangeFixture;
import de.mcmxcv.apitrading.fixture.InstrumentFixture;

public class OrderTest {
	private final static BigDecimal START_PRICE_BUY = new BigDecimal(1_000);
	private final static BigDecimal START_PRICE_SELL = START_PRICE_BUY.subtract(BigDecimal.TWO);
	private final static Price START_PRICE = new Price(START_PRICE_BUY, START_PRICE_SELL, null);
	private final static ExchangeFixture exFix = new ExchangeFixture();
	
	private final static Instrument  DUMMY_INSTRUMENT = InstrumentFixture.getDummyInstrumentWithTrendingPriceDevelopment(new InstrumentDetails("test_instrument", null, START_PRICE_BUY) , new Price(START_PRICE_BUY, START_PRICE_SELL, null), 1, false);
	
	@BeforeAll
	public static void setUp() {
		exFix.registerInstrument(DUMMY_INSTRUMENT);
	}
	
	@Test
	public void longMarketOrderBuilder() {
		BigDecimal quantity = BigDecimal.TEN;
		RiskControl stopLoss = new RiskControl.Builder().threshold(new BigDecimal(500).negate()).as(InterpretationInfo.PROFIT).build();
		RiskControl takeProfit = new RiskControl.Builder().threshold(new BigDecimal(5_000)).as(InterpretationInfo.PROFIT).build();
		
		Builder orderBuilder = new LongMarketOrder.Builder(exFix.getExchange(), DUMMY_INSTRUMENT.details().id());
		AbstractOrder order = orderBuilder.quantity(quantity)
				.stopLoss(stopLoss)
				.takeProfit(takeProfit)
				.build();
		
		assertEquals(quantity, order.getQuantity());
	}
}
