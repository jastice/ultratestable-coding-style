package kata;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.Timer;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

import kata.external.DataQueue;
import kata.external.KeyValueStore;
import kata.external.Lag;

public class SuperAsyncCensoringMachine {

    public static void main(String[] args) throws IOException, InterruptedException {

        File input = new File(args[0]);

        BufferedReader reader = new BufferedReader(new FileReader(input));
        Lag lag = new Lag(new Random(), new Timer());
        KeyValueStore kvStore = new KeyValueStore(lag);
        TokenProcessor tokenProcessor = new TokenProcessor(30, 2000, new SystemClock(), kvStore, new DataQueue(kvStore, lag));
        Censor censor = new Censor(tokenProcessor);
        censor.printIfAllowed(new TokenStream(reader));


        ForkJoinPool.commonPool().awaitQuiescence(23, TimeUnit.DAYS);
    }
}
