package com.github.playernguyen.debuggers;

/**
 * The profiler tool to measure time
 */
public class OptEconomyDebuggerMeasure {
    private final long start;

    public OptEconomyDebuggerMeasure() {
        this.start = System.currentTimeMillis();
    }

    /**
     * Ending the tool and return the time in millisecond.
     *
     * @return the measure time in millisecond
     */
    public long end() {
        return System.currentTimeMillis() - start;
    }
}
