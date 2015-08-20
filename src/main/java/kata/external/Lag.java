package kata.external;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class Lag {

    private final Random random = new Random();

    private final Timer timer = new Timer();

    public <T> CompletableFuture<T> lagged(long delay, Supplier<T> supplier) {

        CompletableFuture<T> future = new CompletableFuture<T>();
        TimerTask task = new TimerTask() {
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
