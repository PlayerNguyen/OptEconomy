package com.github.playernguyen.configurations;

/**
 * The interface configuration template class.
 * This class is immutable and must be enumerable as a section of config.
 *
 */
public interface OptEconomyTemplate {

    /**
     * This path is node for the configuration to follow. For example:
     *  Whether this path is `abc.xyz`. The config section of it's `abc.xyz`
     *
     * @return the path to config
     */
    String path();

    /**
     * The default declaration for the config object
     * @return the default declaration whether the config was not load
     */
    Object declare();

    /**
     * The comments of that section
     * @return the comments of that section
     */
    String[] comments();
}
