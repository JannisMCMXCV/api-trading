package de.mcmxcv.apitrading.exchange.position;

import java.math.BigDecimal;

import de.mcmxcv.apitrading.exchange.instrument.Instrument;
import de.mcmxcv.apitrading.exchange.instrument.Price;
import de.mcmxcv.apitrading.exchange.riskcontrol.RiskControl;

public interface Position {
	Instrument getInstrument();
	Price getEntryPrice();
	Price getExitPrice();
	RiskControl getStopLoss();
	RiskControl getTakeProfit();
	BigDecimal getProfit();
	boolean isClosed();
	void close();
	void close(String reason);
}
