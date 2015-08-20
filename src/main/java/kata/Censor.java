package kata;

import kata.external.Data;
import kata.external.DataQueue;
import kata.external.KeyValueStore;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.concurrent.ExecutionException;


public class Censor {

    private static final long MILLIS_PER_MINUTE = 60000;

    private final double rateLimit;
    private final long pause;

    KeyValueStore kvStore = new KeyValueStore();
    DataQueue queue = new DataQueue(kvStore);

    /**
     *
     * @param rateLimit Occurrences of any word allowed per minute
     * @param wordsPerMinute words to read per Minute7
     */
    public Censor(double rateLimit, int wordsPerMinute) {
        this.rateLimit = rateLimit;
        this.pause = MILLIS_PER_MINUTE / wordsPerMinute;

        queue.start();
    }

    /** Prints each line if it does not exceed the rate limit for each character. */
    public void printIfAllowed(BufferedReader in) throws IOException, InterruptedException {

        for (String line = in.readLine(); line != null; line = in.readLine()) {

            String[] words = line.split("[\\W\\s]+");

            int i = 0;
            for(String word: words) {
                Thread.sleep(pause);
                long now = System.currentTimeMillis();

                if (!word.isEmpty()) try {
                    kvStore.get(word).thenAcceptAsync((data) -> {
                        long totalEvents = data.eventsSince + 1;
                        double rate = (double) totalEvents / (now - data.timestamp) * MILLIS_PER_MINUTE;

                        if (rate < rateLimit) {
                            System.out.print(word + " ");
                            queue.enqueue(new Data(word, now, 1));
                        } else {
                            // censor limited words, but don't count them
                            System.out.print("CENSORED ");
                        }
                    }).get();
                } catch (InterruptedException ignored) {
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                i++;
            }

            System.out.println();
        }
    }
}
