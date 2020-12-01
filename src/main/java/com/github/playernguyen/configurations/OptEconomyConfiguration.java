package com.github.playernguyen.configurations;

import com.github.playernguyen.OptEconomy;
import com.google.common.base.Preconditions;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * Configuration class to init, set-up the configuration items
 *
 * @param <T> the template type. This template is necessary for the configuration object
 *            because configuration object will use this as default value
 */
public class OptEconomyConfiguration<T extends OptEconomyTemplate> {

    private final File file;
    private FileConfiguration fileConfiguration;

    /**
     * Constructor of class
     *
     * @param instance  the instance class {@link OptEconomy}
     * @param name      the name of configuration. i.e config.yml,...
     * @param declarers the declarers of template like Enum.values()
     * @param parent    the nullable parent directory name
     * @throws IOException cannot save the configuration
     * @see OptEconomyConfiguration#save()
     */
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

    /**
     * Reload the configuration class (reload)
     */
    public void reload() {
        this.fileConfiguration = YamlConfiguration.loadConfiguration(this.file);
    }

    /**
     * Save the configuration
     * @throws IOException cannot save the configuration file
     */
    public void save() throws IOException {
        this.fileConfiguration.save(this.file);
    }

    /**
     * Getter the configured object
     * @param key the key in template
     * @return the Object class of {@link T#path()}
     */
    @NotNull
    public Object get(@NotNull T key) {
        Preconditions.checkNotNull(key);
        return Objects.requireNonNull(this.fileConfiguration.get(key.path()));
    }


}
