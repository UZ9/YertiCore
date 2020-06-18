package com.yerti.core.cooldown

import com.yerti.core.YertiPlugin.Companion.hookedPlugin
import org.bukkit.Bukkit
import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitTask

class RunnableTask(builder: Builder) {
    private var task: BukkitTask? = null
    private val interval: Int
    private val delay: Int
    private val async: Boolean
    private val runnable: Runnable?
    private val plugin: Plugin?
    fun start() {
        task = if (interval == -1) {
            if (async) Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, runnable, delay.toLong()) else Bukkit.getScheduler().runTaskLater(plugin, runnable, delay.toLong())
        } else {
            if (async) Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, runnable, delay.toLong(), interval.toLong()) else Bukkit.getScheduler().runTaskTimer(plugin, runnable, delay.toLong(), interval.toLong())
        }
    }

    val taskId: Int
        get() = task!!.taskId

    fun stop() {
        task!!.cancel()
        task = null
    }

    class Builder {
        val async = false
        val delay = 0
        var interval = -1
        var runnable: Runnable? = null
        fun doing(runnable: Runnable?): Builder {
            this.runnable = runnable
            return this
        }

        fun every(amount: Int): Builder {
            interval = amount
            return this
        }

        //Just for making logical sense (e.g. every(20).ticks() instead of just every(20))
        fun ticks(): Builder {
            return this
        }

        fun seconds(): Builder {
            interval *= 20
            return this
        }

        fun minutes(): Builder {
            interval *= 20 * 60
            return this
        }

        fun hours(): Builder {
            interval *= 20 * 60 * 60
            return this
        }

        //Not sure why this would every be used but might as well have it here
        fun days(): Builder {
            interval *= 20 * 60 * 60 * 24
            return this
        }

        fun build(): RunnableTask {
            if (runnable == null) throw NullPointerException("Runnable can't be null")
            return RunnableTask(this)
        }
    }

    init {
        delay = builder.delay
        interval = builder.interval
        runnable = builder.runnable
        async = builder.async
        plugin = hookedPlugin
    }
}