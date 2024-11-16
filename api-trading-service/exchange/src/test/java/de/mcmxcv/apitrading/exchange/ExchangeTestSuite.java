package de.mcmxcv.apitrading.exchange;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import de.mcmxcv.apitrading.exchange.account.Account;

public abstract class ExchangeTestSuite {
	
	public abstract Exchange getExchange();
	
	@Test
	public void getSymbols() {
		getExchange().getSymbols();
		assertNotEmpty
	}
}
