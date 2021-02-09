package com.github.playernguyen.commands.executors;

import com.github.playernguyen.OptEconomy;
import com.github.playernguyen.commands.OptEconomyCommand;
import com.github.playernguyen.commands.OptEconomyCommandParameter;
import com.github.playernguyen.commands.OptEconomyCommandResult;
import com.github.playernguyen.commands.subs.OptEconomyCommandSub;
import com.github.playernguyen.localizes.OptEconomyLocalizeTemplate;
import com.github.playernguyen.settings.OptEconomySettingTemplate;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * An abstract class command executor
 */
public abstract class OptEconomyCommandExecutor implements OptEconomyCommand, TabExecutor {
    private final OptEconomy instance;
    private final String name;
    private final String description;
    private final List<OptEconomyCommandParameter> parameters = new ArrayList<>();
    private final Set<OptEconomyCommandSub> subCommandList = new TreeSet<>();

    public OptEconomyCommandExecutor(OptEconomy instance, String name, String description) {
        this.instance = instance;
        this.name = name;
        this.description = description;
    }

    /**
     * Sub-command set
     *
     * @return the sub command
     */
    public Set<OptEconomyCommandSub> getSubCommandList() {
        return subCommandList;
    }

    @Override
    public OptEconomy getInstance() {
        return instance;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public List<OptEconomyCommandParameter> getParameters() {
        return parameters;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender,
                             @NotNull Command command,
                             @NotNull String label,
                             @NotNull String[] args) {
        // Doing the execute
        OptEconomyCommandResult execute = this.execute(sender, Arrays.asList(args));
        if (execute == null) {
            // Doing nothing here UwU
            this.instance.getDebugger().info(String.format("Skipping the execute because of the null response <%s>",
                    this.getClass().getSimpleName()));
        } else {
            switch (execute) {
                case NO_PERMISSION: {
                    // Whether sender have no permission
                    sender.sendMessage(
                            instance.getLocalizeConfiguration().prefixedColor(OptEconomyLocalizeTemplate
                                    .COMMAND_NO_PERMISSION).toStringWithPlaceholder(
                                    (sender instanceof Player)
                                            ? (Player) sender
                                            : null
                            )
                    );
                    break;
                }
                case NOTHING:
                case NULL: {
                    this.instance.getDebugger().info(String.format("Skipping the execute because of the null " +
                                    "response case <%s>",
                            this.getClass().getSimpleName()));
                    break;
                }
            }
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender,
                                                @NotNull Command command,
                                                @NotNull String alias,
                                                @NotNull String[] args) {
        // Whether disable, switch to sub-command
        if (!instance.getSettingConfiguration().getBoolean(OptEconomySettingTemplate.COMMAND_TAB_COMPLETION)) {
            return null;
        }
        // Otherwise, using tab to complete
        return this.tab(sender, Arrays.asList(args));
    }

    public void registerSubCommand(OptEconomyCommandSub sub) {
        this.subCommandList.add(sub);
    }

}