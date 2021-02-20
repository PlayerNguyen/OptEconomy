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
public abstract class OptEconomyPlayerManagerAbstract implements OptEconomyPlayerManager {
    private final OptEconomy instance;
    private final OptEconomyDatabase database;
    private final OptEconomyTable<OptEconomyPlayer> userTable;

    @SuppressWarnings("unchecked")
    public OptEconomyPlayerManagerAbstract(OptEconomy instance, OptEconomyDatabase database) {
        this.instance = instance;
        this.database = database;
        this.userTable = (OptEconomyTable<OptEconomyPlayer>) database.getTable(instance.getSettingConfiguration()
                .get(OptEconomySettingTemplate.CONNECTION_TABLES_OPTECONOMY)
        );
        Preconditions.checkNotNull(this.userTable,
                "Table user was not declared. Please register the table before use this in code."
        );
    }

    @Override
    public OptEconomyDatabase getDatabase() {
        return database;
    }

    @Override @Nullable
    public OptEconomyPlayer getPlayer(UUID uniqueId) throws SQLException {
        List<OptEconomyPlayer> players = userTable.selectWhere("uuid=?", uniqueId.toString());
        // Whether found multiple player.
        //  Return the first player and show the warning
        if (players.size() > 1) {
            instance.getDebugger().warning( "Found duplicate player with uuid " + uniqueId.toString() );
            return players.get(0);
        }
        if (players.size() == 1) {
            return players.get(0);
        }
        return null;
    }

    @Override
    public boolean hasPlayer(UUID uniqueId) throws SQLException {
        return userTable.selectWhere("uuid=?", uniqueId.toString()).size() > 0;
    }

    @Override
    public boolean updatePlayer(OptEconomyPlayer player) throws SQLException {
        Preconditions.checkNotNull(getPlayer(player.getUniqueId()));
        return userTable.update(player, "uuid=?", player.getUniqueId().toString());
    }

    public boolean createPlayer(OptEconomyPlayer player) throws SQLException {
        return userTable.insert(player);
    }
}
