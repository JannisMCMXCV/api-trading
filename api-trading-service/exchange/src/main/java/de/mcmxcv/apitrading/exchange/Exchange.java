package de.mcmxcv.apitrading.exchange;

import de.mcmxcv.apitrading.exchange.instrument.Instrument;

public interface Exchange {
	String[] getSymbols();
	String[] getSymbols(String contains);
	Instrument getInstrument(String symbol);
	
}
