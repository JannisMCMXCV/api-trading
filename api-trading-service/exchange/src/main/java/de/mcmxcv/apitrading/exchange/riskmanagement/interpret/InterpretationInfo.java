package de.mcmxcv.apitrading.exchange.riskmanagement.interpret;

public enum InterpretationInfo {
	CHART_LEVEL,
	DISTANCE,
	PROFIT;
	
	public static InterpretationInfo useDefault() {
		return InterpretationInfo.CHART_LEVEL;	
	}
}
