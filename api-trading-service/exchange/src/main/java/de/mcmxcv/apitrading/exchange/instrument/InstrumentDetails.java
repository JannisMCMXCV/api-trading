package de.mcmxcv.apitrading.exchange.instrument;

import java.math.BigDecimal;

public record InstrumentDetails(String id, String name, BigDecimal margin) {
}
