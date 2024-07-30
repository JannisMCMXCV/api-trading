package de.mcmxcv.apitrading.exchange.order;

import java.math.BigDecimal;

import de.mcmxcv.apitrading.exchange.Exchange;
import de.mcmxcv.apitrading.exchange.position.Position;

public class ShortMarketOrder extends AbstractOrder {

	public ShortMarketOrder(Exchange exchange,  String instrumentId) {
		super(exchange, instrumentId);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Position place() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal getValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setStake(BigDecimal stake) {
		// TODO Auto-generated method stub

	}

	@Override
	public BigDecimal getStake() {
		// TODO Auto-generated method stub
		return null;
	}

}
