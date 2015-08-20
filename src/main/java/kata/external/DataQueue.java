package kata.external;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;


public class DataQueue {

    private final BlockingDeque<Data> queue = new LinkedBlockingDeque<>();

    private final KeyValueStore kvStore;

    private final Lag lag;

    public DataQueue(KeyValueStore kvStore, Lag lag) {
        this.kvStore = kvStore;
        this.lag = lag;
    }

    public void start() {

        for (int i = 0; i < 5; i++) {
            new Thread(() -> {

                while (true) {
                    try {
                        Data data = queue.takeFirst();
                        lag.lagged(37, () -> kvStore.update(data) );
                    } catch (InterruptedException e) {
                        return;
                    }
                }

            }).start();
        }

    }

    public void enqueue(Data data) {
        try {
            queue.putLast(data);
        } catch (InterruptedException ignore) {}
    }


}
