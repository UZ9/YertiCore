package com.yerti.core.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Custom abstract command handler
 * To use, extend CustomCommand and implement the methods needed
 * in OnEnable, create the CustomCommand by doing new CustomCommad(parameters).initCommand(),
 * where parameters are any needed parameters you need.
 */
public abstract class CustomCommand implements CommandExecutor {

    protected static CommandMap commandMap;

    String command;
    protected String usage;
    String description;
    String noPermMessage;
    List<String> aliases;
    String permission;

    public CustomCommand(String command, String permission) {
        this(command, null, null, null, permission, null);
    }

    public CustomCommand(String command, String usage, String permission) {
        this(command, usage, null, null, permission, null);
    }

    public CustomCommand(String command, String usage, String description, String permission) {
        this(command, usage, description, null, permission, null);
    }

    public CustomCommand(String command, String usage, String description, String noPermMessage, String permission) {
        this(command, usage, description, noPermMessage, permission, null);
    }



    public CustomCommand(String command, String usage, String description, String permission, List<String> aliases) {
        this(command, usage, description, null, permission, aliases);
    }

    public CustomCommand(String command, String usage, String description, String noPermMessage, String permission, List<String> aliases) {
        this.command = command.toLowerCase();
        this.usage = usage;
        this.description = description;
        this.noPermMessage = noPermMessage;
        this.permission = permission;
        this.aliases = aliases;
    }

    public void initCommand() {
        Reflection reflectionCommand = new Reflection(this.command);

        if (usage != null) reflectionCommand.setUsage(this.usage);
        if (description != null) reflectionCommand.setDescription(this.description);
        if (noPermMessage != null) {
            reflectionCommand.setPermissionMessage(this.noPermMessage);
        } else {
            reflectionCommand.setPermissionMessage(ChatColor.RED + "You do not have permission to execute this command!");
        }

        if (aliases != null) reflectionCommand.setAliases(aliases);


        getCommandMap().register("", reflectionCommand);
        reflectionCommand.setExecutor(this);


    }

    public CommandMap getCommandMap() {
        if (commandMap == null) {
            try {
                final Field f = Bukkit.getServer().getClass().getDeclaredField("commandMap");
                f.setAccessible(true);
                commandMap = (CommandMap) f.get(Bukkit.getServer());
                return getCommandMap();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            return commandMap;
        }

        return getCommandMap();
    }


    private final class Reflection extends Command {
        private CustomCommand customCommand = null;
        protected Reflection(String name) {
            super(name);
        }

        public void setExecutor(CustomCommand customCommand) {
            this.customCommand = customCommand;
        }

        @Override
        public boolean execute(CommandSender sender, String commandLabel, String[] args) {
            if (customCommand != null) {
                if (permission != null) {
                    if (sender.hasPermission(permission)) {
                        return customCommand.onCommand(sender, this, commandLabel, args);
                    } else {
                        return false;
                    }
                } else {
                    return customCommand.onCommand(sender, this, commandLabel, args);
                }

            }
            return false;
        }

    }

    public abstract boolean onCommand(CommandSender sender, Command cmd, String label, String[] args);



}