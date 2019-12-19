package com.yerti.core.cooldown;


import com.yerti.core.YertiPlugin;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

public class Cooldown {

    private int seconds;
    private BukkitTask task;

    /**
     * Creates a new Cooldown with an amount of seconds
     * @param seconds
     */
    public Cooldown(int seconds) {
        this.seconds = seconds;
        task = Bukkit.getScheduler().runTaskTimerAsynchronously(YertiPlugin.getHookedPlugin(), () -> {
            if (seconds == 0) stop();
            this.seconds--;
        }, 0L, 20L);

    }

    /**
     * Stops the cooldown
     */
    private void stop() {
        task.cancel();
    }

    /**
     * Retrieves the amount of time the cooldown has left
     * @return
     */
    public int getTimeLeft() {
        return  seconds;
    }

}
