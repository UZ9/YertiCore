package com.yerti.core.utils;


import com.yerti.core.YertiPlugin;
import org.bukkit.Bukkit;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class NMSUtils {

    private static final Map<Class<?>, Class<?>> CORRESPONDING_TYPES = new HashMap<Class<?>, Class<?>>();

    /**
     * Attempts to find the primitive type of a {@link Class<?>}
     * If no primitive type is found, the class will be returned as normal
     * @param clazz
     * @return
     */
    private static Class<?> getPrimitiveType(Class<?> clazz) {
        return CORRESPONDING_TYPES
                .getOrDefault(clazz, clazz);
    }

    /**
     * Gets an NMS Class by its name
     * @param name name of the class
     * @return a {@link Class<?>} nms object
     */
    public static Class<?> getNMSClass(String name) {
        try {
            return Class.forName("net.minecraft.server." + YertiPlugin.getHookedPlugin().getServer().getVersion() + "." + name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Converts a list of {@link Class<?>} to their primitive types.
     * If no primitive type is found, the class will be returned as normal
     * @param classes
     * @return
     */
    private static Class<?>[] toPrimitiveTypeArray(Class<?>[] classes) {
        int a = classes != null ? classes.length : 0;
        Class<?>[] types = new Class<?>[a];
        for (int i = 0; i < a; i++)
            types[i] = getPrimitiveType(classes[i]);
        return types;
    }

    /**
     * Checks if two different lists of {@link Class<?>} are equal
     * @param a
     * @param o
     * @return
     */
    public static boolean equalsTypeArray(Class<?>[] a, Class<?>[] o) {
        if (a.length != o.length)
            return false;
        for (int i = 0; i < a.length; i++)
            if (!a[i].equals(o[i]) && !a[i].isAssignableFrom(o[i]))
                return false;
        return true;
    }

    /**
     * Retrieves the handle from a specified object
     * @param obj
     * @return
     */
    public static Object getHandle(Object obj) {
        try {
            return getMethod("getHandle", obj.getClass()).invoke(obj);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Gets a method from a {@link Class<?>} using reflection
     * @param name
     * @param clazz
     * @param paramTypes
     * @return
     */
    public static Method getMethod(String name, Class<?> clazz,
                             Class<?>... paramTypes) {
        Class<?>[] t = toPrimitiveTypeArray(paramTypes);
        for (Method m : clazz.getMethods()) {
            Class<?>[] types = toPrimitiveTypeArray(m.getParameterTypes());
            if (m.getName().equals(name) && equalsTypeArray(types, t))
                return m;
        }
        return null;
    }

    /**
     * Gets the version for using reflection
     * @return
     */
    private String getVersion() {
        String name = Bukkit.getServer().getClass().getPackage().getName();
        String version = name.substring(name.lastIndexOf('.') + 1) + ".";
        return version;
    }

    /**
     * Retrieves a field from a {@link Class<?>} using reflection
     * @param clazz
     * @param name
     * @return
     */
    public static Field getField(Class<?> clazz, String name) {
        try {
            Field field = clazz.getDeclaredField(name);
            field.setAccessible(true);
            return field;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Retrieves a method from a {@link Class<?>} class using reflection
     * @param clazz
     * @param name
     * @param args
     * @return
     */
    public static Method getMethod(Class<?> clazz, String name, Class<?>... args) {
        for (Method m : clazz.getMethods())
            if (m.getName().equals(name)
                    && (args.length == 0 || ClassListEqual(args,
                    m.getParameterTypes()))) {
                m.setAccessible(true);
                return m;
            }
        return null;
    }

    /**
     * Checks if two different lists of classes are equal
     * @param l1
     * @param l2
     * @return
     */
    public static boolean ClassListEqual(Class<?>[] l1, Class<?>[] l2) {
        boolean equal = true;
        if (l1.length != l2.length)
            return false;
        for (int i = 0; i < l1.length; i++)
            if (l1[i] != l2[i]) {
                equal = false;
                break;
            }
        return equal;
    }



}
