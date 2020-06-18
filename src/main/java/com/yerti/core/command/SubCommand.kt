package com.yerti.core.command

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@Retention(RetentionPolicy.RUNTIME)
/**
 * Java annotation used for adding subcommands to an already existing command
 */
annotation class SubCommand(
        /**
         * REQUIRED
         * Name of the parent command
         * ex. for /fly 'fly' would be the parent for the subcommand
         * @return
         */
        val parent: String,
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
         * OPTIONAL, HIGHLY RECOMMENDED
         * Usage for command
         * Leave blank for no usage
         * @return
         */
        val usage: String,
        /**
         * OPTIONAL
         * Description for command
         * Leave blank for no description
         * @return
         */
        val description: String)