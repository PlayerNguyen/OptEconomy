package com.github.playernguyen.configurations;

import com.github.playernguyen.OptEconomy;
import com.google.common.base.Preconditions;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.TreeSet;

/**
 * Configuration class to init, set-up the configuration items
 *
 * @param <T> the template type. This template is necessary for the configuration object
 *            because configuration object will use this as default value
 */
public abstract class OptEconomyConfiguration<T extends OptEconomyTemplate> {

    private final OptEconomy instance;
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
        this.instance = instance;
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
        List<T> list = Arrays.asList(declarers);
        list.sort((o1, o2) -> o1.path().compareToIgnoreCase(o2.path()));
        for (T declarer : list) {
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
        instance.getDebugger().info(String.format(
                "Loading FileConfiguration from %s", this.file.getPath()
        ));
        this.fileConfiguration = YamlConfiguration.loadConfiguration(this.file);
    }

    /**
     * Save the configuration
     * @throws IOException cannot save the configuration file
     */
    public void save() throws IOException {
        instance.getDebugger().info(String.format(
                "Saving <%s> (%s)", this.getClass().getSimpleName(), this.file.getPath()
        ));
        this.fileConfiguration.save(this.file);
    }

    /**
     * Getter the configured object
     * @param key the key in template
     * @return the Object class of {@link T#path()}
     */
    @SuppressWarnings(value = "unchecked")
    public <TType> TType get(@NotNull T key) {
        Preconditions.checkNotNull(key);
        instance.getDebugger().info(String.format(
                "Getting %s <%s>", key.path(), this.getClass().getSimpleName()
        ));
        return (TType) Objects.requireNonNull(this.fileConfiguration.get(key.path()));
    }

    /**
     * Get the configured object as boolean cast
     * @param key the key in template
     * @return the Boolean object which was configured
     */
    public boolean getBoolean(@NotNull T key) {
        return (Boolean) get(key);
    }

    /**
     * Get the configured object as string cast
     * @param key the key in template key
     * @return the String object which was configured
     */
    public String getString(@NotNull T key) {
        return (String) get(key);
    }

}
