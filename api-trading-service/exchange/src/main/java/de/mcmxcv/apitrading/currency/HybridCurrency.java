package de.mcmxcv.apitrading.currency;

import java.util.Currency;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author mcmxcv
 * 
 * HybridCurrency represents both fiat and cryptocurrency.
 * It caches instances of currencies to avoid redundant object creation.
 */
public class HybridCurrency {
	private static final Map<String, HybridCurrency> cached = new ConcurrentHashMap<>();
	private final String currencyCode;
	private final Currency fiatCurrency;

	/**
	 * Constructor for HybridCurrency. Checks if the currency is fiat and
	 * initialises accordingly.
	 *
	 * @param currencyCode the currency code
	 */
	private HybridCurrency(String currencyCode) {
		this.currencyCode = currencyCode;
		this.fiatCurrency = isFiat(currencyCode) ? Currency.getInstance(currencyCode) : null;
	}

	/**
	 * Constructor for fiat currencies using the Currency instance.
	 *
	 * @param fiatCurrency the fiat currency instance
	 */
	private HybridCurrency(Currency fiatCurrency) {
		this(fiatCurrency.getCurrencyCode());
	}

	/**
	 * Returns an instance of HybridCurrency for the given code.
	 * Caches the instance if not already cached.
	 *
	 * @param code the currency code
	 * @return the HybridCurrency instance
	 */
	public static HybridCurrency of(String code) {
		return cached.computeIfAbsent(code, HybridCurrency::new);
	}

	/**
	 * Returns an instance of HybridCurrency for the given fiatCurrency.
	 * Caches the instance if not already cached.
	 *
	 * @param fiatCurrency the fiatCurrency
	 * @return the HybridCurrency instance
	 */
	public static HybridCurrency of(Currency fiatCurrency) {
		return cached.computeIfAbsent(fiatCurrency.getCurrencyCode(), HybridCurrency::new);
	}

	/**
	 * Checks if the given currency code is a fiat currency.
	 *
	 * @param currencyCode the currency code
	 * @return true if fiat, false otherwise
	 */
	public static boolean isFiat(String currencyCode) {
		try {
			Currency.getInstance(currencyCode);
			return true;
		} catch (IllegalArgumentException e) {
			return false;
		}
	}

	/**
	 * Checks if the currency is fiat.
	 *
	 * @return true if fiat, false otherwise
	 */
	public boolean isFiat() {
		return fiatCurrency != null;
	}

	/**
	 * Returns the currency code.
	 *
	 * @return the currency code
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		HybridCurrency that = (HybridCurrency) o;
		return currencyCode.equals(that.currencyCode);
	}

	@Override
	public int hashCode() {
		return currencyCode.hashCode();
	}

	@Override
	public String toString() {
		return String.format("HybridCurrency{currencyCode='%s', fiatCurrency=%s}", currencyCode,
				isFiat());
	}

	static void clearCache() {
		cached.clear();
	}

}
