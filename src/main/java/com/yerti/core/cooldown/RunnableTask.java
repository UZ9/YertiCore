package com.yerti.core.cooldown;


import com.yerti.core.YertiPlugin;
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


    /**
     * Creates a new RunnableTask off of a RunnableTask Builder
     * @param builder
     */
    public RunnableTask(Builder builder) {
        this.delay = builder.delay;
        this.interval = builder.interval;
        this.runnable = builder.runnable;
        this.async = builder.async;
        this.plugin = YertiPlugin.getHookedPlugin();
    }

    /**
     * Starts the RunnableTask
     */
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

    /**
     * Stops the RunnableTask
     */
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

        /**
         * Sets the operation that the builder does
         * @param runnable
         * @return
         */
        public Builder doing(Runnable runnable) {
            this.runnable = runnable;

            return this;
        }

        /**
         * Sets the interval of the runnable
         * @param amount
         * @return
         */
        public Builder every(int amount) {
            this.interval = amount;
            return this;
        }

        //Just for making logical sense (e.g. every(20).ticks() instead of just every(20))

        /**
         * Sets the interval type to ticks
         * @return
         */
        public Builder ticks() {
            return this;
        }

        /**
         * Sets the interval type to seconds
         * @return
         */
        public Builder seconds() {
            this.interval *= 20;
            return this;
        }

        /**
         * Sets the interval type to minutes
         * @return
         */
        public Builder minutes() {
            this.interval *= 20 * 60;
            return this;
        }

        /**
         * Sets the interval type to hours
         * @return
         */
        public Builder hours() {
            this.interval *= 20 * 60 * 60;
            return this;
        }

        //Not sure why this would every be used but might as well have it here

        /**
         * Sets the interval type to days
         * @return
         */
        public Builder days() {
            this.interval *= 20 * 60 * 60 * 24;
            return this;
        }

        /**
         * Builds the task an returns a RunnableTask
         * @return
         */
        public RunnableTask build() {
            if (runnable == null) throw new NullPointerException("Runnable can't be null");

            return new RunnableTask(this);
        }


    }
}
