package kata;

import java.util.Timer;
import java.util.TimerTask;

public class FakeTimer extends Timer {

    private final FakeClock fakeClock;

    public FakeTimer(FakeClock fakeClock) {
        this.fakeClock = fakeClock;
    }

    @Override
    public void schedule(TimerTask task, long delay) {
        task.run();
        fakeClock.pause(delay); // Push the clock later by the given delay
    }
}
