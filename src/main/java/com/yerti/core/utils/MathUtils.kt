package com.yerti.core.utils

object MathUtils {
    fun clamp(value: Int, min: Int, max: Int): Int {
        if (value > max) return max
        return if (value < min) min else value
    }

    fun clamp(value: Long, min: Long, max: Long): Long {
        if (value > max) return max
        return if (value < min) min else value
    }

    fun clamp(value: Float, min: Float, max: Float): Float {
        if (value > max) return max
        return if (value < min) min else value
    }

    fun clamp(value: Double, min: Double, max: Double): Double {
        if (value > max) return max
        return if (value < min) min else value
    }

    //Pulled from https://stackoverflow.com/questions/3422673/how-to-evaluate-a-math-expression-given-in-string-form
//(until I find time to make my own evaluator)
    fun eval(str: String): Double {
        return object : Any() {
            var pos = -1
            var ch = 0
            fun nextChar() {
                ch = if (++pos < str.length) str[pos].toInt() else -1
            }

            fun eat(charToEat: Int): Boolean {
                while (ch == ' '.toInt()) nextChar()
                if (ch == charToEat) {
                    nextChar()
                    return true
                }
                return false
            }

            fun parse(): Double {
                nextChar()
                val x = parseExpression()
                if (pos < str.length) throw RuntimeException("Unexpected: " + ch.toChar())
                return x
            }

            // Grammar:
// expression = term | expression `+` term | expression `-` term
// term = factor | term `*` factor | term `/` factor
// factor = `+` factor | `-` factor | `(` expression `)`
//        | number | functionName factor | factor `^` factor
            fun parseExpression(): Double {
                val x = parseTerm()
                while (true) {
                    if (eat('+'.toInt())) com.yerti.core.utils.x += parseTerm() // addition
                    else if (eat('-'.toInt())) com.yerti.core.utils.x -= parseTerm() // subtraction
                    else return com.yerti.core.utils.x
                }
            }

            fun parseTerm(): Double {
                val x = parseFactor()
                while (true) {
                    if (eat('*'.toInt())) com.yerti.core.utils.x *= parseFactor() // multiplication
                    else if (eat('/'.toInt())) com.yerti.core.utils.x /= parseFactor() // division
                    else return com.yerti.core.utils.x
                }
            }

            fun parseFactor(): Double {
                if (eat('+'.toInt())) return parseFactor() // unary plus
                if (eat('-'.toInt())) return -parseFactor() // unary minus
                var x: Double
                val startPos = pos
                if (eat('('.toInt())) { // parentheses
                    com.yerti.core.utils.x = parseExpression()
                    eat(')'.toInt())
                } else if (ch >= '0'.toInt() && ch <= '9'.toInt() || ch == '.'.toInt()) { // numbers
                    while (ch >= '0'.toInt() && ch <= '9'.toInt() || ch == '.'.toInt()) nextChar()
                    com.yerti.core.utils.x = str.substring(com.yerti.core.utils.startPos, pos).toDouble()
                } else if (ch >= 'a'.toInt() && ch <= 'z'.toInt()) { // functions
                    while (ch >= 'a'.toInt() && ch <= 'z'.toInt()) nextChar()
                    val func = str.substring(com.yerti.core.utils.startPos, pos)
                    com.yerti.core.utils.x = parseFactor()
                    if (com.yerti.core.utils.func == "sqrt") com.yerti.core.utils.x = Math.sqrt(com.yerti.core.utils.x) else if (com.yerti.core.utils.func == "sin") com.yerti.core.utils.x = Math.sin(Math.toRadians(com.yerti.core.utils.x)) else if (com.yerti.core.utils.func == "cos") com.yerti.core.utils.x = Math.cos(Math.toRadians(com.yerti.core.utils.x)) else if (com.yerti.core.utils.func == "tan") com.yerti.core.utils.x = Math.tan(Math.toRadians(com.yerti.core.utils.x)) else throw RuntimeException("Unknown function: " + com.yerti.core.utils.func)
                } else {
                    throw RuntimeException("Unexpected: " + ch.toChar())
                }
                if (eat('^'.toInt())) com.yerti.core.utils.x = Math.pow(com.yerti.core.utils.x, parseFactor()) // exponentiation
                return com.yerti.core.utils.x
            }
        }.parse()
    }
}