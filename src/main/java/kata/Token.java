package kata;

public interface Token {
    <T> T accept(TokenVisitor<T> visitor);
}
