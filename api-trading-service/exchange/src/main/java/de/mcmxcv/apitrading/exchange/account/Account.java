package de.mcmxcv.apitrading.exchange.account;

import java.math.BigDecimal;
import java.util.Currency;

public record Account(BigDecimal funds, BigDecimal available, Currency currency) {
}
