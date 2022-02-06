package io.github.nsdigirolamo.powerportals.commands;

import io.github.nsdigirolamo.powerportals.structures.PowerPortal;
import io.github.nsdigirolamo.powerportals.utils.PortalStorageUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * Portals is a command that allows players to see a list of PowerPortals they own.
 */
public class Portals implements CommandExecutor {

    // TODO: This method can be simplified. The for loops on lines 28 and 56 are exactly the same.

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 0) {

                ArrayList<PowerPortal> ownedPortals = new ArrayList<>();

                for (PowerPortal portal: PortalStorageUtil.getPortals()) {
                    if (portal.getOwner().getUniqueId().equals(player.getUniqueId())) {
                        ownedPortals.add(portal);
                    }
                }

                int pageCount = (int) Math.ceil(ownedPortals.size() / 10.0);
                String message = "[PowerPortals] Portal List page 1 / " + pageCount + "\n";

                for (int i = 0; i < 10; i++) {
                    if (i > ownedPortals.size() - 1) {
                        break;
                    }
                    PowerPortal portal = ownedPortals.get(i);
                    message = message + (i + 1) + ". " + portal.getName() + " ( " + portal.getX() + ", " + portal.getY() + ", " +
                            portal.getZ() + ")\n";
                }

                player.sendMessage(ChatColor.GREEN + message);

            } else if (args.length == 1) {

                try {

                    int page = Integer.parseInt(args[0]);

                    ArrayList<PowerPortal> ownedPortals = new ArrayList<>();

                    for (PowerPortal portal: PortalStorageUtil.getPortals()) {
                        if (portal.getOwner().getUniqueId().equals(player.getUniqueId())) {
                            ownedPortals.add(portal);
                        }
                    }

                    int pageCount = (int) Math.ceil(ownedPortals.size() / 10.0);
                    String message = "[PowerPortals] Portal List page " + page + " / " + pageCount + "\n";

                    if (page > pageCount || page < 1) {
                        player.sendMessage(ChatColor.RED + "[PowerPortals] Invalid arguments. That page does not exist");
                        return true;
                    }

                    page = (page * 10) - 10;

                    for (int i = 0; i < 10; i++) {
                        if (i + page > ownedPortals.size() - 1) {
                            break;
                        } else {
                            PowerPortal portal = ownedPortals.get(i + page);
                            message = message + (i + 1 + page) + ". " + portal.getName() + " ( " + portal.getX() + ", " +
                                    portal.getY() + ", " + portal.getZ() + ")\n";
                        }
                    }

                    player.sendMessage(ChatColor.GREEN + message);

                } catch (NumberFormatException e) {
                    player.sendMessage(ChatColor.RED + "[PowerPortals] Invalid arguments. Use /portals or /portals <page>");
                }

            } else {
                player.sendMessage(ChatColor.RED + "[PowerPortals] Invalid arguments. Use /portals or /portals <page>");
            }
        }
        return true;
    }
}
