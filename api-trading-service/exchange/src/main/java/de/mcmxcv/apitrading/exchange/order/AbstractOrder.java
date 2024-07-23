package de.mcmxcv.apitrading.exchange.order;

import java.math.BigDecimal;

import de.mcmxcv.apitrading.exchange.Exchange;
import de.mcmxcv.apitrading.exchange.instrument.Instrument;
import de.mcmxcv.apitrading.exchange.instrument.Price;
import de.mcmxcv.apitrading.exchange.order.LongMarketOrder.Builder;
import de.mcmxcv.apitrading.exchange.position.Position;
import de.mcmxcv.apitrading.exchange.riskcontrol.RiskControl;

public abstract class AbstractOrder implements Order {
	private final Price aimedEntryPrice;
	private final Instrument instrument;
	private final Exchange exchange;
	private BigDecimal quantity;
	private RiskControl stopLoss;
	private RiskControl takeProfit;
	
	public AbstractOrder(Exchange exchange, String instrumentId) {
		this.exchange = exchange;
		this.instrument = exchange.getInstrument(instrumentId);
		aimedEntryPrice = determineAimedEntryPrice();
	}	
	
	public abstract Position place();
	
	public abstract BigDecimal getValue();
	
	public abstract BigDecimal getStake();
	
	public abstract void setStake(BigDecimal stake);
	
	public Instrument getInstrument() {
		return instrument;
	}
	
	public Price getAimedEntryPrice() {
		return aimedEntryPrice;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public RiskControl getStopLoss() {
		return stopLoss;
	}

	public void setStopLoss(RiskControl stopLoss) {
		this.stopLoss = stopLoss;
	}

	public RiskControl getTakeProfit() {
		return takeProfit;
	}

	protected Exchange getExchange() {
		return this.exchange;
	}

	public void setTakeProfit(RiskControl takeProfit) {
		this.takeProfit = takeProfit;
	}
	
	private Price determineAimedEntryPrice() {
		Price currentPrice = this.exchange.getCurrentPrice(getInstrument().details().id());
		return currentPrice;
	}

	public static abstract class Builder implements de.mcmxcv.builder.Builder<AbstractOrder> {
		private AbstractOrder order;
		
		public Builder(Exchange exchange, String instrumentId) {
			this.order = getOrder(exchange, instrumentId);
		}
		
		protected abstract AbstractOrder getOrder(Exchange exchange, String instrumentId);
		
		@Override
		public AbstractOrder build() {
			return this.order;
		}

		public Builder quantity(BigDecimal quantity) {
			this.order.setQuantity(quantity);
			return this;
		}

		public Builder stake(BigDecimal stake) {
			this.order.setStake(stake);
			return this;
		}

		public Builder stopLoss(RiskControl stopLoss) {
			this.order.setStopLoss(stopLoss);
			return this;
		}

		public Builder takeProfit(RiskControl takeProfit) {
			this.order.setTakeProfit(takeProfit);
			return this;
		}
	}
}
