package de.mcmxcv.apitrading.exchange;

import java.util.List;

import de.mcmxcv.apitrading.exchange.instrument.Instrument;

public interface Exchange {
	public Instrument getInstrument(String Id);
	public List<Instrument> getInstruments();
	public List<Instrument> getInstruments(String Name);	
}
