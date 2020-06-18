package com.yerti.core.cooldown

import com.yerti.core.YertiPlugin.Companion.hookedPlugin
import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitTask

class Cooldown(private var timeLeft: Int) {
    private val task: BukkitTask
    private fun stop() {
        task.cancel()
    }

    init {
        task = Bukkit.getScheduler().runTaskTimerAsynchronously(hookedPlugin, {
            if (timeLeft == 0) stop()
            timeLeft--
        }, 0L, 20L)
    }
}