package com.yerti.core.command

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@Retention(RetentionPolicy.RUNTIME)
/**
 * Java Annotation for commands
 * Custom Command Annotation Parameters:
 * String name, String permission, String description, String usage, String[] aliases
 * SubCommand can be used the same way
 * @param sender CommandSender
 * @param command Command
 * @param args String[] args
 */
annotation class CustomCommand(
        /**
         * REQUIRED
         * Name of a command
         * Ex. /fly would have the name of 'fly'
         * @return
         */
        val name: String,
        /**
         * OPTIONAL
         * Permission required to issue the command
         * Leave blank for no permission
         * @return
         */
        val permission: String,
        /**
         * OPTIONAL
         * Aliases for command
         * Leave blank for no aliases
         * @return
         */
        val aliases: Array<String>,
        /**
         * OPTIONAL
         * Description for command
         * Leave blank for no description
         * @return
         */
        val description: String,
        /**
         * OPTIONAL, HIGHLY RECOMMENDED
         * Usage for command
         * Leave blank for no usage
         * @return
         */
        val usage: String)