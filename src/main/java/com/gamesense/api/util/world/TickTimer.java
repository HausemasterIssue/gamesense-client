package com.gamesense.api.util.world;

public class TickTimer {
    private static Timer timer = new Timer();

    public static boolean passed(int ticks) {
        return timer.passedMs(50L * ticks);
    }

    public TickTimer reset() {
        this.timer.reset();
        return this;
    }
}
