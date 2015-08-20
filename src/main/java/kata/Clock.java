package kata;

public interface Clock {
    void pause(long millis);

    long currentTime();
}
