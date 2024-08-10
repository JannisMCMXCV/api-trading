package de.mcmxcv.apitrading.impl.binance.dto.account;

public record BalanceResponse(
		int makerCommission,
	    int takerCommission,
	    int buyerCommission,
	    int sellerCommission,
	    CommissionRates commissionRates,
	    boolean canTrade,
	    boolean canWithdraw,
	    boolean canDeposit,
	    boolean brokered,
	    boolean requireSelfTradePrevention,
	    boolean preventSor,
	    long updateTime,
	    String accountType,
	    BalanceDetail[] balances
	    ) {}
