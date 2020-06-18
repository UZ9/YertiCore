package com.yerti.core.command

import java.lang.reflect.Method
import java.util.*

class BukkitCommand(var name: String, var permission: String, var usage: String, var description: String, var aliases: Array<String>, var method: Method, var executor: Any?) {
    private val subCommands: MutableList<BukkitCommand>
    private var parent: BukkitCommand? = null
    fun addSubCommand(command: BukkitCommand) {
        command.parent = this
        if (!subCommands.contains(command)) {
            subCommands.add(command)
        }
    }

    fun getSubCommands(): List<BukkitCommand> {
        return subCommands
    }

    init {
        subCommands = ArrayList()
    }
}