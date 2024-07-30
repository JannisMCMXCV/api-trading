package de.mcmxcv.apitrading.exchange.account;

import java.math.BigDecimal;
import java.util.Currency;

import io.reactivex.rxjava3.core.Observable;

public record Account(Observable<BigDecimal> funds, Observable<BigDecimal> available, Currency currency) {}
