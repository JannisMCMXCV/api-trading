package de.mcmxcv.apitrading.exchange.instrument;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;

import de.mcmxcv.apitrading.exchange.Exchange;
import io.reactivex.rxjava3.core.Observable;

public record Instrument(
		Exchange exchange,
		String symbol,
		String name,
		boolean tradable,
		BigDecimal margin,
		BigDecimal minQuantity,
		BigDecimal maxQuantity,
		Map<Instant, Price> currentPrice){
	
	public Observable<Price> subscribe() {
		return this.exchange.subscribeInstrument(this.symbol);
	}
}
