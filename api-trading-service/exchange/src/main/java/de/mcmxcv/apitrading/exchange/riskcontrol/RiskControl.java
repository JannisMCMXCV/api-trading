package de.mcmxcv.apitrading.exchange.riskcontrol;

import java.math.BigDecimal;

public class RiskControl {
	private BigDecimal triggerValue;
	private InterpretationInfo interpretationInfo;
	
	private RiskControl () {}
	
	public BigDecimal getTriggerValue() {
		return triggerValue;
	}
	
	public InterpretationInfo getInterpretationInfo() {
		return interpretationInfo;
	}
	
	
	public static class Builder implements de.mcmxcv.builder.Builder<RiskControl> {
		RiskControl takeProfit;
		
		public Builder() {
			this.takeProfit = new RiskControl();
		}
		
		@Override
		public RiskControl build() {
			if(this.takeProfit.triggerValue == null) {
				throw new RuntimeException("Please provide least a trigger value. (Would be interpreted by default as'" + InterpretationInfo.useDefault() + "'.)");
			}
			if(this.takeProfit.interpretationInfo == null) {
				System.out.println("No interpretation info was set using default Interpretation: '" + InterpretationInfo.useDefault() + "'.");
				this.takeProfit.interpretationInfo = InterpretationInfo.useDefault();
			}
			return this.takeProfit;
		}
		
		public Builder triggerValue(BigDecimal trigger) {
			this.takeProfit.triggerValue = trigger;
			return this;
		}
		
		public Builder as(InterpretationInfo interpretationInformation) {
			this.takeProfit.interpretationInfo = interpretationInformation;
			return this;
		}		
	}
}
