package com.yerti.core.command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
/**
 * Java Annotation for commands
 * Custom Command Annotation Parameters:
 * String name, String permission, String description, String usage, String[] aliases
 * SubCommand can be used the same way
 * @param sender CommandSender
 * @param command Custom Command issued
 * @param args Arguments from command
 */
public @interface CustomCommand {

    /**
     * REQUIRED
     * Name of a command
     * Ex. /fly would have the name of 'fly'
     * @return
     */
    String name();

    /**
     * OPTIONAL
     * Permission required to issue the command
     * Leave blank for no permission
     * @return
     */
    String permission();

    /**
     * OPTIONAL
     * Aliases for command
     * Leave blank for no aliases
     * @return
     */
    String[] aliases();

    /**
     * OPTIONAL
     * Description for command
     * Leave blank for no description
     * @return
     */
    String description();

    /**
     * OPTIONAL, HIGHLY RECOMMENDED
     * Usage for command
     * Leave blank for no usage
     * @return
     */
    String usage();
}

