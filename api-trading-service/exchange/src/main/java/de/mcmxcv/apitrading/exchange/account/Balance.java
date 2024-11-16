package de.mcmxcv.apitrading.exchange.account;

import java.math.BigDecimal;

import de.mcmxcv.apitrading.currency.HybridCurrency;
import io.reactivex.rxjava3.core.Observable;

public record Balance(Observable<BigDecimal> balance, Observable<BigDecimal> available, HybridCurrency currency) {}
