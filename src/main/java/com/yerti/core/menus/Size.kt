package com.yerti.core.menus

/**
 * Enum for the different sizes in a menu
 * @see Page
 */
enum class Size(var row: Row, var slotAmount: Int) {
    //9, 18, 27, 36, 45, 54
    ONE_ROW(Row.FIRST, 9),
    TWO_ROW(Row.SECOND, 18), THREE_ROW(Row.THIRD, 27), FOUR_ROW(Row.FOURTH, 36), FIVE_ROW(Row.FIFTH, 45), SIX_ROW(Row.SIXTH, 54);

}