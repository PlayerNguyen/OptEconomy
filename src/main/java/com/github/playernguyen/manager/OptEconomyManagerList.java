package com.github.playernguyen.manager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * The inheritance of {@link OptEconomyManager} by
 * using {@link java.util.List} instead of {@link java.util.Collection}
 *
 * @param <T> the represent data to collect
 */
public abstract class OptEconomyManagerList<T> implements OptEconomyManager<T> {

    /**
     * The collection variable which casted by List
     */
    private final List<T> collection;

    /**
     * None-default type of list constructor
     * @param collection the list-collection to define
     */
    public OptEconomyManagerList(List<T> collection) {
        this.collection = collection;
    }

    /**
     * Simply the constructor by using ArrayList as initiate list
     */
    public OptEconomyManagerList() {
        this.collection = new ArrayList<>();
    }

    /**
     * Inherit from the collection
     * @return the collection which initiate at constructor
     */
    @Override
    public Collection<T> collection() {
        return this.collection;
    }

}
