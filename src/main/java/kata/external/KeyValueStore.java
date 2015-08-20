package kata.external;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

/** Simulate an external Key-Value Store with some lag. */
public class KeyValueStore {

    private final Map<String, Data> kv = new HashMap<>();

    private final Random random = new Random();

    private final long defaultTime = System.currentTimeMillis();

    private Data getOrDefault(String id) {
        return kv.getOrDefault(id, new Data(id, defaultTime, 0));
    }

    private void lag() {
        try {
            Thread.sleep(5 + random.nextInt(10));
        } catch (InterruptedException ignore) {}
    }

    public void update(Data data) {

        CompletableFuture.runAsync( () -> {
            lag();
            synchronized (kv) {
                kv.put(data.id, getOrDefault(data.id).updateWith(data));
            }
        } );

    }

    public CompletableFuture<Data> get(String id) {
        return CompletableFuture.supplyAsync(() -> {
            Data data;
            synchronized (kv) {
                data = getOrDefault(id);
            }
            lag();
            return data;
        });
    }

}
