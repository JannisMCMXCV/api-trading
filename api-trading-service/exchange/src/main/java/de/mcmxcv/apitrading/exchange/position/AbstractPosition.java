package de.mcmxcv.apitrading.exchange.position;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import de.mcmxcv.apitrading.exchange.Exchange;
import de.mcmxcv.apitrading.exchange.instrument.Instrument;
import de.mcmxcv.apitrading.exchange.instrument.Price;
import de.mcmxcv.apitrading.exchange.riskcontrol.RiskControl;
import io.reactivex.rxjava3.disposables.Disposable;

public abstract class AbstractPosition implements Position {	
	private final Instrument instrument;
	
	private RiskControl stopLoss;
	private RiskControl takeProfit;
	
	private boolean closed;
	
	protected List<Disposable> priceSubscriptions = new ArrayList<>();
	
	protected AbstractPosition(Instrument instrument) {
		this.instrument = instrument;
		stopLossWatchDog();
		takeProfitWatchDog();
	}

	protected AbstractPosition(Instrument instrument, RiskControl stopLoss, RiskControl takeProfit) {
		this.stopLoss = stopLoss;
		this.takeProfit = takeProfit;
		this.instrument = instrument;
	}
	
	protected abstract boolean shouldTriggerStopLoss(Price price, RiskControl stopLoss);
	protected abstract boolean shouldTriggerTakeProfit(Price price, RiskControl takeProfit);
	
    private void stopLossWatchDog() {
        CompletableFuture.runAsync(() -> {
        	priceSubscriptions.add(instrument.price().subscribe(price -> {	
        		if(closed) {
        			return;
        		}
        		if (stopLoss == null) {
        			return;
        		}
        		if (shouldTriggerStopLoss(price, this.stopLoss)) {
        			close("Stop Loss triggered");
        		}
        	}));
        });
    }

    private void takeProfitWatchDog() {
        CompletableFuture.runAsync(() -> {
        	priceSubscriptions.add(instrument.price().subscribe(price -> {	
        		if(closed) {
        			return;
        		}
        		if (shouldTriggerTakeProfit(price, this.takeProfit)) {
        			close("Take Profit triggered");
        		}
        	}));
        });
    }

	@Override
	public void close(String reason) {
		// TODO: handle reason with decorators
		if (isClosed())  {
			disposePriceSubscriptions();
			return;
		}
		try{
			disposePriceSubscriptions();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	protected void disposePriceSubscriptions() {
		priceSubscriptions.forEach(Disposable::dispose);
		priceSubscriptions.clear();
	}

	private Exchange getExchange() {
		return this.instrument.details().exchange();
	}

	@Override
	public Instrument getInstrument() {
		return this.instrument;
	}

	@Override
	public RiskControl getStopLoss() {
		return null;
	}
	
	public void setStopLoss(RiskControl stopLoss) {
		this.stopLoss = stopLoss;
	}

	@Override
	public RiskControl getTakeProfit() {
		return null;
	}
	
	
	public void setTakeProfit(RiskControl takeProfit) {
		this.takeProfit = takeProfit;
	}

	@Override
	public boolean isClosed() {
		return false;
	}

	@Override
	public void close() {
		close("");
		
	}
}
