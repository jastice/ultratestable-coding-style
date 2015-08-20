package kata.external;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class Lag {

    private final Random random;
    private final Timer timer;

    public Lag(Random random, Timer timer) {
        this.random = random;
        this.timer = timer;
    }

    public <T> CompletableFuture<T> lagged(long delay, Supplier<T> supplier) {

        CompletableFuture<T> future = new CompletableFuture<T>();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                future.complete(supplier.get());
            }
        };
        timer.schedule(task, delay);
        return future;
    }

    public void lagged(long delay, Runnable runnable) {
        Supplier<Integer> supplier = () -> {runnable.run(); return 0;};
        lagged(delay, supplier);
    }
}
