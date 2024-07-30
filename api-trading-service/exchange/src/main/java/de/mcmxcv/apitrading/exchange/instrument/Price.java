package de.mcmxcv.apitrading.exchange.instrument;

import java.math.BigDecimal;
import java.util.Currency;

public record Price(BigDecimal buy, BigDecimal sell, Currency currency) {}
