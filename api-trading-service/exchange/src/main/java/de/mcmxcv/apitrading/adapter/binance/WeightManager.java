package de.mcmxcv.apitrading.adapter.binance;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class WeightManager {
    private final AtomicInteger weight;
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    private ScheduledFuture<?> resetTask;

    public WeightManager(int initialWeight, int resetIntervalSeconds) {
        this.weight = new AtomicInteger(initialWeight);
        weightResetter(initialWeight, resetIntervalSeconds);
    }

    private void weightResetter(int resetWeight, int resetIntervalSeconds) {
        resetTask = scheduler.scheduleAtFixedRate(() -> weight.set(resetWeight), 
                                                  resetIntervalSeconds, 
                                                  resetIntervalSeconds, 
                                                  TimeUnit.SECONDS);
    }

    public int addAndGet(int delta) {
        return weight.addAndGet(delta);
    }

    public int get() {
        return weight.get();
    }

    public void shutdown() {
        if (resetTask != null) {
            resetTask.cancel(true);
        }
        scheduler.shutdown();
        try {
            if (!scheduler.awaitTermination(5, TimeUnit.SECONDS)) {
                scheduler.shutdownNow();
            }
        } catch (InterruptedException e) {
            scheduler.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
