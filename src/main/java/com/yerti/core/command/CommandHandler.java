package com.yerti.core.command;

import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;

public class CommandHandler {

    public static Map<String, CommandInfo> getCommands(Plugin plugin) {
        Map<String, CommandInfo> infoMap = new HashMap<>();

        plugin.getDescription().getCommands().forEach((key, value) -> infoMap.put(key, new CommandInfo(value)));

        return infoMap;
    }

}
