package de.mcmxcv.apitrading.exchange.riskcontrol;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import de.mcmxcv.apitrading.exchange.riskmanagement.interpret.InterpretationInfo;

public class TestRiskControl {
	
	@Test
	public void riskControl() {
		BigDecimal triggerValue = new BigDecimal("100.00");
		InterpretationInfo interpretationInfo = InterpretationInfo.DISTANCE;
		
		RiskControl takeProfit = new RiskControl.Builder()
			    .triggerValue(triggerValue)
			    .as(interpretationInfo)
			    .build();
		
		assertEquals(triggerValue, takeProfit.getTriggerValue());
		assertEquals(interpretationInfo, takeProfit.getInterpretationInfo());
	}
}
