package com.yerti.core.utils;

import java.util.Map;
import java.util.stream.Collectors;

public class CastingUtils {

    public static Map<?, ?> toNewMap(Map<?, ?> map, Class<?> newKeyClass, Class<?> newValueClass) {
        return map.entrySet().stream().collect(Collectors.toMap(
                e -> newKeyClass.cast(e.getKey()),
                e -> newValueClass.cast(e.getValue())
        ));
    }

}
