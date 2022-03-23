package io.github.nsdigirolamo.powerportals.commands;

import io.github.nsdigirolamo.powerportals.structures.PowerPortal;
import io.github.nsdigirolamo.powerportals.utilities.StorageUtility;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Portals implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {

            Player player = (Player) sender;

            if (player.hasPermission("powerportals.commands.portals")) {
                if (args.length == 0) {

                    listPortals(player, 1);

                } else if (args.length == 1) {

                    try {
                        int page = Integer.parseInt(args[0]);
                        listPortals(player, page);
                    } catch (NumberFormatException e) {
                        player.sendMessage(ChatColor.RED + "[ P² ] " + ChatColor.GRAY + "Command failed. You must provide a page number.");
                    }

                } else {
                    player.sendMessage(ChatColor.RED + "[ P² ] " + ChatColor.GRAY + "Command failed. Too many arguments.");
                }
            } else {
                player.sendMessage(ChatColor.RED + "[ P² ] " + ChatColor.GRAY + "Command failed. You do not have permission to use this command.");
            }
        }
        return true;
    }

    /**
     * Provides the player with a list of PowerPortals they own.
     * @param owner the player.
     * @param pageNum the page number the player would like to see.
     */
    private static void listPortals (Player owner, int pageNum) {

        PowerPortal[] portals = StorageUtility.findPortals(owner);
        // integer division chops off the decimal, add one to round up to the proper max page number
        int maxPages = (portals.length / 10) + 1;

        if (pageNum > maxPages) {
            pageNum = maxPages;
        }

        int startIndex = (pageNum * 10) - 10;
        int endIndex = pageNum * 10;

        if (endIndex > portals.length) {
            endIndex = portals.length;
        }

        String message = ChatColor.GREEN + " --+-- [ PowerPortals " + ChatColor.GRAY + "(" + pageNum + " / " +
                maxPages + ")" + ChatColor.GREEN + " ] --+--\n";

        // TODO: alphabetize this output
        for (int i = startIndex; i < endIndex; i++) {
            message += ChatColor.GREEN + "[ " + (i + 1) + " ] " + ChatColor.WHITE + portals[i].getName() + ChatColor.GRAY +
                    " (" + portals[i].getX() + ", " + portals[i].getY() + ", " + portals[i].getZ() + ")\n";
        }

        owner.sendMessage(message);
        owner.playSound(owner.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 2);
    }
}
