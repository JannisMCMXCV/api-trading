package de.mcmxcv.apitrading.fixture;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

import de.mcmxcv.apitrading.exchange.instrument.Instrument;
import de.mcmxcv.apitrading.exchange.instrument.InstrumentDetails;
import de.mcmxcv.apitrading.exchange.instrument.Price;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;

public class InstrumentFixture {
	private static final double MIN_SPREAD = 0.0001;
	private static final double MAX_SPREAD = 1;
	private static final double MAX_RANDOM_START_PRICE = 1_000_000;
	private static final int SLEEP_INTERVAL_MS = 100;
	private static final double DEFAULT_STEP_SIZE = 1.0;
	private final static Random random = new Random();

	/**
	 * Creates a dummy Instrument with given details and price observable.
	 * 
	 * @param details    the instrument details
	 * @param dummyPrice the observable providing price updates
	 * @return a dummy Instrument for testing purposes
	 */
	public static Instrument getDummyInstrument(InstrumentDetails details, Observable<Price> DummyPrice) {
		return new Instrument(details, DummyPrice);
	}

	/**
	 * Creates a dummy Instrument with given details, starting price, and a custom
	 * price development function.
	 * 
	 * @param details                  the instrument details
	 * @param startPrice               the starting price for the instrument
	 * @param priceDevelopmentFunction the function that defines how the price will
	 *                                 change over time
	 * @return a dummy Instrument for testing purposes with a custom price
	 *         development
	 */
	public static Instrument getDummyInstrument(InstrumentDetails details, Price startPrice,
			Function<Price, Price> priceDevelopmentFunction) {

		AtomicReference<@NonNull Price> nextPrice = new AtomicReference<>(startPrice);
		Observable<Price> price = Observable.create(emitter -> {
			CompletableFuture.runAsync(() -> {
				while (!emitter.isDisposed()) {
					emitter.onNext(nextPrice.get());
					nextPrice.set(priceDevelopmentFunction.apply(nextPrice.get()));
					try {
						TimeUnit.MILLISECONDS.sleep(SLEEP_INTERVAL_MS);
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
						emitter.onError(e);
					}
				}
			});

		});
		return getDummyInstrument(details, price);
	}

	/**
	 * Creates a dummy Instrument with given details, starting price, and a price
	 * observable that trends either upwards or downwards.
	 * 
	 * @param details         the instrument details
	 * @param startPrice      the starting price for the instrument
	 * @param stepSize        the step size by which the price will increase or
	 *                        decrease
	 * @param priceDecreasing if true, the price will trend downwards; if false, the
	 *                        price will trend upwards
	 * @return a dummy Instrument for testing purposes with a trending price
	 */
	public static Instrument getDummyInstrumentWithTrendingPriceDevelopment(InstrumentDetails details, Price startPrice,
			double stepSize, boolean priceDecreasing) {
		return getDummyInstrument(details, startPrice, price -> getNextDummyPrice(price, stepSize, priceDecreasing));
	}

	/**
	 * Creates a dummy Instrument with given details and a price observable that
	 * trends upwards.
	 * 
	 * @param details the instrument details
	 * @return a dummy Instrument with an increasing price for testing purposes
	 */
	public static Instrument getDummyInstrumentWithIncreasingPriceDevelopment(InstrumentDetails details) {
		Price seedPrice = randomStartPrice();
		return getDummyInstrument(details, seedPrice, InstrumentFixture::getNextIncreasedDummyPrice);
	}

	/**
	 * Creates a dummy Instrument with given details and a price observable that
	 * trends downwards.
	 * 
	 * @param details the instrument details
	 * @return a dummy Instrument with a decreasing price for testing purposes
	 */
	public static Instrument getDummyInstrumentWithDecreasingPriceDevelopment(InstrumentDetails details) {
		Price seedPrice = randomStartPrice();
		return getDummyInstrument(details, seedPrice, InstrumentFixture::getNextDecreasedDummyPrice);
	}

	/**
	 * Creates a dummy Instrument with given details and a randomly generated price
	 * observable.
	 * 
	 * @param details the instrument details
	 * @return a dummy Instrument with randomly generated price development for
	 *         testing purposes
	 */
	public static Instrument getDummyInstrumentWithRandomPriceDevelopment(InstrumentDetails details) {
		Price seedPrice = randomStartPrice();
		return getDummyInstrument(details, seedPrice, InstrumentFixture::getApproximatelyRealisticNextRandomDummyPrice);
	}

	private static Price randomStartPrice() {
		BigDecimal randomBuy = BigDecimal.valueOf(random.nextDouble(0, MAX_RANDOM_START_PRICE));
		BigDecimal randomSell = randomBuy
				.multiply(BigDecimal.ONE.subtract(BigDecimal.valueOf(random.nextDouble(MIN_SPREAD, MAX_SPREAD))));
		return new Price(randomBuy, randomSell, null);
	}

	private static Price getNextDummyPrice(Price currentPrice, double stepSize, boolean decreasing) {
		BigDecimal spreadPercent = calculateApproximatelyRealisticSpreadInPercent(currentPrice);
		BigDecimal nextSell = decreasing ? currentPrice.sell().subtract(BigDecimal.valueOf(stepSize))
				: currentPrice.sell().add(BigDecimal.valueOf(stepSize));
		BigDecimal nextBuy = nextSell.multiply(BigDecimal.ONE.add(spreadPercent));
		return new Price(nextBuy, nextSell, currentPrice.currency());
	}

	private static Price getNextIncreasedDummyPrice(Price currentPrice) {
		return getNextDummyPrice(currentPrice, DEFAULT_STEP_SIZE, false);
	}

	private static Price getNextDecreasedDummyPrice(Price currentPrice) {
		return getNextDummyPrice(currentPrice, DEFAULT_STEP_SIZE, true);
	}

	private static Price getApproximatelyRealisticNextRandomDummyPrice(Price currentPrice) {
		boolean priceDecreases = random.nextBoolean();
		double difference = random.nextDouble(0, 1);
		return getNextDummyPrice(currentPrice, difference, priceDecreases);
	}

	private static BigDecimal calculateApproximatelyRealisticSpreadInPercent(Price currentPrice) {
		BigDecimal spread = currentPrice.buy().subtract(currentPrice.sell()).abs();
		MathContext mc = MathContext.DECIMAL128;
		if (currentPrice.buy().abs().compareTo(currentPrice.sell()) > 0) {
			return spread.divide(currentPrice.buy().abs(), mc);
		} else {
			return spread.divide(currentPrice.sell().abs(), mc);
		}
	}
}
