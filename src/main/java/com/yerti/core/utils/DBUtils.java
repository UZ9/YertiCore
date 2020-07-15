package com.yerti.core.utils;

public class DBUtils {

    public static void closeQuiet(AutoCloseable closable) {
        if (closable != null) {
            try {
                closable.close();
            } catch (Exception ignored) {
                /* ignored, as this is quiet */
            }
        }
    }




}
