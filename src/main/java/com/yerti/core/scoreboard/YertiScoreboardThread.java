package com.yerti.core.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class YertiScoreboardThread extends Thread {

    @Override
    @SuppressWarnings("InfiniteLoopStatement")
    public void run() {
        while (true) {
            for (Player player : Bukkit.getOnlinePlayers()) {

                YertiScoreboard board = YertiScoreboardHandler.getInstance().getScoreboard(player);

                if (board != null) {
                    board.update(player);
                }
            }

            try {
                Thread.sleep(YertiScoreboardHandler.getInstance().getDelay());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
