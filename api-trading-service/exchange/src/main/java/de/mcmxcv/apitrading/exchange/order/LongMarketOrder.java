package de.mcmxcv.apitrading.exchange.order;

import java.math.BigDecimal;

import de.mcmxcv.apitrading.exchange.Direction;
import de.mcmxcv.apitrading.exchange.Exchange;
import de.mcmxcv.apitrading.exchange.instrument.Instrument;
import de.mcmxcv.apitrading.exchange.position.Position;
import de.mcmxcv.apitrading.exchange.riskcontrol.RiskControl;

public class LongMarketOrder extends AbstractOrder {
	private final static Direction DIRECTION = Direction.LONG;
	
	public LongMarketOrder(Exchange exchange, String instrumentId) {
		super(exchange, instrumentId);
	}

	@Override
	public Position place() {
		return getExchange().openPosition(getInstrument().details().id(), getQuantity(), DIRECTION);
	}

	@Override
	public BigDecimal getValue() {
		return getQuantity().multiply(getAimedEntryPrice().sell());
	}
	
	@Override
	public BigDecimal getStake() {
		return getQuantity().multiply(getAimedEntryPrice().buy()).multiply(getMargin());
	}

	private BigDecimal getMargin() {
		return getInstrument().details().margin();
	}

	@Override
	public void setStake(BigDecimal stake) {
		setQuantity(stake.divide(getMargin()).divide(getAimedEntryPrice().buy()));
	}
	
	public static class Builder extends de.mcmxcv.apitrading.exchange.order.AbstractOrder.Builder {
		public Builder(Exchange exchange, String instrumentId) {
			super(exchange, instrumentId);
		}

		@Override
		protected AbstractOrder getOrder(Exchange exchange, String instrumentId) {
			return new LongMarketOrder(exchange, instrumentId);
		}	
		
	}

}
