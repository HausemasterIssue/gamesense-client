package com.gamesense.api.util.misc;

public class Timer {

    private long current;
    private long time = -1L;

    public Timer() {
        this.current = System.currentTimeMillis();
    }

    public boolean hasReached(final long delay) {
        return System.currentTimeMillis() - this.current >= delay;
    }

    public boolean hasReached(final long delay, boolean reset) {
        if (reset)
            reset();
        return System.currentTimeMillis() - this.current >= delay;
    }

    public void reset() {
        this.current = System.currentTimeMillis();
    }

    public long getTimePassed() {
        return System.currentTimeMillis() - this.current;
    }

    public boolean sleep(final long time) {
        if (time() >= time) {
            reset();
            return true;
        }
        return false;
    }

    public long time() {
        return System.currentTimeMillis() - current;
    }
    
    public boolean passedS(double s) {
        return this.passedMs((long)s * 1000L);
    }

    public boolean passedMs(long ms) {
        return this.passedNS(this.convertToNS(ms));
    }

    public boolean passedNS(long ns) {
        return System.nanoTime() - this.time >= ns;
    }

    public long convertToNS(long time) {
        return time * 1000000L;
    }

    public long getPassedTimeMs() {
        return (System.nanoTime() - this.time) / 1000000L;
    }
}
