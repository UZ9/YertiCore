package com.yerti.core.utils;

public class StringUtils {

    /**
     * Parses a string of text for a number. Main function of using this over Integer.parseInt is
     * this can parse a string of text with character
     * i.e. 'cooler 32' would return 32
     *
     * @param text string parameter
     * @return any number found in the string
     */
    public static int parse(String text) {
        return Integer.parseInt(text.replaceAll("[\\D]", ""));
    }

}
