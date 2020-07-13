package com.yerti.core.scoreboard;

import com.yerti.core.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.List;

public abstract class YertiScoreboard {

    private Scoreboard scoreboard;
    private Objective obj;

    public YertiScoreboard(String scoreboardTitle, DisplaySlot slot) {
        this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

        this.obj = this.scoreboard.registerNewObjective(ChatColor.stripColor(scoreboardTitle), "dummy");
        obj.setDisplayName(ChatUtils.style(scoreboardTitle));
        obj.setDisplaySlot(slot);
    }

    public void update(Player player) {
        List<String> lines = getLines(player);

        YertiScoreboardElement[] elements = new YertiScoreboardElement[lines.size()];

        for (int i = 0; i < lines.size(); i++) {
            String text = ChatUtils.style(lines.get(i));
            elements[i] = new YertiScoreboardElement(i, text, text);
        }

        for (YertiScoreboardElement element : elements) {
            if (scoreboard.getTeam(element.getTeam()) != null) {
                Team team = this.scoreboard.registerNewTeam(element.getTeam());
                team.addEntry(element.getText());
            }

            obj.getScore(element.getText()).setScore(element.getLine());


        }
    }

    public void setScoreboard(Player player) {
        player.setScoreboard(this.scoreboard);
    }

    public abstract List<String> getLines(Player player);

    public static class YertiScoreboardElement {
        private String team, text;
        private int line;

        public YertiScoreboardElement(int line, String team, String text) {
            this.team = team;
            this.text = text;
            this.line = line;
        }

        public String getTeam() {
            return team;
        }

        public String getText() {
            return text;
        }

        public int getLine() {
            return line;
        }

    }


}
