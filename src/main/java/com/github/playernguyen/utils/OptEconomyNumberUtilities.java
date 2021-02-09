package com.github.playernguyen.utils;

public class OptEconomyNumberUtilities {

    /**
     * Check whether the value is integer or not
     * @param value the value to be checked
     * @return true/false whether the value is integer
     */
    public static boolean isInteger(double value) {
        return value % 1 == 0;
    }

}
