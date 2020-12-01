package com.github.playernguyen.players;

import com.github.playernguyen.OptEconomy;
import com.github.playernguyen.manager.OptEconomyManagerList;
import com.github.playernguyen.settings.OptEconomySettingTemplate;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.UUID;
import java.util.function.Predicate;

/**
 * Player manager list which storage cache data. Inheritance {@link OptEconomyManagerList}.
 */
public class OptEconomyPlayerManager extends OptEconomyManagerList<OptEconomyPlayer> {

    private final OptEconomy instance;

    public OptEconomyPlayerManager(OptEconomy instance) {
        super(new LinkedList<>());
        this.instance = instance;
    }

    /**
     * Get the player from manager list
     *
     * @param uuid the uuid of player
     * @return the player will be returned
     * @throws SQLException throw whether cannot get new exception
     */
    public OptEconomyPlayer get(UUID uuid) throws SQLException {
        // Find it in collection
        OptEconomyPlayer player = this.collection().stream().filter(element -> element.getUniqueId().equals(uuid))
                .findAny().orElse(null);
        // Add new storage
        //  And then check is out of date
        //  If out of date, update player
        //  Or else return the player
        if (player == null) {
            this.collection().add(instance.getPlayerStorageManager().get(uuid));
        } else {
            if ((System.currentTimeMillis() - player.getLastUpdated()) >
                    (long) instance
                            .getSettingConfiguration().get(OptEconomySettingTemplate.OPTIONS_CACHE_STORAGE_DURATION
                            )) {
                this.updatePlayer(uuid);
            }
        }
        return player;
    }

    /**
     * Update the player
     *
     * @param uuid the player uuid
     * @throws SQLException throw whether cannot get player in data
     */
    private void updatePlayer(UUID uuid) throws SQLException {
        // Remove player
        this.collection().removeIf(ele -> ele.getUniqueId().equals(uuid));
        // Post new player
        this.collection().add(instance.getPlayerStorageManager().get(uuid));
    }

    /**
     * Remove the player in collection
     *
     * @param predicate the predicate condition to be removed
     * @return whether removed or not
     */
    public boolean removePlayer(Predicate<OptEconomyPlayer> predicate) {
        return this.collection().removeIf(predicate);
    }

    /**
     * Remove the player in collection using uuid (not predicate)
     *
     * @param uuid the uuid to be removed
     * @return whether removed or not
     * @see #removePlayer(Predicate)
     */
    public boolean removePlayer(UUID uuid) {
        return this.removePlayer(player -> player.getUniqueId().equals(uuid));
    }

}
