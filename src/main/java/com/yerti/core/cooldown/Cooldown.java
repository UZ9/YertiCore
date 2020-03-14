package com.yerti.core.cooldown;


import com.yerti.banditgames.core.YertiPlugin;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

public class Cooldown {

    private int seconds;
    private BukkitTask task;

    public Cooldown(int seconds) {
        this.seconds = seconds;
        task = Bukkit.getScheduler().runTaskTimerAsynchronously(YertiPlugin.getHookedPlugin(), () -> {
            if (seconds == 0) stop();
            this.seconds--;
        }, 0L, 20L);

    }

    private void stop() {
        task.cancel();
    }

    public int getTimeLeft() {
        return  seconds;
    }

}
