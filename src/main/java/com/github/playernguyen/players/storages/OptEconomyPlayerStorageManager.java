package com.github.playernguyen.players.storages;

import com.github.playernguyen.OptEconomy;
import com.github.playernguyen.databases.OptEconomyDatabase;
import com.github.playernguyen.databases.OptEconomyTable;
import com.github.playernguyen.debuggers.OptEconomyDebuggerMeasure;
import com.github.playernguyen.objects.OptEconomyDouble;
import com.github.playernguyen.players.OptEconomyPlayer;
import com.github.playernguyen.settings.OptEconomySettingTemplate;
import com.google.common.base.Preconditions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

/**
 * The player manager to manage online or offline player
 */
public abstract class OptEconomyPlayerStorageManager {
    private final OptEconomy instance;
    private final OptEconomyDatabase databases;
    private final OptEconomyTable<OptEconomyPlayer> userTable;

    @SuppressWarnings("unchecked")
    public OptEconomyPlayerStorageManager(OptEconomy instance, OptEconomyDatabase databases) {
        this.instance = instance;
        this.databases = databases;
        this.userTable = (OptEconomyTable<OptEconomyPlayer>) databases.getTable(instance.getSettingConfiguration()
                .get(OptEconomySettingTemplate.CONNECTION_TABLES_OPTECONOMY)
        );
        Preconditions.checkNotNull(this.userTable,
                "Table user was not declared. Please register the table before use in code"
        );
    }

    public OptEconomyDatabase getDatabases() {
        return databases;
    }

    /**
     * Get user from uuid
     *
     * @param from the uuid to get
     * @return the user at first rows whether find multiple choice or the user in {@link #findAccount(UUID)}
     * list whether found single. Null whether the find list length is 0
     * @throws SQLException unless connected to the SQL Server or trouble
     * @see #findAccount(UUID)
     */
    @Nullable
    public OptEconomyPlayer get(UUID from) throws SQLException {
        List<OptEconomyPlayer> playerResponseList = this.findAccount(from);
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
                : player
        ;
    }

    /**
     * Find user from UUID
     *
     * @param from the uuid to find
     * @return the user list from uuid
     * @throws SQLException unless connected to the SQL Server or trouble
     */
    public List<OptEconomyPlayer> findAccount(UUID from) throws SQLException {
        // Enable profiler
        OptEconomyDebuggerMeasure measure = new OptEconomyDebuggerMeasure();
        List<OptEconomyPlayer> responsePlayers = this.userTable.selectWhere("uuid=?", from.toString());
        instance.getDebugger().info("find() measure end at: " + measure.end() + "ms");
        // End profiler
        instance.getDebugger().info(String.valueOf(responsePlayers));
        return responsePlayers;
    }

    /**
     * Check whether player existed in database or not.
     *
     * @param uuid the uuid of that player
     * @return true whether player existed, false otherwise
     * @throws SQLException unless connected to SQL server
     */
    public boolean hasAccount(UUID uuid) throws SQLException {
        return get(uuid) != null;
    }

    /**
     * Create the player whether not exist in database.Otherwise, update player data
     *
     * @param player the player to save
     * @throws SQLException unless connected to SQL error
     */
    public void saveAccount(OptEconomyPlayer player) throws SQLException {
        UUID uniqueId = player.getUniqueId();
        // Whether not found, create the new one:
        //   edit otherwise
        instance.getDebugger().info("Saving account to database...");
        instance.getDebugger().info("Check existence of account...");
        if (!hasAccount(uniqueId)) {
            instance.getDebugger().info("The account not exist in database. Inserting the new one...");
            // Inserting new one
            this.userTable.insert(player);
        } else {
            // Updating the value
            instance.getDebugger().info(String.format("Update the account (%s = %s)",
                    player, player.getBalance().toDouble())
            );
            this.userTable.update(player, "uuid=?", player.getUniqueId().toString());
        }
    }
}
