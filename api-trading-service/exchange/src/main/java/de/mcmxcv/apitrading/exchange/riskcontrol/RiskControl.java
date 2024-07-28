package de.mcmxcv.apitrading.exchange.riskcontrol;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RiskControl {
	private static Logger logger = LoggerFactory.getLogger(RiskControl.class);
	private BigDecimal threshold;
	private String reason;
	private InterpretationInfo interpretationInfo;
	
	
	private RiskControl () {}
	
	public BigDecimal getThreashold() {
		return threshold;
	}
	
	public InterpretationInfo getInterpretationInfo() {
		return interpretationInfo;
	}
	public String getReason() {
		return this.reason;
	}
	
	
	public static class Builder implements de.mcmxcv.builder.Builder<RiskControl> {
		RiskControl riskControl;
		
		public Builder() {
			this.riskControl = new RiskControl();
		}
		
		@Override
		public RiskControl build() {
			if(this.riskControl.threshold == null) {
				throw new RuntimeException("Please provide least a trigger value. (Would be interpreted by default as'" + (riskControl.getInterpretationInfo() == null ? InterpretationInfo.useDefault() : riskControl.getInterpretationInfo()) + "'.)");
			}
			if(this.riskControl.interpretationInfo == null) {
				logger.warn("No interpretation info was set using default Interpretation: '{}'", InterpretationInfo.useDefault());
				this.riskControl.interpretationInfo = InterpretationInfo.useDefault();
			}
			if(this.riskControl.reason == null) {
				logger.warn("No reason provided");
				this.riskControl.reason = "";
			}
			return this.riskControl;
		}

		public Builder threshold(BigDecimal trigger) {
			this.riskControl.threshold = trigger;
			return this;
		}
		
		public Builder reason(String reason) {
			this.riskControl.reason = reason;
			return this;
		}
		
		public Builder as(InterpretationInfo interpretationInformation) {
			this.riskControl.interpretationInfo = interpretationInformation;
			return this;
		}		
	}
}
