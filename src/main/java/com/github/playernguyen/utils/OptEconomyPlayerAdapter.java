package com.github.playernguyen.utils;

import org.bukkit.Bukkit;

import java.util.UUID;

/**
 * The adapter will convert UUID to player and vice versa
 */
public class OptEconomyPlayerAdapter {

    /**
     * Get the UUID from offline player username
     * @param name the username to get
     * @return the return
     */
    public static UUID getUUIDPlayerName(String name) {
        return Bukkit.getOfflinePlayer(name).getUniqueId();
    }

}
