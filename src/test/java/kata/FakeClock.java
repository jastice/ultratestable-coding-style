package kata;

public class FakeClock implements Clock {

    private long currentTime = 0;

    @Override
    public void pause(long millis) {
        currentTime += millis;
    }

    @Override
    public long currentTime() {
        return currentTime;
    }
}
