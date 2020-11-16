package com.github.playernguyen.objects;

/**
 * Pair is an object was define by two data type
 *
 * @param <K> the first data type
 * @param <V> the second data type
 */
public class OptEconomyPair<K, V> {

    private final K first;
    private final V second;

    /**
     * Constructor to the pair
     *
     * @param first  the first value of pair object
     * @param second the second value of pair object
     */
    public OptEconomyPair(K first, V second) {
        this.first = first;
        this.second = second;
    }

    /**
     * Get the first object
     *
     * @return the first object
     */
    public K getFirst() {
        return first;
    }

    /**
     * Get the second object
     *
     * @return the second object
     */
    public V getSecond() {
        return second;
    }

    /**
     * Initiate the new pair
     *
     * @param first  the first value of pair
     * @param second the second value of pair
     * @param <T> the first generic data type
     * @param <J> the second generic data type
     * @return the new pair object
     */
    public static <T, J> OptEconomyPair<T, J> of(T first, J second) {
        return new OptEconomyPair<>(first, second);
    }
}
