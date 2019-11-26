package com.yerti.core.command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
/**
 * Java annotation used for adding subcommands to an already existing command
 */
public @interface SubCommand {

    /**
     * REQUIRED
     * Name of the parent command
     * ex. for /fly 'fly' would be the parent for the subcommand
     * @return
     */
    String parent();

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
     * OPTIONAL, HIGHLY RECOMMENDED
     * Usage for command
     * Leave blank for no usage
     * @return
     */
    String usage();

    /**
     * OPTIONAL
     * Description for command
     * Leave blank for no description
     * @return
     */
    String description();

}
