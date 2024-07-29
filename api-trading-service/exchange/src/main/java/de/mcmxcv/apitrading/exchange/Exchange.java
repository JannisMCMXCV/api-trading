package de.mcmxcv.apitrading.exchange;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collection;
import java.util.List;

import de.mcmxcv.apitrading.exchange.account.Account;
import de.mcmxcv.apitrading.exchange.instrument.Instrument;
import de.mcmxcv.apitrading.exchange.instrument.Price;
import de.mcmxcv.apitrading.exchange.position.Position;

public interface Exchange {
	Instrument getInstrument(String instrumentId);
	List<Instrument> getInstruments();
	List<Instrument> getInstruments(String name);
	boolean isTradable(String instrument);
	boolean isShortable(String instrument);
	Price getCurrentPrice(String instrument);
	Account getAccount();
	
	/**
	 * returns an array with two Instant objects which represent
	 * the current or upcoming trading session.
	 * [0] = begin timestamp when the instrument opened or will open for trades
	 * [1] = end timestamp when the instrument closed or will close for trades 
	 * @param instrumentId
	 * @return
	 */
	Instant[] getTradingHours(String instrumentId);
	Position openPosition(String instrumentId, BigDecimal quantity, Direction direction);
	boolean closePosition(String positionId);
	Collection<String> getOpenPositions();
}
