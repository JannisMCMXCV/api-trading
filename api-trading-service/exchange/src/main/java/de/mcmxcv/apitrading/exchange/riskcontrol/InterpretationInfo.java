package de.mcmxcv.apitrading.exchange.riskcontrol;

public enum InterpretationInfo {
	CHART_LEVEL,
	DISTANCE,
	PROFIT;
	
	public static InterpretationInfo useDefault() {
		return InterpretationInfo.CHART_LEVEL;	
	}
}
