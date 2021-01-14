package com.github.playernguyen.databases;

import com.github.playernguyen.OptEconomy;
import com.github.playernguyen.databases.tables.OptEconomyDatabaseTable;
import com.github.playernguyen.databases.tables.OptEconomyDatabaseTableUserMySQL;
import com.github.playernguyen.establishs.OptEconomySQLEstablish;
import com.github.playernguyen.settings.OptEconomySettingTemplate;
import org.jetbrains.annotations.Nullable;

import java.sql.SQLException;

/**
 * The database class of MySQL
 */
public class OptEconomyDatabaseMySQL extends OptEconomyDatabase {
    private final OptEconomy instance;

    /**
     * The constructor of this class
     *
     * @param establish the establish class
     */
    public OptEconomyDatabaseMySQL(OptEconomy instance, OptEconomySQLEstablish establish)
            throws SQLException {
        super(establish);
        // Get the instance
        this.instance = instance;
//        this.setUserTable(new OptEconomyDatabaseTableUserMySQL(
//                (String) instance.getSettingConfiguration().get(OptEconomySettingTemplate.CONNECTION_TABLES_OPTECONOMY),
//                this
//        ));
        this.getTableSet().add(new OptEconomyDatabaseTableUserMySQL(
                instance.getSettingConfiguration().get(OptEconomySettingTemplate.CONNECTION_TABLES_OPTECONOMY),
                this)
        );
    }

    /**
     * Get the table in the storage table set.
     *
     * @param name the table name
     * @return the table by the name param
     */
    @Nullable
    public OptEconomyDatabaseTable<?> getTable(String name) {
        return getTableSet().stream().filter(e -> e.getName().equalsIgnoreCase(name)).findAny().orElse(null);
    }

    /**
     * The instance of OptEconomy
     *
     * @return the instance of OptEconomy
     */
    public OptEconomy getInstance() {
        return instance;
    }
}
