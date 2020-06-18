package com.yerti.core.utils

import com.yerti.core.YertiPlugin.Companion.hookedPlugin
import org.bukkit.Bukkit

class Animation /*int current = 0;
        for (Runnable runnable : runnables) {
            System.out.println(current);
            System.out.println("Run task at " + interval + "ticks");
            Bukkit.getScheduler().runTaskLater(YertiPlugin.getHookedPlugin(), runnable, interval * 20L);
            current++;
        }*/(private val interval: Int) {
    private var times = 0
    private var stopped = false
    private var runnables: Array<Runnable> = null
    private var then: Runnable? = null
    fun doing(vararg runnables: Runnable): Animation {
        this.runnables = runnables
        return this
    }

    operator fun times(times: Int): Animation {
        this.times = times
        return this
    }

    fun start(): Animation {
        runAnimation(0, runnables)
        return this
    }

    fun stop(): Animation {
        stopped = true
        return this
    }

    fun then(runnable: Runnable?): Animation {
        then = runnable
        return this
    }

    private fun runAnimation(current: Int, runnables: Array<Runnable>) {
        var current = current
        if (stopped) return
        if (current == runnables.size) {
            if (times > 1) {
                current = 0
                times--
            } else {
                if (then != null) {
                    Bukkit.getScheduler().runTaskLater(hookedPlugin, then, interval.toLong())
                }
                return
            }
        }
        Bukkit.getScheduler().runTaskLater(hookedPlugin, runnables[current], interval.toLong())
        val finalCurrent = current
        Bukkit.getScheduler().runTaskLater(hookedPlugin, { runAnimation(finalCurrent + 1, runnables) }, interval.toLong())
    }

}