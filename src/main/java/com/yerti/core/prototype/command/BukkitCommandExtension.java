package com.yerti.core.prototype.command;

import org.bukkit.command.CommandException;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

public class BukkitCommandExtension extends org.bukkit.command.Command {

    private BukkitCommand command;
    private CommandExecutor executor;

    protected BukkitCommandExtension(BukkitCommand command, CommandExecutor executor) {
        super(command.getName(), command.getDescription(), command.getUsage(), Arrays.asList(command.getAliases()));

        this.command = command;
        this.executor = executor;
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {

        try {
            executor.onCommand(commandSender, this, s, strings);
        } catch (CommandException e) {
            e.printStackTrace();
        }

        return false;
    }
}
