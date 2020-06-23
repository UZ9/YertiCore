package com.yerti.core.utils;

import org.bukkit.ChatColor;

public class ProgressBar {

    private String symbol;
    private int percent;
    private int max;
    private ChatColor empty;
    private ChatColor filled;
    private int barAmount;

    public ProgressBar(String symbol, int percent, int max, int barAmount, ChatColor empty, ChatColor filled) {
        this.symbol = symbol;
        this.max = max;
        this.percent = percent;
        this.empty = empty;
        this.filled = filled;
        this.barAmount = barAmount;
    }

    public String getBar() {
        double neededPercent = (double) percent / max;
        int bars = (int) (barAmount * (neededPercent / 100.));

        StringBuilder builder = new StringBuilder();

        for (int i = 1; i <= barAmount; i++) {
            if (i <= bars) {
                builder.append(filled).append(symbol);
            } else {
                builder.append(empty).append(symbol);
            }
        }

        return builder.toString();


    }

    public void updatePercent(int percent) {
        this.percent = percent;
    }


}
