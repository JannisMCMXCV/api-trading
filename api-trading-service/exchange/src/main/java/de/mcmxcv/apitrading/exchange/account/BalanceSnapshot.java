package de.mcmxcv.apitrading.exchange.account;

import java.math.BigDecimal;
import java.util.Currency;

import de.mcmxcv.apitrading.currency.HybridCurrency;

public record BalanceSnapshot(BigDecimal balance, BigDecimal available, HybridCurrency currency) {}
