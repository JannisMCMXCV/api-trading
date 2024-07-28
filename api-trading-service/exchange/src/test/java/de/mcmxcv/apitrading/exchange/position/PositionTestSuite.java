package de.mcmxcv.apitrading.exchange.position;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.mcmxcv.apitrading.exchange.Exchange;

public abstract class PositionTestSuite {
	private Position position;
	
	protected abstract Position getPosition();
	protected abstract Exchange getExchange();
	
	@BeforeEach
	public void before() {
		this.position = getPosition();
	}
	
	@Test
	public void close() {
		position.close();
		assertTrue(position.isClosed());
	}
	
}
