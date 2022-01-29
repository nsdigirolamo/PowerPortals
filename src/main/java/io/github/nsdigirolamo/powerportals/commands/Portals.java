package io.github.nsdigirolamo.powerportals.commands;

import io.github.nsdigirolamo.powerportals.structures.PowerPortal;
import io.github.nsdigirolamo.powerportals.utils.PortalStorageUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Portals is a command that allows players to see a list of PowerPortals they own.
 */
public class Portals implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 0) {
                int count = 0;
                String message = ChatColor.GREEN + "[PowerPortals]\n";
                for (PowerPortal portal: PortalStorageUtil.getPortals()) {
                    if (portal.getOwner().getUniqueId().equals(player.getUniqueId())) {
                        count++;
                        message = message + count + " ... " + portal.getName() + " at (" + portal.getX() + ", " +
                                portal.getY() + ", " + portal.getZ() + ")\n";
                    }
                }
                player.sendMessage(message);
            } else {
                player.sendMessage(ChatColor.RED + "[PowerPortals] Invalid arguments. Use /portals");
            }
        }
        return true;
    }
}
