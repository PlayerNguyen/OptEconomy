package com.github.playernguyen.manager;

import java.util.HashMap;
import java.util.Map;

/**
 * The abstract map
 */
public class OptEconomyManagerAbstractMap<K, V> implements OptEconomyManagerMap<K, V> {

    private final Map<K, V> map;

    public OptEconomyManagerAbstractMap(Map<K, V> map) {
        this.map = map;
    }

    public OptEconomyManagerAbstractMap() {
        this.map = new HashMap<>();
    }

    /**
     * The map manager object
     * @return the map object
     */
    @Override
    public Map<K, V> map() {
        return this.map;
    }
}
