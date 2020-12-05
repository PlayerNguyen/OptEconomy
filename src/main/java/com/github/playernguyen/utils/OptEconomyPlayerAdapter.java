package com.github.playernguyen.utils;

import org.bukkit.Bukkit;

import java.util.UUID;

/**
 * The adapter will convert UUID to player and vice versa
 */
public class OptEconomyPlayerAdapter {

    /**
     * Get the UUID from offline player username
     *
     * @param name the username to get
     * @return the return
     */
    public static UUID getUUIDPlayerName(String name) {
        return Bukkit.getOfflinePlayer(name).getUniqueId();
    }

    /**
     * Get the username from uuid
     *
     * @param uuid the uuid of that player
     * @return the username of that player
     */
    public static String getUsernameFromUUID(UUID uuid) {
        return Bukkit.getOfflinePlayer(uuid).getName();
    }

}
