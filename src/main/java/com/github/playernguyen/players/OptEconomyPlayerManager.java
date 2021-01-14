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
        // Instance set up
        this.instance = instance;
    }

    /**
     * Get the player from manager list. Whether not found that player, create that player with balance value at init level.
     *
     * @param uuid the uuid of player
     * @return the player will be returned
     * @throws SQLException throw whether cannot connect or execute queries
     * @see com.github.playernguyen.players.storages.OptEconomyPlayerStorageManager#get(UUID)
     */
    public OptEconomyPlayer get(UUID uuid) throws SQLException {
        // Find it in collection
        OptEconomyPlayer player = this.collection().stream().filter(element -> element.getUniqueId().equals(uuid))
                .findAny().orElse(null);
        // Describe method:
        //  Add new storage and then check whether is out of date
        //      If out of date, update player
        //      Else return the player
        if (player == null) {
            player = instance.getPlayerStorageManager().getNotNull(uuid);
            this.collection().add(player);
        } else {
            if ((System.currentTimeMillis() - player.getLastUpdated()) >
                    Long.parseLong(instance.getSettingConfiguration().get(OptEconomySettingTemplate.CACHE_STORAGE_DURATION))) {
                this.updatePlayer(uuid);
            }
        }
        // Collection debugger
        instance.getDebugger().iterateOut(this.collection());
        return player;
    }

    /**
     * Get the player from manager list only.
     *
     * @param uuid
     * @return
     */
    public OptEconomyPlayer getInCollection(UUID uuid) {
        return this.collection().stream().filter(element -> element.getUniqueId().equals(uuid)).findAny().orElse(null);
    }

    /**
     * Force OptEconomy to update player
     *
     * @param uuid the player uuid
     * @throws SQLException throw whether cannot get player in data
     */
    public void updatePlayer(UUID uuid) throws SQLException {
        // Remove player
        this.collection().removeIf(ele -> ele.getUniqueId().equals(uuid));
        // Post new player
        this.collection().add(instance.getPlayerStorageManager().get(uuid));
    }

    /**
     * Save and remove the player in collection
     *
     * @param predicate the predicate condition to be removed
     * @return whether removed or not
     */
    public boolean removePlayer(Predicate<OptEconomyPlayer> predicate) throws SQLException {
        OptEconomyPlayer player = this.collection().stream().filter(predicate).findAny().orElse(null);
        // Check for not null
        if (player == null) {
            throw new NullPointerException();
        }
        // Save the data
        this.instance.getPlayerStorageManager().save(player);
        return this.collection().remove(player);
    }

    /**
     * Save and repove the player in collection using uuid (not predicate)
     *
     * @param uuid the uuid to be removed
     * @return whether removed or not
     * @see #removePlayer(Predicate)
     */
    public boolean removePlayer(UUID uuid) throws SQLException {
        return this.removePlayer(player -> player.getUniqueId().equals(uuid));
    }

}
