package com.yerti.core.menus;

/**
 * Enum for the different sizes in a menu
 * @see Page
 */
public enum Size {


    //9, 18, 27, 36, 45, 54
    ONE_ROW(Row.FIRST, 9), TWO_ROW(Row.SECOND, 18), THREE_ROW(Row.THIRD, 27), FOUR_ROW(Row.FOURTH, 36), FIVE_ROW(Row.FIFTH, 45), SIX_ROW(Row.SIXTH, 54);

    public Row row;
    public int slotAmount;

    Size(Row row, int slotAmount) {
        this.row = row;
        this.slotAmount = slotAmount;
    }
}
