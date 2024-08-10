package de.mcmxcv.apitrading.impl.binance.account;

import java.math.BigDecimal;
import java.util.Currency;
import com.google.gson.Gson;

import de.mcmxcv.apitrading.exchange.account.AccountSnapshot;
import de.mcmxcv.apitrading.exchange.account.BalanceSnapshot;
import de.mcmxcv.apitrading.impl.binance.dto.account.BalanceDetail;
import de.mcmxcv.apitrading.impl.binance.dto.account.BalanceResponse;

public class AccountParser {
	private static BalanceSnapshot[] parseBalances(String jsonResponse) {
        Gson gson = new Gson();
        BalanceResponse balanceResponse = gson.fromJson(jsonResponse, BalanceResponse.class);

        BalanceDetail[] balanceDetails = balanceResponse.balances();
        BalanceSnapshot[] balanceSnapshots = new BalanceSnapshot[balanceDetails.length];

        for (int i = 0; i < balanceDetails.length; i++) {
            BigDecimal free = new BigDecimal(balanceDetails[i].free());
            BigDecimal locked = new BigDecimal(balanceDetails[i].locked());
            BigDecimal available = free.add(locked);
            Currency currency = Currency.getInstance(balanceDetails[i].asset());
            balanceSnapshots[i] = new BalanceSnapshot(available, free, currency);
        }

        return balanceSnapshots;
    }
	
	public static AccountSnapshot parseAccount(String json) {
		return new AccountSnapshot(parseBalances(json));
	}
}
