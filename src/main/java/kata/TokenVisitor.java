package kata;

public interface TokenVisitor<T> {
    T visitWordToken(WordToken wordToken);

    T visitNewlineToken(NewlineToken newlineToken);
}
