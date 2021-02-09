package com.github.playernguyen.apis;

import com.github.playernguyen.OptEconomy;
import com.github.playernguyen.players.OptEconomyPlayer;

import java.sql.SQLException;
import java.util.UUID;

/**
 * The API class of OptEconomy. This class provide all accessible permission to interact with OptEconomy plugin.
 * Directly access to OptEconomy system may cause some issues and this class was created to fix that problem.
 */
public class OptEconomyAPI {
    private final OptEconomy plugin;

    /**
     * The constructor for API
     *
     * @param plugin the OptEconomy main class
     */
    public OptEconomyAPI(OptEconomy plugin) {
        this.plugin = plugin;
    }

    /**
     * @return OptEconomy plugin instance
     */
    public OptEconomy getPlugin() {
        return plugin;
    }

    /**
     * Get the {@link OptEconomyPlayer}. Whether not found that player. Return new player object with init balance.
     * otherwise return player.
     *
     * @param uuid the uniquely identify of that player (UUID)
     * @return the player which storage.
     * @throws SQLException error SQL problems such as connections, requests,...
     */
    public OptEconomyPlayer getPlayer(UUID uuid) throws SQLException {
        return plugin.getPlayerManager().get(uuid);
    }

    public void update(OptEconomyPlayer player) throws SQLException {
        this.getPlugin().getPlayerStorageManager().saveAccount(player);
    }

    public void balance(UUID uuid, double amount) throws SQLException {
        OptEconomyPlayer player = this.getPlayer(uuid);
        // Set the balance to player
        player.setBalance(amount);
        // Update the player to database
        this.update(player);
    }

    public void transact(UUID uuid, double amount) throws SQLException {
        // Get player profile to get player account
        OptEconomyPlayer player = this.getPlayer(uuid);
        // Transact the balance to player
        player.setBalance(player.getBalance().toDouble() + amount);
        // Update the player
        this.update(player);
    }

    public void withdraw(UUID uuid, double amount) throws SQLException {
        // Get player profile to withdraw
        OptEconomyPlayer player = this.getPlayer(uuid);
        // Withdraw account
        player.setBalance(player.getBalance().toDouble() - amount);
        // Update player
        this.update(player);
    }

}
