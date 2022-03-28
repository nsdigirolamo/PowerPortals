package io.github.nsdigirolamo.powerportals.utilities.interfaces;

import org.bukkit.ChatColor;

public interface Messages {

    String NO_PERMISSION = ChatColor.RED + "[ P² ] " + ChatColor.GRAY +
            "Command failed. You do not have permission to use this command.";

    String TOO_MANY_ARGS = ChatColor.RED + "[ P² ] " + ChatColor.GRAY +
            "Command failed. Too many arguments.";

    String TOO_FEW_ARGS = ChatColor.RED + "[ P² ] " + ChatColor.GRAY +
            "Command failed. Too few arguments.";

    String INVALID_ARGS = ChatColor.RED + "[ P² ] " + ChatColor.GRAY +
            "Command failed. Invalid arguments.";

}
