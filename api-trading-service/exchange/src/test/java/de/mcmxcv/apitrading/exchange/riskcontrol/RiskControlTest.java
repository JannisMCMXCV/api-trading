package de.mcmxcv.apitrading.exchange.riskcontrol;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

public class RiskControlTest {
	
	@Test
	public void riskControl() {
		BigDecimal triggerValue = new BigDecimal("100.00");
		InterpretationInfo interpretationInfo = InterpretationInfo.DISTANCE;
		String reason = "Ich kontrolliere das Risoko, du Lusche";
		
		RiskControl riskControl = new RiskControl.Builder()
			    .triggerValue(triggerValue)
			    .as(interpretationInfo)
			    .reason(reason)
			    .build();
		
		assertEquals(triggerValue, riskControl.getTriggerValue());
		assertEquals(interpretationInfo, riskControl.getInterpretationInfo());
		assertEquals(reason, riskControl.getReason());
	}
	
	@Test
	public void throwsWhenTriggerValueIsNotSet() {
		assertThrows(RuntimeException.class, () -> {
			RiskControl riskControl = new RiskControl.Builder()
					.build();	
		});
	}
}
