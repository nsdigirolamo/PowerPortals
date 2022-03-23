package io.github.nsdigirolamo.powerportals.commands.tabcompleters;

import io.github.nsdigirolamo.powerportals.structures.PowerPortal;
import io.github.nsdigirolamo.powerportals.utilities.StorageUtility;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class LinkTabCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1 && sender instanceof Player) {

            Player player = (Player) sender;
            ArrayList<String> portalNames = new ArrayList<String>();
            PowerPortal[] ownedPortals = StorageUtility.findPortals(player);

            for (PowerPortal portal : ownedPortals) {
                portalNames.add(portal.getName());
            }

            return portalNames;
        }
        return null;
    }
}
