package kata;

public class NewlineToken implements Token {

    @Override
    public <T> T accept(TokenVisitor<T> visitor) {
        return visitor.visitNewlineToken(this);
    }

}
