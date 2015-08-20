package kata;

import java.io.IOException;


public class Censor {

    static final long MILLIS_PER_MINUTE = 60;

    private final TokenProcessor tokenProcessor;

    public Censor(TokenProcessor tokenProcessor) {
        this.tokenProcessor = tokenProcessor;
    }

    /** Prints each line if it does not exceed the rate limit for each character. */
    public void printIfAllowed(TokenStream stream) throws IOException, InterruptedException {
        for (Token token : stream) {
            System.out.print(token.accept(tokenProcessor));
        }
    }
}
