package com.gamesense.api.util.world;

public class TickTimer {
    private final Timer timer = new Timer();

    public boolean passed(float ticks) {
        return this.timer.passedMs(50L * (long) ticks);
    }

    public boolean passed(int ticks) {
        return this.timer.passedMs(50L * ticks);
    }

    public TickTimer reset() {
        this.timer.reset();
        return this;
    }
}
