package com.yerti.core.prototype.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class CommandFramework implements CommandExecutor {

    private final Plugin plugin;
    private Map<BukkitCommand, Object> commands = new HashMap<>();
    private CommandMap map;

    public CommandFramework(Plugin plugin) {
        this.plugin = plugin;
        map = getCommandMap();
    }

    public void register(Object object) {
        final Class<?> clazz = object.getClass();

        final Map<String, BukkitCommand> commandMap = new HashMap<>();

        for (Method method : clazz.getMethods()) {
            if (!method.isAnnotationPresent(com.yerti.core.prototype.command.Command.class)) {
                continue;
            }

            Class<?>[] parameterType = method.getParameterTypes();

            if (parameterType.length > 3)
                if (parameterType[0] != CommandSender.class || parameterType[1] != org.bukkit.command.Command.class || parameterType[2] != String[].class) {
                    Bukkit.getLogger().log(Level.SEVERE, "Unable to register command " + method.getName() + ".");
                }
            final com.yerti.core.prototype.command.Command command = method.getAnnotation(com.yerti.core.prototype.command.Command.class);

            final BukkitCommand customCommand = new BukkitCommand(command.name(), command.permission(), command.usage(), command.description(), command.aliases(), method, object);

            final BukkitCommandExtension bukkitCommandExtension = new BukkitCommandExtension(customCommand, this);

            commandMap.put(plugin.getName(), customCommand);
            commands.put(customCommand, object);
            map.register(plugin.getName(), bukkitCommandExtension);

        }

        for (Method method : clazz.getClass().getMethods()) {
            if (!method.isAnnotationPresent(SubCommand.class)) {
                continue;
            }

            Class<?>[] parameterType = method.getParameterTypes();

            if (parameterType.length > 3)
                if (parameterType[0] != CommandSender.class || parameterType[1] != org.bukkit.command.Command.class || parameterType[2] != String[].class) {
                    Bukkit.getLogger().log(Level.SEVERE, "Unable to register subcommand " + method.getName() + "."); }
                SubCommand subCommand = method.getAnnotation(SubCommand.class);
                BukkitCommand parent = commandMap.get(subCommand.parent());

                BukkitCommand command = new BukkitCommand(
                  subCommand.name(),
                  subCommand.permission(),
                  subCommand.usage(),
                  subCommand.description(),
                  new String[0],
                  method,
                  object
                );

                parent.addSubCommand(command);


        }

    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command cmd, String s, String[] strings) {
        for (BukkitCommand bukkitCommand : commands.keySet()) {
            if (bukkitCommand.getName().equalsIgnoreCase(cmd.getName())) {
                Method method = bukkitCommand.getMethod();
                Object object = commands.get(bukkitCommand);
                com.yerti.core.prototype.command.Command command = method.getAnnotation(com.yerti.core.prototype.command.Command.class);

                if (!command.permission().isEmpty() && !commandSender.hasPermission(command.permission())) {
                    //TODO: DI Error message from subclass
                    commandSender.sendMessage("You don't have permission for that!");
                    return true;
                }

                //TODO: Only Player
                //if (!command.)


                try {
                    method.invoke(object, commandSender,  cmd, strings);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }

            for (BukkitCommand subCommand : bukkitCommand.getSubCommands()) {
                Method subMethod = subCommand.getMethod();
                Object object = subCommand.getExecutor();
                SubCommand command = subMethod.getAnnotation(SubCommand.class);

            }
        }

        return false;
    }

    private CommandMap getCommandMap() {
        if (map == null) {
            try {
                final Field f = Bukkit.getServer().getClass().getDeclaredField("commandMap");
                f.setAccessible(true);
                map = (CommandMap) f.get(Bukkit.getServer());
                return getCommandMap();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            return map;
        }

        return getCommandMap();
    }
}
