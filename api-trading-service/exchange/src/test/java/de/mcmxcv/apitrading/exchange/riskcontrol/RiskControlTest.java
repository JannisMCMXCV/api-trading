package de.mcmxcv.apitrading.exchange.riskcontrol;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

public class RiskControlTest {
	
	@Test
	public void riskControl() {
		BigDecimal threshold = new BigDecimal("100.00");
		InterpretationInfo interpretationInfo = InterpretationInfo.DISTANCE;
		String reason = "Ich kontrolliere das Risoko, du Lusche";
		
		RiskControl riskControl = new RiskControl.Builder()
			    .threshold(threshold)
			    .as(interpretationInfo)
			    .reason(reason)
			    .build();
		
		assertEquals(threshold, riskControl.getThreashold());
		assertEquals(interpretationInfo, riskControl.getInterpretationInfo());
		assertEquals(reason, riskControl.getReason());
	}
	
	@Test
	@SuppressWarnings("unused")
	public void throwsWhenTriggerValueIsNotSet() {
		assertThrows(RuntimeException.class, () -> {
			RiskControl riskControl = new RiskControl.Builder()
					.build();	
		});
	}
}
