package de.mcmxcv.apitrading.exchange.instrument;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import de.mcmxcv.apitrading.currency.HybridCurrency;
import de.mcmxcv.apitrading.exchange.Exchange;
import de.mcmxcv.apitrading.fixture.InstrumentFixture;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;

public class InstrumentTest {
	@Test
	public void instrument() {		
		Price price1 = new Price(BigDecimal.ONE, BigDecimal.ONE, HybridCurrency.of("EUR"));
		Price price2 = new Price(BigDecimal.TWO, BigDecimal.TWO, HybridCurrency.of("EUR"));
		Price price3 = new Price(BigDecimal.TEN, BigDecimal.TEN, HybridCurrency.of("EUR"));
		List<Price> prices = List.of(price1, price2,price3);
	
		String instrumentId = "test";
		String instrumentName = "testInstrument";
		BigDecimal margin = new BigDecimal("0.2");
		
		InstrumentDetails details = new InstrumentDetails(instrumentId, instrumentName, margin);
		
		
		Instrument instrument = new Instrument(details, Observable.create(e -> prices.forEach(e::onNext)));
		
		List<Price> receivedPrices = new LinkedList<>();
		instrument.price().subscribe(receivedPrices::add);
		assertTrue(receivedPrices.contains(price1));
		assertTrue(receivedPrices.contains(price2));
		assertTrue(receivedPrices.contains(price3));
		
		assertEquals(instrumentId, instrument.details().id());
		assertEquals(instrumentName, instrument.details().name());
	}
	
	@Test
	public void subscribeUnsubscribe() {
		int subscriptionPeriod = 1;
		
		InstrumentFixture fixture = new InstrumentFixture();
		Instrument instrument = fixture.getDummyInstrumentWithRandomPriceDevelopment(new InstrumentDetails("testDetails", null, null));
		AtomicInteger emissionCount = new AtomicInteger(0);
		
		CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
		
			@NonNull
			Disposable subscription = instrument.price().subscribe(price -> {
				System.out.println(emissionCount.get() + ": " + price);
				emissionCount.getAndIncrement();
			}, Assertions::fail);
			
			try {
				TimeUnit.SECONDS.sleep(subscriptionPeriod);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			subscription.dispose();	
		});
		
		try {
			// + 1 Second for tolerance
			future.get(subscriptionPeriod + 1, TimeUnit.SECONDS);
		} catch (InterruptedException | ExecutionException | TimeoutException e) {	
			fail("Could not successfully unsubscribe from Instrument", e);
		}
		assertTrue(emissionCount.get() > 2);
	}
}
