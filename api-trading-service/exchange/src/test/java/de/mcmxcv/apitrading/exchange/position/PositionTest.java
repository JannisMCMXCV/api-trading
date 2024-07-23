package de.mcmxcv.apitrading.exchange.position;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.internal.progress.MockingProgress;

import de.mcmxcv.apitrading.exchange.Exchange;
import de.mcmxcv.apitrading.exchange.instrument.Instrument;
import de.mcmxcv.apitrading.exchange.instrument.Price;
import de.mcmxcv.apitrading.exchange.riskcontrol.RiskControl;
import de.mcmxcv.apitrading.fixture.InstrumentFixture;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.disposables.Disposable;

public class PositionTest {
	private InstrumentFixture instrumentFixture = new InstrumentFixture("testIs", "testName", null, Currency.getInstance("EUR"));
	private Instrument instrument = instrumentFixture.getDummyInstrumentWithRandomPriceDevelopment();
	private AbstractPosition position = new AbstractPosition(null, null) {

		@Override
		public BigDecimal getProfit() {
			return null;
		}

		@Override
		protected boolean shouldTriggerStopLoss(Price price, RiskControl stopLoss) {
			return false;
		}

		@Override
		protected boolean shouldTriggerTakeProfit(Price price, RiskControl takeProfit) {
			return false;
		}
		
	};
		
	@Test
	public void priceSubscriptionsAreDisposed() {
		Disposable disposable1 = Mockito.mock(Disposable.class);
        Disposable disposable2 = Mockito.mock(Disposable.class);
        position.priceSubscriptions.add(disposable1);
        position.priceSubscriptions.add(disposable2);
		
		List<Disposable> disposables = position.priceSubscriptions;
		position.disposePriceSubscriptions();
		disposables.forEach(disposable -> verify(disposable, times(1)).dispose());        
	}
	
	@Test
	public void disposePriceSubscriptionsIsCalledOnClose() {
		// TODO: Dieser Test macht nicht, was er soll!
		Exchange exchange = Mockito.mock(Exchange.class);
		Mockito.when(exchange.closePosition(Mockito.anyString())).thenReturn(Mockito.mock(Confirmation.class));
		AbstractPosition mockPosition = Mockito.mock(AbstractPosition.class);
		position.close("test");
		verify(position, times(1)).disposePriceSubscriptions();
	}
	
	@Test
	public void testInstrument() {		
		InstrumentFixture fixture = new InstrumentFixture();
		BigDecimal buy = BigDecimal.valueOf(1000);
		BigDecimal sell = BigDecimal.valueOf(995);
		Instrument instrument = fixture.getDummyInstrumentWithTrendingPriceDevelopment("dummy", "name", new Price(buy, sell, null), 1.0, true);
		AtomicReference<Disposable> sub = new AtomicReference<>();
		AtomicReference<Disposable> sub2 = new AtomicReference<>();
		CompletableFuture<Void> hurensohn = CompletableFuture.runAsync(() -> {
			@NonNull
			Disposable dingens = instrument.price().subscribe(System.out::println);
			sub.set(dingens);
			
			try {
				TimeUnit.SECONDS.sleep(5);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
			
			@NonNull
			Disposable bummens = instrument.price().subscribe(System.err::println);
			sub2.set(bummens);
		});
		
		
		
		try {
			TimeUnit.SECONDS.sleep(6);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		
        if (sub.get() != null) {
            System.out.println("disposable: " + sub.get());
            sub.get().dispose();
        } else {
            System.out.println("Disposable is null");
        }
        
        try {
        	TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
        	Thread.currentThread().interrupt();
        }
        
        if (sub2.get() != null) {
            System.out.println("disposable2: " + sub2.get());
            sub2.get().dispose();
        } else {
            System.out.println("Disposable2 is null");
        }
        hurensohn.join();
	}
	
	
	
}
