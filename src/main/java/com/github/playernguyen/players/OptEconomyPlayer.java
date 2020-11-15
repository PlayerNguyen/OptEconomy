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
    private OptEconomyDouble balance;

    /**
     * Initiate the player class
     * @param uniqueId the uuid which represent to the player
     * @param balance the balance of player
     */
    public OptEconomyPlayer(UUID uniqueId,
                            OptEconomyDouble balance) {
        this.uniqueId = uniqueId;
        this.balance = balance;
    }

    @Override
    public int compareTo(@NotNull OptEconomyPlayer o) {
        return this.uniqueId.toString().compareTo(o.uniqueId.toString());
    }
}
