package com.github.playernguyen.players;

import com.github.playernguyen.objects.OptEconomyDouble;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * The player class represent the data of Player.
 */
public class OptEconomyPlayer implements Comparable<OptEconomyPlayer> {

    /**
     * The immutable identify for object
     */
    private final UUID uniqueId;
    /**
     * A balance of player aka. point
     */
    private OptEconomyDouble balance;
    /**
     * The last update data of player of the cache
     */
    private long lastUpdated;

    /**
     * Initiate the player class
     *
     * @param uniqueId the uuid which represent to the player
     * @param balance  the balance of player
     */
    public OptEconomyPlayer(UUID uniqueId,
                            OptEconomyDouble balance,
                            long lastUpdate) {
        this.uniqueId = uniqueId;
        this.balance = balance;
        this.lastUpdated = lastUpdate;
    }

    /**
     * Set a new balance to player
     *
     * @param balance a new balance value
     */
    public void setBalance(OptEconomyDouble balance) {
        this.balance = balance;
    }

    /**
     * Get a last update time in milliseconds
     *
     * @return a last update time as milliseconds
     */
    public long getLastUpdated() {
        return lastUpdated;
    }

    /**
     * Set a new last updated time in milliseconds
     *
     * @param lastUpdated a new last updated value to be set
     */
    public void setLastUpdated(long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    /**
     * Get a balance of player
     *
     * @return A balance of player
     */
    public OptEconomyDouble getBalance() {
        return balance;
    }

    @Override
    public int compareTo(@NotNull OptEconomyPlayer o) {
        return this.uniqueId.toString().compareTo(o.uniqueId.toString());
    }

    /**
     * Get unique id of this player which immutable
     * @return the unique id of this player
     */
    public UUID getUniqueId() {
        return uniqueId;
    }

    /**
     * Adapter from old player
     * @param player the player to be converted
     * @return the new player
     */
    public OptEconomyPlayer from(OptEconomyPlayer player) {
        return new OptEconomyPlayer(player.getUniqueId(), player.getBalance(), player.getLastUpdated());
    }
}
