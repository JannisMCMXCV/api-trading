package de.mcmxcv.apitrading.adapter.binance;

import de.mcmxcv.apitrading.exchange.Exchange;
import de.mcmxcv.apitrading.exchange.ExchangeTestSuite;

public class TestBinanceSpot extends ExchangeTestSuite{

	@Override
	public Exchange getExchange() {
		return new BinanceSpot();
	}

	@Override
	public String getSymbol() {
		return "BTCFDUSD";
	}

}
