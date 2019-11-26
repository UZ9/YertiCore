package com.yerti.core.prototype.command;

import com.yerti.runecraft.RuneCraft;
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
    private Class commandClass;

    public CommandFramework(Plugin plugin, Class commandClass) {
        this.plugin = plugin;
        map = getCommandMap();
        this.commandClass = commandClass;

        registerCommands();





    }

    public void registerCommands() {
        final Map<String, BukkitCommand> commandMap = new HashMap<>();

        //for (Method method : clazz.getMethods()) {
        for (Method method  : commandClass.getMethods()) {
            Object object = null;
            String name = commandClass.getName();
            try {
                Class<?> clazz = Class.forName(name);
                object = clazz.newInstance();


            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }

            System.out.println("Found class " + object.getClass().getName());

            if (!method.isAnnotationPresent(CustomCommand.class)) {
                continue;
            }


            Class<?>[] parameterType = method.getParameterTypes();

            if (parameterType.length > 3)
                if (parameterType[0] != CommandSender.class || parameterType[1] != Command.class || parameterType[2] != String[].class) {
                    Bukkit.getLogger().log(Level.SEVERE, "Unable to register command " + method.getName() + ".");
                }
            final CustomCommand command = method.getAnnotation(CustomCommand.class);

            final BukkitCommand customCommand = new BukkitCommand(command.name(), command.permission(), command.usage(), command.description(), command.aliases(), method, object);

            final BukkitCommandExtension bukkitCommandExtension = new BukkitCommandExtension(customCommand, this);

            commandMap.put(plugin.getName(), customCommand);
            commands.put(customCommand, object);
            map.register(plugin.getName(), bukkitCommandExtension);

        }

        for (Method method : commandClass.getMethods()) {
            Object object = method.getClass();

            if (!method.isAnnotationPresent(SubCommand.class)) {
                continue;
            }

            Class<?>[] parameterType = method.getParameterTypes();

            if (parameterType.length > 3)
                if (parameterType[0] != CommandSender.class || parameterType[1] != Command.class || parameterType[2] != String[].class) {
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
                CustomCommand customCommand = method.getAnnotation(CustomCommand.class);
                if (!customCommand.permission().isEmpty() && !commandSender.hasPermission(customCommand.permission())) {
                    //TODO: DI Error message from subclass
                    commandSender.sendMessage("You don't have permission for that!");
                    return true;
                }

                //TODO: Only Player
                //if (!customCommand.)


                try {
                    method.invoke(object, commandSender,  cmd, strings);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    if (RuneCraft.getInstance().debugMode()) {
                        System.out.println("Caught invoke exception.");
                        e.printStackTrace();
                    } else {
                        System.out.println("Caught invoke exception.");
                    }

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
