package de.mcmxcv.apitrading.adapter.binance;

import de.mcmxcv.apitrading.exchange.Exchange;
import de.mcmxcv.apitrading.exchange.ExchangeTestSuite;

public class TestBinance extends ExchangeTestSuite{

	@Override
	public Exchange getExchange() {
		return new Binance();
	}

	@Override
	public String getSymbol() {
		return "BNBUSDT";
	}

}
