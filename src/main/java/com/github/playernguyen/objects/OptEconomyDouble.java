package com.github.playernguyen.objects;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;

/**
 * The double value holder with more essential
 */
public class OptEconomyDouble {

    /**
     * Value as double will be hold.
     * This variable will be mutated
     */
    private double value;

    /**
     * Initial of the class
     *
     * @param value the start value
     */
    public OptEconomyDouble(double value) {
        this.value = value;
    }

    /**
     * The value will be set to zero
     */
    public OptEconomyDouble() {
        this.value = 0;
    }

    /**
     * Set the holder value to the specific value
     *
     * @param value the value to be set
     */
    public void value(double value) {
        this.value = value;
    }

    /**
     * Get the formatted String value of the holder
     *
     * @param formatter the format pattern to format
     * @return the formatted string value
     * @see DecimalFormat
     * @see DecimalFormat#format(Object)
     */
    public String formatted(@NotNull String formatter) {
        return new DecimalFormat(formatter).format(value);
    }

    /**
     * Get the primitive double value
     * @return the primitive double value
     * @see Double
     */
    public double toDouble() {
        return this.value;
    }

    /**
     * Init new object with zero value
     *
     * @return the {@link OptEconomyDouble} class with zero value
     */
    public static OptEconomyDouble zero() {
        return new OptEconomyDouble(0);
    }
}
