package de.mcmxcv.apitrading.exchange.position;

import java.math.BigDecimal;

import de.mcmxcv.apitrading.exchange.Exchange;
import de.mcmxcv.apitrading.exchange.instrument.Instrument;
import de.mcmxcv.apitrading.exchange.instrument.Price;
import de.mcmxcv.apitrading.exchange.riskcontrol.RiskControl;

public class ShortPosition extends AbstractPosition {

	protected ShortPosition(Instrument instrument, Exchange exchange) {
		super(instrument, exchange);
		// TODO Auto-generated constructor stub
	}
	
	protected ShortPosition(Instrument instrument, Exchange exchange, RiskControl stopLoss, RiskControl takeProfit) {
		super(instrument, exchange, stopLoss, takeProfit);
		// TODO Auto-generated constructor stub
	}

	@Override
	public BigDecimal getProfit() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected boolean shouldTriggerStopLoss(Price price, RiskControl stopLoss) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean shouldTriggerTakeProfit(Price price, RiskControl takeProfit) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setStopLoss(RiskControl stopLoss) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setTakeProfit(RiskControl takeProfit) {
		// TODO Auto-generated method stub

	}

}
