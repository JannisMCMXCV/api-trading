package de.mcmxcv.apitrading.exchange.instrument;

import io.reactivex.rxjava3.core.Observable;

public record Instrument(String id, String name, Observable<Price> price){}
