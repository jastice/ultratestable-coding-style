package kata.external;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/** Simulate an external Key-Value Store with some lag. */
public class KeyValueStore {

    private final Map<String, Data> kv = new HashMap<>();

    private final long defaultTime = System.currentTimeMillis();

    private final Lag lag;

    public KeyValueStore(Lag lag) {
        this.lag = lag;
    }

    private Data getOrDefault(String id) {
        return kv.getOrDefault(id, new Data(id, defaultTime, 0));
    }


    public void update(Data data) {
        lag.lagged(5, () -> {
            synchronized (kv) {
                return kv.put(data.id, getOrDefault(data.id).updateWith(data));
            }
        });
    }

    public CompletableFuture<Data> get(String id) {
        return lag.lagged(5, () -> {
            synchronized(kv) { return getOrDefault(id); }
        });
    }

}
