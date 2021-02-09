package com.github.playernguyen.vacuums;

import com.github.playernguyen.OptEconomy;
import com.github.playernguyen.players.OptEconomyPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is using to remove all redundant cache storage
 */
public class OptEconomyVacuum {
    private final OptEconomy instance;

    public OptEconomyVacuum(OptEconomy instance) {
        this.instance = instance;
    }

    /**
     * Clean up the player data which store Player information
     *
     * @throws SQLException cannot get information the player from SQL
     */
    public void cleanUp() throws SQLException {
        List<OptEconomyPlayer> trashList = new ArrayList<>();
        // Collecting the trash
        for (OptEconomyPlayer player : this.instance.getPlayerManager().collection()) {
            // Check player whether online and update
            // Remove otherwise
            Player bukkitPlayer = Bukkit.getPlayer(player.getUniqueId());
            if (bukkitPlayer == null) {
                instance.getDebugger().info(String.format(
                        "Put %s into trash list due to offline status",
                        player.getUniqueId()
                ));
                trashList.add(player);
            } else {
                System.out.println("Vacuum update player");
                this.instance.getPlayerManager().update(player.getUniqueId());
            }
        }
        // Remove all trashes
        this.instance.getPlayerManager().collection().removeAll(trashList);
    }

}
