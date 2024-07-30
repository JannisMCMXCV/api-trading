package de.mcmxcv.apitrading.exchange.instrument;

import io.reactivex.rxjava3.core.Observable;

public record Instrument(InstrumentDetails details, Observable<Price> price){
}
