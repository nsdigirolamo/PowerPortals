package io.github.nsdigirolamo.powerportals.commands;

import io.github.nsdigirolamo.powerportals.structures.PowerPortal;
import io.github.nsdigirolamo.powerportals.utilities.StorageUtility;
import io.github.nsdigirolamo.powerportals.utilities.interfaces.Messages;
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
                // if there are no arguments print the first page of the player's portals
                if (args.length == 0) {

                    listPortals(player, 1);

                // if there is one argument check if its "all" or a page number and pass to the appropriate method
                } else if (args.length == 1) {
                    if (args[0].equals("all") && player.hasPermission("powerportals.commands.portals.all")) {

                        listAllPortals(player, 1);

                    } else {

                        try {
                            int page = Integer.parseInt(args[0]);
                            listPortals(player, page);
                        } catch (NumberFormatException e) {
                            player.sendMessage(Messages.INVALID_ARGS);
                        }

                    }
                // if there are two arguments check if the first is "all" and the second is a page number
                } else if (args.length == 2 && player.hasPermission("powerportals.commands.portals.all")) {
                    if (args[0].equals("all")) {

                        try {
                            int page = Integer.parseInt(args[1]);
                            listAllPortals(player, page);
                        } catch (NumberFormatException e) {
                            player.sendMessage(Messages.INVALID_ARGS);
                        }

                    } else {
                        player.sendMessage(Messages.INVALID_ARGS);
                    }
                } else {
                    player.sendMessage(Messages.TOO_MANY_ARGS);
                }
            } else {
                player.sendMessage(Messages.NO_PERMISSION);
            }
        }
        return true;
    }

    /**
     * Provides the player with a list of PowerPortals they own.
     * @param owner the player.
     * @param pageNum the page number of the list.
     */
    private static void listPortals (Player owner, int pageNum) {

        PowerPortal[] portals = StorageUtility.findPortals(owner);
        int portalsPerPage = 8;
        // integer division chops off the decimal, add one to round up to the proper max page number
        int maxPages = (portals.length / portalsPerPage) + 1;

        if (pageNum > maxPages) {
            pageNum = maxPages;
        }

        int startIndex = (pageNum * portalsPerPage) - portalsPerPage;
        int endIndex = pageNum * portalsPerPage;

        if (endIndex > portals.length) {
            endIndex = portals.length;
        }

        StringBuilder message = new StringBuilder(ChatColor.GREEN + " --+-- [ PowerPortals " + ChatColor.GRAY + "(" +
                pageNum + " / " + maxPages + ")" + ChatColor.GREEN + " ] --+--\n");

        // TODO: alphabetize this output
        for (int i = startIndex; i < endIndex; i++) {
            message.append(ChatColor.GREEN).append("[ ").append(i + 1).append(" ] ").append(ChatColor.WHITE)
                    .append(portals[i].getName()).append(ChatColor.GRAY).append(" (").append(portals[i].getX())
                    .append(", ").append(portals[i].getY()).append(", ").append(portals[i].getZ()).append(")\n");
        }

        owner.sendMessage(message.toString());
        owner.playSound(owner.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 2);
    }

    /**
     * Provides the player with a list of all PowerPortals.
     * @param player the player.
     * @param pageNum the page number of the list.
     */
    private static void listAllPortals (Player player, int pageNum) {
        PowerPortal[] portals = StorageUtility.getPortals();
        int portalsPerPage = 8;
        // integer division chops off the decimal, add one to round up to the proper max page number
        int maxPages = (portals.length / portalsPerPage) + 1;

        if (pageNum > maxPages) {
            pageNum = maxPages;
        }

        int startIndex = (pageNum * portalsPerPage) - portalsPerPage;
        int endIndex = pageNum * portalsPerPage;

        if (endIndex > portals.length) {
            endIndex = portals.length;
        }

        StringBuilder message = new StringBuilder(ChatColor.GREEN + " --+-- [ PowerPortals " + ChatColor.GRAY + "(" +
                pageNum + " / " + maxPages + ")" + ChatColor.GREEN + " ] --+--\n");

        // TODO: alphabetize this output
        for (int i = startIndex; i < endIndex; i++) {
            message.append(ChatColor.GREEN).append("[ ").append(i + 1).append(" ] ").append(ChatColor.WHITE)
                    .append(portals[i].getName()).append(ChatColor.GRAY).append(" (").append(portals[i].getX())
                    .append(", ").append(portals[i].getY()).append(", ").append(portals[i].getZ()).append(")\n");
        }

        player.sendMessage(message.toString());
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 2);
    }
}
