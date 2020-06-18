package com.yerti.core.utils

object StringUtils {
    fun parse(text: String): Int {
        return text.replace("[\\D]".toRegex(), "").toInt()
    }
}