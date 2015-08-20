package kata;

import java.util.concurrent.ExecutionException;

import kata.external.Data;
import kata.external.DataQueue;
import kata.external.KeyValueStore;

public final class TokenProcessor implements TokenVisitor<String> {
    private final double rateLimit;
    private final long pause;
    private final Clock clock;

    private final KeyValueStore kvStore;
    private final DataQueue queue;

    public TokenProcessor(double rateLimit, long pause, Clock clock, KeyValueStore kvStore, DataQueue queue) {
        this.rateLimit = rateLimit;
        this.pause = pause;
        this.clock = clock;
        this.kvStore = kvStore;
        this.queue = queue;

        queue.start();
    }

    @Override
    public String visitWordToken(WordToken wordToken) {
        clock.pause(pause);
        long now = clock.currentTime();

        if (!wordToken.getWord().isEmpty()) {
            return processNonemptyWord(wordToken.getWord(), now);
        } else {
            return "";
        }
    }

    private String processNonemptyWord(String word, long now) {
        try {
            return kvStore.get(word).thenApplyAsync(data -> processWordWithData(word, now, data).doWork()).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
            return "";
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return "";
        }
    }

    private TokenProcessor.ProcessedWord processWordWithData(String word, long now, Data data) {
        long totalEvents = data.eventsSince + 1;
        double rate = ((double) totalEvents / (now - data.timestamp)) * Censor.MILLIS_PER_MINUTE;

        if (rate < rateLimit) {
            return new UncensoredWord(new Data(word, now, 1), word);
        } else {
            return new CensoredWord();
        }
    }

    @Override
    public String visitNewlineToken(NewlineToken newlineToken) {
        return "\n";
    }

    public interface ProcessedWord {
        String doWork();
    }

    public class UncensoredWord implements TokenProcessor.ProcessedWord {

        private final Data data;
        private final String word;

        public UncensoredWord(Data data, String word) {
            this.data = data;
            this.word = word;
        }

        @Override
        public String doWork() {
            queue.enqueue(data);
            return word + " ";
        }
    }

    public class CensoredWord implements TokenProcessor.ProcessedWord {

        @Override
        public String doWork() {
            return "CENSORED ";
        }

    }
}