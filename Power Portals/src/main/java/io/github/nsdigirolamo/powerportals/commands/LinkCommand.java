package io.github.nsdigirolamo.powerportals.commands;

import io.github.nsdigirolamo.powerportals.PowerPortals;
import io.github.nsdigirolamo.powerportals.models.PowerPortal;
import io.github.nsdigirolamo.powerportals.utils.LinkUtil;
import io.github.nsdigirolamo.powerportals.utils.StorageUtil;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Switch;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class LinkCommand implements CommandExecutor {

    private static Plugin plugin = PowerPortals.getPlugin();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 1) {
                List<MetadataValue> values = player.getMetadata("activatedPortal");
                if (!values.isEmpty() && values.get(0).value() instanceof PowerPortal) {

                    PowerPortal entrance = (PowerPortal) values.get(0).value();
                    PowerPortal exit = StorageUtil.searchPowerPortal(args[0]);

                    if (exit != null) {

                        Block block = entrance.getLever();
                        if ( block.getType() == Material.LEVER) {
                            Switch lever = (Switch) block.getBlockData();
                            lever.setPowered(false);
                            block.setBlockData(lever);
                        }

                        LinkUtil.createLinkage(player, entrance, exit);

                    } else {
                        player.sendMessage(ChatColor.RED + "[PowerPortals] No portal found with name " + args[0]);
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "[PowerPortals] No activated portal found! Please activate a portal.");
                }
            } else {
                player.sendMessage(ChatColor.RED + "[PowerPortals] Too many arguments! Use /link <name>");
            }
        }
        return true;
    }
}
