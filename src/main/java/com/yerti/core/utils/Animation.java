package com.yerti.core.utils;


import com.yerti.core.YertiPlugin;
import org.bukkit.Bukkit;

public class Animation {

    private int interval;
    private int times;
    private boolean stopped = false;
    private Runnable[] runnables;
    private Runnable then;

    public Animation(int interval) {
        this.interval = interval;


        /*int current = 0;
        for (Runnable runnable : runnables) {
            System.out.println(current);
            System.out.println("Run task at " + interval + "ticks");
            Bukkit.getScheduler().runTaskLater(YertiPlugin.getHookedPlugin(), runnable, interval * 20L);
            current++;
        }*/
    }

    public Animation doing(Runnable... runnables) {
        this.runnables = runnables;
        return this;
    }

    public Animation times(int times) {
        this.times = times;
        return this;
    }

    public Animation start() {
        runAnimation(0, runnables);
        return this;
    }

    public Animation stop() {
        stopped = true;
        return this;
    }

    public Animation then(Runnable runnable) {
        this.then = runnable;
        return this;
    }

    private void runAnimation(int current, Runnable[] runnables) {
        if (stopped) return;
        if (current == runnables.length) {
            if (times > 1) {
                current = 0;
                times--;
            } else {
                if (then != null) {
                    Bukkit.getScheduler().runTaskLater(YertiPlugin.getHookedPlugin(), then, interval);
                }
                return;
            }

        }

        Bukkit.getScheduler().runTaskLater(YertiPlugin.getHookedPlugin(), runnables[current], interval);
        int finalCurrent = current;
        Bukkit.getScheduler().runTaskLater(YertiPlugin.getHookedPlugin(), () -> runAnimation(finalCurrent + 1, runnables), interval);
    }


}
