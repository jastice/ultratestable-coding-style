package kata;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

public class SuperAsyncCensoringMachine {

    public static void main(String[] args) throws IOException, InterruptedException {

        File input = new File(args[0]);

        BufferedReader reader = new BufferedReader(new FileReader(input));
        Censor censor = new Censor(30, 2000);
        censor.printIfAllowed(reader);


        ForkJoinPool.commonPool().awaitQuiescence(23, TimeUnit.DAYS);
    }
}
