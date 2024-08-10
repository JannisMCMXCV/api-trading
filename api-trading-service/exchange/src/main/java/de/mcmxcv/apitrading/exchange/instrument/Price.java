package de.mcmxcv.apitrading.exchange.instrument;

import java.math.BigDecimal;

import de.mcmxcv.apitrading.currency.HybridCurrency;

public record Price(BigDecimal buy, BigDecimal sell, HybridCurrency currency) {}
