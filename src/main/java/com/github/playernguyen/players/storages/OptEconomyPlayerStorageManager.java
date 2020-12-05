package com.github.playernguyen.players.storages;

import com.github.playernguyen.OptEconomy;
import com.github.playernguyen.databases.OptEconomyDatabases;
import com.github.playernguyen.objects.OptEconomyDouble;
import com.github.playernguyen.objects.OptEconomyPair;
import com.github.playernguyen.players.OptEconomyPlayer;
import com.github.playernguyen.utils.OptEconomyPlayerAdapter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The player manager to manage online or offline player
 */
public abstract class OptEconomyPlayerStorageManager {

    private final OptEconomy instance;
    private final OptEconomyDatabases databases;

    public OptEconomyPlayerStorageManager(OptEconomy instance, OptEconomyDatabases databases) {
        this.instance = instance;
        this.databases = databases;
    }

    public OptEconomyDatabases getDatabases() {
        return databases;
    }

    /**
     * Get user from uuid
     *
     * @param from the uuid to get
     * @return the user at first rows whether find multiple choice or the user in {@link #find(UUID)}
     * list whether found single. Null whether the find list length is 0
     * @throws SQLException unless connected to the SQL Server or trouble
     * @see #find(UUID)
     */
    @Nullable
    public OptEconomyPlayer get(UUID from) throws SQLException {
        List<OptEconomyPlayer> playerResponseList = this.find(from);
        // Whether found nothing
        if (playerResponseList.size() == 0) {
            return null;
        }
        this.instance.getDebugger().info(String.format("Retrieving database's data from uuid %s", from.toString()));
        // Or found, response the first data in array
        return playerResponseList.get(0);
    }

    /**
     * Get user from uuid or create whether not exist
     *
     * @param from the uuid to get
     * @return the user object in {@link #get(UUID)}. Whether the result is null, return new instance with zero value
     * @throws SQLException unless connected to the SQL Server or trouble
     */
    @NotNull
    public OptEconomyPlayer getNotNull(UUID from) throws SQLException {
        OptEconomyPlayer player = this.get(from);
        return (player == null)
                ? new OptEconomyPlayer(from, OptEconomyDouble.zero(), System.currentTimeMillis())
                : player;
    }

    /**
     * Find user from UUID
     *
     * @param from the uuid to find
     * @return the user list from uuid
     * @throws SQLException unless connected to the SQL Server or trouble
     */
    public List<OptEconomyPlayer> find(UUID from) throws SQLException {
        ResultSet resultSet = this.getDatabases().getUserTable().selectAll("WHERE uuid=?", from);
        List<OptEconomyPlayer> temp = new ArrayList<>();
        while (resultSet.next()) {
            UUID uuid = UUID.fromString(resultSet.getString("uuid"));
            double balance = resultSet.getDouble("balance");
            temp.add(new OptEconomyPlayer(uuid, new OptEconomyDouble(balance), System.currentTimeMillis()));
        }
        return temp;
    }

    /**
     * Check whether player existed in database or not
     *
     * @param uuid the uuid of that player
     * @return true whether player existed, false otherwise
     * @throws SQLException unless connected to SQL server
     */
    public boolean has(UUID uuid) throws SQLException {
        return get(uuid) != null;
    }

    /**
     * Create the player whether not exist in database.Otherwise, update player data
     *
     * @param player the player to save
     * @throws SQLException unless connected to SQL error
     */
    public void save(OptEconomyPlayer player) throws SQLException {
        UUID uniqueId = player.getUniqueId();
        // Whether not found, create the new one
        //   edit otherwise
        instance.getDebugger().info("Checking existence of account...");
        if (!has(uniqueId)) {
            // Inserting new one
            instance.getDebugger().info("Inserting new account data (" + player.toString() + ")...");
            this.getDatabases().getUserTable().insert(
                    OptEconomyPair.of("uuid", uniqueId.toString()),
                    OptEconomyPair.of("balance", player.getBalance().toDouble()),
                    OptEconomyPair.of("username", OptEconomyPlayerAdapter.getUsernameFromUUID(uniqueId))
            );
        } else {
            // Updating the value
            instance.getDebugger().info(String.format("Updating the account (%s = %s)",
                    player, player.getBalance().toDouble())
            );
            this.getDatabases().getUserTable().update(
                    // Where
                    OptEconomyPair.of("uuid", uniqueId),
                    // Update values
                    OptEconomyPair.of("balance", player.getBalance().toDouble())
            );
        }
    }
}
