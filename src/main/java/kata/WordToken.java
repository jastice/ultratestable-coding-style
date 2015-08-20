package kata;

public class WordToken implements Token {

    private final String word;

    public WordToken(String word) {
        this.word = word;
    }

    public String getWord() {
        return word;
    }

    @Override
    public <T> T accept(TokenVisitor<T> visitor) {
        return visitor.visitWordToken(this);
    }
}
