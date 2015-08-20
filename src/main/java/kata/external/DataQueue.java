package kata.external;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;


public class DataQueue {

    private final BlockingDeque<Data> queue = new LinkedBlockingDeque<>();

    private final KeyValueStore kvStore;

    public DataQueue(KeyValueStore kvStore) {
        this.kvStore = kvStore;
    }

    public void start() {

        for (int i = 0; i < 5; i++)
            new Thread(new KVWriter()).start();

    }

    public void enqueue(Data data) {
        try {
            queue.putLast(data);
        } catch (InterruptedException ignore) {}
    }

    private class KVWriter implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    Data data = queue.takeFirst();
                    Thread.sleep(37);
                    kvStore.update(data);
                } catch (InterruptedException e) {
                    return;
                }
            }
        }
    }

}
