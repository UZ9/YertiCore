package com.yerti.core.command;

import java.util.List;
import java.util.Map;

public class CommandInfo {

    private String description;
    private List<String> aliases;
    private String permission;
    private String permissionMessage;
    private String usage;
    private String name;

    @SuppressWarnings("unchecked") //Aliases will always be a string list
    public CommandInfo(String name, Map<String, Object> info) {
        this.name = name;
        this.description = (String) info.get("description");
        this.aliases = (List<String>) info.get("aliases");
        this.permission = (String) info.get("permission");
        this.permissionMessage = (String) info.get("permission-message");
        this.usage = (String) info.get("usage");
    }

    public String getDescription() {
        return description;
    }

    public List<String> getAliases() {
        return aliases;
    }

    public String getPermission() {
        return permission;
    }

    public String getPermissionMessage() {
        return permissionMessage;
    }

    public String getUsage() {
        return usage;
    }
}
