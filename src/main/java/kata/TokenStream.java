package kata;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class TokenStream implements Iterable<Token> {

    private final BufferedReader in;

    private class TokenIterator implements Iterator<Token> {

        private String line;
        private List<String> words;
        private Iterator<String> current;

        public TokenIterator() {
            readNextLine();
        }

        @Override
        public boolean hasNext() {
            if (!current.hasNext()) {
                readNextLine();
            }

            return current.hasNext();
        }

        @Override
        public Token next() {
            if (!current.hasNext()) {
                readNextLine();
            }

            if (current.hasNext()) {
                return new WordToken(current.next());
            } else {
                return new NewlineToken();
            }
        }

        private void readNextLine() {
            try {
                line = in.readLine();
                words = Arrays.asList(line.split("[\\W\\s]+"));
                current = words.iterator();
            } catch (IOException ignored) {
            }
        }

    }

    public TokenStream(BufferedReader in) {
        this.in = in;
    }

    @Override
    public Iterator<Token> iterator() {
        return new TokenIterator();
    }

}
