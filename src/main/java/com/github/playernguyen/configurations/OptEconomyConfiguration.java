package com.github.playernguyen.configurations;

import com.github.playernguyen.OptEconomy;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;

/**
 * Configuration class to init, set-up the configuration items
 *
 * @param <T> the template type. This template is necessary for the configuration object
 *           because configuration object will use this as default value
 */
public class OptEconomyConfiguration<T extends OptEconomyTemplate> {

    private final File file;
    private FileConfiguration fileConfiguration;

    public OptEconomyConfiguration(OptEconomy instance, String name, T[] declarers, @Nullable String parent) throws IOException {
        // Create data folder
        File dataFolder = instance.getDataFolder();
        if (!dataFolder.exists() && !dataFolder.mkdir()) {
            throw new IllegalStateException("Cannot access and make the data folder.");
        }
        // Create folder
        File parentFolder = (parent == null || parent.equalsIgnoreCase(""))
                ? dataFolder : new File(dataFolder, parent);
        // File & config init
        this.file = new File(parentFolder, name);
        this.fileConfiguration = YamlConfiguration.loadConfiguration(this.file);
        // Load with loader
        for (T declarer : declarers) {
            if (!this.fileConfiguration.contains(declarer.path())) {
                this.fileConfiguration.set(declarer.path(), declarer.declare());
            }
        }
        // Save changes
        save();
    }

    public void reload() {
        this.fileConfiguration = YamlConfiguration.loadConfiguration(this.file);
    }

    public void save () throws IOException {
        this.fileConfiguration.save(this.file);
    }
}
