package com.yerti.core.command;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class BukkitCommand {

    private final List<BukkitCommand> subCommands;
    private String name;
    private String permission;
    private String usage;
    private String description;
    private String[] aliases;
    private Method method;
    private BukkitCommand parent = null;
    private Object executor;

    public BukkitCommand(String name, String permission, String usage, String description, String[] aliases, Method method, Object exector) {
        this.name = name;
        this.permission = permission;
        this.usage = usage;
        this.description = description;
        this.aliases = aliases;
        this.method = method;
        this.executor = exector;
        this.subCommands = new ArrayList<BukkitCommand>();
    }

    public void addSubCommand(BukkitCommand command) {
        command.parent = this;

        if (!subCommands.contains(command)) {
            subCommands.add(command);
        }
    }

    public String getName() {
        return name;
    }

    public List<BukkitCommand> getSubCommands() {
        return subCommands;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String[] getAliases() {
        return aliases;
    }

    public void setAliases(String[] aliases) {
        this.aliases = aliases;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object getExecutor() {
        return executor;
    }

    public void setExecutor(Object executor) {
        this.executor = executor;
    }
}
