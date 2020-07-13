package com.yerti.core.scoreboard;

import org.bukkit.entity.Player;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class YertiScoreboardHandler {

    private static YertiScoreboardHandler instance;

    private final Map<UUID, YertiScoreboard> boardData = new HashMap<>();
    private final long delay;

    public YertiScoreboardHandler(Duration delay) {
        instance = this;
        this.delay = delay.toMillis();
    }

    public YertiScoreboard getScoreboard(Player player) {
        return getScoreboard(player.getUniqueId());
    }

    public YertiScoreboard getScoreboard(UUID uuid) {
        return boardData.get(uuid);
    }

    public long getDelay() {
        return delay;
    }

    public static YertiScoreboardHandler getInstance() {
        return instance;
    }
}
