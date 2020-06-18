package com.yerti.core.command

import com.yerti.core.YertiPlugin
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandMap
import org.bukkit.command.CommandSender
import java.lang.reflect.InvocationTargetException
import java.util.*
import java.util.logging.Level

class CommandFramework(private val plugin: YertiPlugin, commandClass: Class<*>) : CommandExecutor {
    private val commands: MutableMap<BukkitCommand, Any?> = HashMap()
    private var map: CommandMap?
    private val commandClass: Class<*>
    private fun registerCommands() {
        val commandMap: MutableMap<String?, BukkitCommand> = HashMap()
        //for (Method method : clazz.getMethods()) {
        for (method in commandClass.methods) {
            var `object`: Any? = null
            val name = commandClass.name
            try {
                val clazz = Class.forName(name)
                `object` = clazz.newInstance()
            } catch (e: ClassNotFoundException) {
                e.printStackTrace()
            } catch (e: InstantiationException) {
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            }
            if (!method.isAnnotationPresent(CustomCommand::class.java)) {
                continue
            }
            val parameterType = method.parameterTypes
            if (parameterType.size > 3) if (parameterType[0] != CommandSender::class.java || parameterType[1] != Command::class.java || parameterType[2] != Array<String>::class.java) {
                Bukkit.getLogger().log(Level.SEVERE, "Unable to register command " + method.name + ".")
            }
            val command = method.getAnnotation(CustomCommand::class.java)
            val customCommand = BukkitCommand(command.name, command.permission, command.usage, command.description, command.aliases, method, `object`)
            val bukkitCommandExtension = BukkitCommandExtension(customCommand, this)
            commandMap[customCommand.name] = customCommand
            commands[customCommand] = `object`
            map!!.register(plugin.name, bukkitCommandExtension)
        }
        for (method in commandClass.methods) {
            val `object`: Any = method.javaClass
            if (!method.isAnnotationPresent(SubCommand::class.java)) {
                continue
            }
            val parameterType = method.parameterTypes
            if (parameterType.size > 3) if (parameterType[0] != CommandSender::class.java || parameterType[1] != Command::class.java || parameterType[2] != Array<String>::class.java) {
                Bukkit.getLogger().log(Level.SEVERE, "Unable to register subcommand " + method.name + ".")
            }
            val subCommand = method.getAnnotation(SubCommand::class.java)
            val parent = commandMap[subCommand.parent]
            if (parent == null) {
                Bukkit.getLogger().log(Level.SEVERE, "Unable to register subcommand " + method.name + " because the parent " + subCommand.parent + " was null.")
                continue
            }
            val command = BukkitCommand(
                    subCommand.name,
                    subCommand.permission,
                    subCommand.usage,
                    subCommand.description, emptyArray(),
                    method,
                    `object`
            )
            parent.addSubCommand(command)
        }
    }

    override fun onCommand(commandSender: CommandSender, cmd: Command, s: String, strings: Array<String>): Boolean {
        for (bukkitCommand in commands.keys) {
            if (bukkitCommand.name.equals(cmd.name, ignoreCase = true)) {
                val method = bukkitCommand.method
                val `object` = commands[bukkitCommand]
                val customCommand = method.getAnnotation(CustomCommand::class.java)
                if (customCommand.permission.isNotEmpty() && !commandSender.hasPermission(customCommand.permission)) { //TODO: DI Error message from subclass
                    commandSender.sendMessage("You don't have permission for that!")
                    return true
                }
                //TODO: Only Player
//if (!customCommand.)
                if (bukkitCommand.getSubCommands().isEmpty() || strings.isEmpty()) {
                    try {
                        method.invoke(`object`, plugin, commandSender, cmd, strings)
                    } catch (e: IllegalAccessException) {
                        e.printStackTrace()
                    } catch (e: InvocationTargetException) {
                        e.printStackTrace()
                    }
                } else {
                    for (subCommand in bukkitCommand.getSubCommands()) {
                        val subMethod = subCommand.method
                        var obj: Any? = null
                        val name = commandClass.name
                        try {
                            val clazz = Class.forName(name)
                            obj = clazz.newInstance()
                        } catch (e: ClassNotFoundException) {
                            e.printStackTrace()
                        } catch (e: InstantiationException) {
                            e.printStackTrace()
                        } catch (e: IllegalAccessException) {
                            e.printStackTrace()
                        }
                        val command = subMethod!!.getAnnotation(SubCommand::class.java)
                        if (command.permission.isNotEmpty() && !commandSender.hasPermission(command.permission)) { //TODO: DI Error message from subclass
                            commandSender.sendMessage("You don't have permission for that!")
                            return true
                        }
                        if (!strings[0].equals(command.name, ignoreCase = true)) continue
                        try {
                            subMethod.invoke(obj, plugin, commandSender, cmd, strings)
                        } catch (e: IllegalAccessException) {
                            e.printStackTrace()
                        } catch (e: InvocationTargetException) {
                            e.printStackTrace()
                        }
                    }
                }
            }
        }
        return false
    }

    private val commandMap: CommandMap? = null
        get() {
            if (map == null) {
                try {
                    val f = Bukkit.getServer().javaClass.getDeclaredField("commandMap")
                    f.isAccessible = true
                    map = f[Bukkit.getServer()] as CommandMap
                    return commandMap
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            } else {
                return map as CommandMap
            }
            return field
        }

    init {
        map = commandMap
        this.commandClass = commandClass
        registerCommands()
    }
}