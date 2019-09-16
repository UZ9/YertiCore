package com.yerti.core.prototype.command;

public @interface SubCommand {

    String parent();

    String name();

    String permission();

    String usage();

    String description();

}
