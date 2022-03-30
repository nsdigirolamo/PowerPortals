package io.github.nsdigirolamo.powerportals.utilities.interfaces;

import org.bukkit.ChatColor;

public interface Messages {

    String RED_PREFIX = ChatColor.RED + "[ P² ] " + ChatColor.GRAY;

    String GREEN_PREFIX = ChatColor.GREEN + "[ P² ] " + ChatColor.GRAY;

    String NO_PERMISSION = RED_PREFIX + "Command failed. You do not have permission to use this command.";

    String TOO_MANY_ARGS = RED_PREFIX + "Command failed. Too many arguments.";

    String TOO_FEW_ARGS = RED_PREFIX + "Command failed. Too few arguments.";

    String INVALID_ARGS = RED_PREFIX + "Command failed. Invalid arguments.";

    String PORTAL_DNE = RED_PREFIX + "Command failed. That portal does not exist.";



}
