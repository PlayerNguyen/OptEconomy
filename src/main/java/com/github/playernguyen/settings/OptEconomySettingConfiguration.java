package com.github.playernguyen.settings;

import com.github.playernguyen.OptEconomy;
import com.github.playernguyen.OptEconomyConstants;
import com.github.playernguyen.configurations.OptEconomyConfiguration;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

/**
 * The setting configuration class.
 */
public class OptEconomySettingConfiguration
        extends OptEconomyConfiguration<OptEconomySettingTemplate> {

    /**
     * Constructor
     * @param instance the main class instance
     * @throws IOException cannot save the file
     */
    public OptEconomySettingConfiguration(OptEconomy instance) throws IOException {
        super(instance,
                OptEconomyConstants.SETTING_FILE_NAME,
                OptEconomySettingTemplate.values(),
                null
        );
    }
}
