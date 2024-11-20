package de.mcmxcv.apitrading.exchange;

import de.mcmxcv.apitrading.exchange.instrument.Instrument;
import de.mcmxcv.apitrading.exchange.instrument.Price;
import io.reactivex.rxjava3.core.Observable;

public interface Exchange {
	String[] getSymbols();
	String[] getSymbols(String contains);
	Instrument getInstrument(String symbol);
	Observable<Price> subscribeInstrument(String symbol);
	
	
}
