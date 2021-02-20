package com.github.playernguyen.localizes;

import com.github.playernguyen.configurations.OptEconomyTemplate;

public enum  OptEconomyLocalizeTemplate implements OptEconomyTemplate {

    PREFIX("Prefix", "&7[&cOptEconomy&7] "),

    COMMAND_NO_PERMISSION("Command.NoPermission", "&cYou are not having the permission to do this command"),

    COMMAND_MAIN_COMMAND_DESCRIPTION("Command.OptEconomy.MainDescription", "The command of OptEconomy"),
    COMMAND_PARAMETER_COMMAND("Command.Parameter.Command", "command"),

    COMMAND_PARAM_REQUIRED("Command.Parameters.Required", "required"),
    COMMAND_PARAM_NON_REQUIRED("Command.Parameters.Non-Required", "non-required"),

    COMMAND_REQUIRE_PLAYER_SENDER("Command.RequirePlayerSender", "&cThis command is required player sender."),

    COMMAND_ME_RESPONSE("Command.Me.Response", "&7You currently have &6%point% %currency% &7."),
    COMMAND_ME_DESCRIPTION("Command.Me.Description", "Reveal the current balance of yourself"),

    COMMAND_GIVE_DESCRIPTION("Command.Give.Description", "Give points to players"),

    COMMAND_SUB_COMMAND_NOT_FOUND("Command.SubCommandNotFound", "&cCommand not found."),

    COMMAND_PARAM_TARGET("Command.Parameters.Target", "target"),
    COMMAND_PARAM_AMOUNT("Command.Parameters.Amount", "amount"),

    COMMAND_RESPONSE_INVALID_ARGUMENTS("Command.Response.InvalidArguments",
            "&cInvalid arguments"),
    COMMAND_RESPONSE_INVALID_NUMBER_FORMAT("Command.Response.ErrorNumberFormat",
            "&cThe value must be a number, your input is invalid."),
    COMMAND_RESPONSE_SQL_ERROR("Command.Response.SQLError",
            "&cThe connect to database server is down, please contact to admin for more information."),
    COMMAND_RESPONSE_INVALID_NUMBER_VALUE("Command.Response.InvalidNumberValue",
            "&cInvalid number value, must be greater than 0."),

    COMMAND_GIVE_RESPONSE_PLAYER_NOT_FOUND("Command.Give.Response.PlayerNotFound",
            "&cThis player are not existing on server, using &6/p offlinegive &cwhether you want to skip the valid step."),
    COMMAND_GIVE_RESPONSE_USE_INTEGER("Command.Give.Response.UseInteger",
            "&cThe value must be an integer."),
    COMMAND_GIVE_RESPONSE_SUCCEED_TRANSACT("Command.Give.Response.SucceedTransact",
            "&aGave &c%deposit% %currency% &ato player %player%."),
    COMMAND_GIVE_RESPONSE_FAILED_TRANSACT("Command.Give.Response.FailedTransact",
            "&cHaving an issue whereas update the account %player%."),

    COMMAND_TAKE_DESCRIPTION("Command.Take.Description", "Take player point"),
    COMMAND_TAKE_RESPONSE_PLAYER_NOT_FOUND("Command.Take.Response.PlayerNotFound",
            "&Player %player% are not existing on server."),
    COMMAND_TAKE_RESPONSE_TOOK("Command.Take.Response.Took",
            "&6Took &a%point% %currency% &6from &a%player%&6."),
    COMMAND_TAKE_RESPONSE_UNKNOWN_ERROR("Command.Take.Response.UnknownError",
                                       "&cUnknown error occurred whereas taking point of %player%.")
    ;

    private final String path;
    private final Object declare;

    OptEconomyLocalizeTemplate(String path, Object declare) {
        this.path = path;
        this.declare = declare;
    }

    @Override
    public String path() {
        return path;
    }

    @Override
    public Object declare() {
        return declare;
    }
}
