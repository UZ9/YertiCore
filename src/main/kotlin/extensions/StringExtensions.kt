package extensions

import com.yerti.core.utils.ChatUtils

fun String.color(string: String): String {
    return ChatUtils.style(string)
}

fun String.colorPrefix(string: String): String {
    return ChatUtils.stylePrefix(string)
}