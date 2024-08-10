package de.mcmxcv.apitrading.exchange.account;

import java.math.BigDecimal;
import java.util.Currency;

public record BalanceSnapshot(BigDecimal balance, BigDecimal available, Currency currency) {

}
