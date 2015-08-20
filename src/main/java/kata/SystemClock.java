package kata;

public class SystemClock implements Clock {

    @Override
    public void pause(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public long currentTime() {
        return System.currentTimeMillis();
    }

}
