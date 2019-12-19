package com.yerti.core.utils;

import org.bukkit.ChatColor;

public class ProgressBar {

    private String symbol;
    private int value;
    private int max;
    private ChatColor empty;
    private ChatColor filled;
    private int barAmount;

    /**
     * Creates a {@link ProgressBar} object used to make easy bars on lore or titles
     * @param symbol The symbol the progress bar is made out of, i.e. '||||||||||||' would have | as the symbol
     * @param value The starting value for the progress bar to be at
     * @param max Maximum value of the progress bar
     * @param barAmount Amount of bars (symbols) the progress bar contains
     * @param empty The {@link ChatColor} a bar will have when it is empty
     * @param filled The {@link ChatColor} a bar will have when it is full
     */
    public ProgressBar(String symbol, int value, int max, int barAmount, ChatColor empty, ChatColor filled) {
        this.symbol = symbol;
        this.max = max;
        this.value = value;
        this.empty = empty;
        this.filled = filled;
        this.barAmount = barAmount;
    }

    /**
     * Creates a {@link ProgressBar} object used to make easy bars on lore or titles
     * @param symbol The symbol the progress bar is made out of, i.e. '||||||||||||' would have | as the symbol
     * @param max Maximum value of the progress bar
     * @param barAmount Amount of bars (symbols) the progress bar contains
     * @param empty The {@link ChatColor} a bar will have when it is empty
     * @param filled The {@link ChatColor} a bar will have when it is full
     */
    public ProgressBar(String symbol, int max, int barAmount, ChatColor empty, ChatColor filled) {
        this.symbol = symbol;
        this.max = max;
        this.empty = empty;
        this.filled = filled;
        this.barAmount = barAmount;
    }

    /**
     * Retrieves the formatted bar using all of the parameters
     * @return the formatted bar
     */
    public String getBar() {
        double neededPercent = (double) value / max;
        int bars = (int) (barAmount * (neededPercent));

        StringBuilder builder = new StringBuilder();

        for (int i = 1 ; i <= barAmount; i++) {
            if (i <= bars) {
                builder.append(filled).append(symbol);
            } else {
                builder.append(empty).append(symbol);
            }
        }

        return builder.toString();



    }

    /**
     * Updates the value of the progress bar
     * @param value
     */
    public void updateValue(int value) {
        this.value = value;
    }


}
