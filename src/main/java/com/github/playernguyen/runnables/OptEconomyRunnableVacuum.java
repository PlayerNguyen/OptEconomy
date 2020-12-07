package com.github.playernguyen.runnables;

import com.github.playernguyen.OptEconomy;
import com.github.playernguyen.loggers.OptEconomyExceptionCatcher;
import com.github.playernguyen.settings.OptEconomySettingTemplate;
import com.github.playernguyen.vacuums.OptEconomyVacuum;

import java.sql.SQLException;

/**
 * This runnable will call a vacuum class to forcibly clean the cache storage
 *  in the period time.
 */
public class OptEconomyRunnableVacuum extends OptEconomyRunnable {

    private final OptEconomyVacuum vacuum;
    private final long maxCounter;
    private long counter;

    public OptEconomyRunnableVacuum(OptEconomy inst, OptEconomyVacuum vacuum) {
        super(inst);
        // Vacuum insert
        this.vacuum = vacuum;
        this.maxCounter = (int) this.getInstance().getSettingConfiguration()
                .get(OptEconomySettingTemplate.VACUUM_CACHE_UPDATE_DURATION);
        this.counter = maxCounter;
    }

    @Override
    public void run() {
        // Ticking
        counter --;

        // Processing
        if (counter <= 0) {
            try {
                vacuum.cleanUp();
            } catch (SQLException e) {
                OptEconomyExceptionCatcher.stackTrace(e);
            }
        }

        // Resetting
        this.reset();
    }

    private void reset() {
        this.counter = maxCounter;
    }
}
