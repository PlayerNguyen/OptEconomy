package com.github.playernguyen.manager;

import java.util.Map;

/**
 * Map manager class to contain as key-value data
 */
public interface OptEconomyManagerMap<K, V> {

    /**
     * The map which containing data
     * @return the map containing data
     */
    Map<K, V> map();

}
