package com.yerti.core.cooldown;


import com.yerti.banditgames.core.YertiPlugin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

public class RunnableTask {

    private BukkitTask task;
    private int interval;
    private int delay;
    private boolean async;
    private Runnable runnable;
    private Plugin plugin;


    public RunnableTask(Builder builder) {
        this.delay = builder.delay;
        this.interval = builder.interval;
        this.runnable = builder.runnable;
        this.async = builder.async;
        this.plugin = YertiPlugin.getHookedPlugin();
    }

    public void start() {
        if (interval == -1) {
            task = async ? Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, runnable, delay) : Bukkit.getScheduler().runTaskLater(plugin, runnable, delay);
        } else {
            task = async ? Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, runnable, delay, interval) : Bukkit.getScheduler().runTaskTimer(plugin, runnable, delay, interval);
        }
    }

    public int getTaskId() {
        return task.getTaskId();
    }

    public void stop() {
        task.cancel();
        task = null;
    }


    public static class Builder {

        private boolean async;
        private int delay = 0;
        private int interval = -1;
        private Runnable runnable;

        public Builder() {

        }

        public Builder doing(Runnable runnable) {
            this.runnable = runnable;

            return this;
        }

        public Builder every(int amount) {
            this.interval = amount;
            return this;
        }

        //Just for making logical sense (e.g. every(20).ticks() instead of just every(20))
        public Builder ticks() {
            return this;
        }

        public Builder seconds() {
            this.interval *= 20;
            return this;
        }

        public Builder minutes() {
            this.interval *= 20 * 60;
            return this;
        }

        public Builder hours() {
            this.interval *= 20 * 60 * 60;
            return this;
        }

        //Not sure why this would every be used but might as well have it here
        public Builder days() {
            this.interval *= 20 * 60 * 60 * 24;
            return this;
        }

        public RunnableTask build() {
            if (runnable == null) throw new NullPointerException("Runnable can't be null");

            return new RunnableTask(this);
        }


    }
}
