package de.mcmxcv.apitrading.exchange.order.pending;

import java.math.BigDecimal;

import de.mcmxcv.apitrading.exchange.Exchange;
import de.mcmxcv.apitrading.exchange.instrument.Price;
import de.mcmxcv.apitrading.exchange.order.ShortMarketOrder;
import de.mcmxcv.apitrading.exchange.position.Position;

public class ShortLimitStopOrder extends ShortMarketOrder {

	public ShortLimitStopOrder(Exchange exchange, String instrumentId, Price pendingEntryPrice) {
		super(exchange, instrumentId);
		throw new RuntimeException("Not implemented.");
	}
	
	@Override
	public Position place() {
		throw new RuntimeException("Not implemented.");
	}

	@Override
	public BigDecimal getValue() {
		throw new RuntimeException("Not implemented.");
	}
	
	@Override
	public BigDecimal getStake() {
		throw new RuntimeException("Not implemented.");
	}

	@Override
	public void setStake(BigDecimal stake) {
		throw new RuntimeException("Not implemented.");
	}
}
