package com.yerti.core.command

import org.bukkit.command.Command
import org.bukkit.command.CommandException
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import java.util.*

class BukkitCommandExtension(private val command: BukkitCommand, private val executor: CommandExecutor) : Command(command.name, command.description, command.usage, Arrays.asList(*command.aliases)) {
    override fun execute(commandSender: CommandSender, s: String, strings: Array<String>): Boolean {
        try {
            executor.onCommand(commandSender, this, s, strings)
        } catch (e: CommandException) {
            e.printStackTrace()
        }
        return false
    }

}