package de.mcmxcv.apitrading.exchange.instrument;

import java.math.BigDecimal;

public record InstrumentDetails(
		String id,
		String name,
		boolean tradable,
		BigDecimal margin,
		BigDecimal minQuantity,
		BigDecimal maxQuantity
		) {
}
