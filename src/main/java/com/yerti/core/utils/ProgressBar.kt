package com.yerti.core.utils

import org.bukkit.ChatColor

class ProgressBar(private val symbol: String, private var percent: Int, private val max: Int, private val barAmount: Int, private val empty: ChatColor, private val filled: ChatColor) {
    val bar: String
        get() {
            val neededPercent = percent.toDouble() / max
            val bars = (barAmount * (neededPercent / 100.0)).toInt()
            val builder = StringBuilder()
            for (i in 1..barAmount) {
                if (i <= bars) {
                    builder.append(filled).append(symbol)
                } else {
                    builder.append(empty).append(symbol)
                }
            }
            return builder.toString()
        }

    fun updatePercent(percent: Int) {
        this.percent = percent
    }

}