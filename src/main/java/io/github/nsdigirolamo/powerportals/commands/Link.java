package io.github.nsdigirolamo.powerportals.commands;

import io.github.nsdigirolamo.powerportals.structures.PowerPortal;
import io.github.nsdigirolamo.powerportals.utils.PortalStorageUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;

import java.util.List;

/**
 * Link is a command that allows players to link two PowerPortals, so they can be used for teleportation.
 */
public class Link implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {

            Player player = (Player) sender;

            if (args.length == 1) {

                List<MetadataValue> values = player.getMetadata("activatedPortal");

                if (!values.isEmpty() && values.get(0).value() instanceof PowerPortal) {

                    PowerPortal entrance = (PowerPortal) values.get(0).value();
                    PowerPortal exit = PortalStorageUtil.findPortal(args[0]);

                    if (exit != null) {

                        entrance.setEntrance(true);
                        entrance.setExit(exit);

                        for (Block block: entrance.getTriggerBlocks()) {
                            block.setType(Material.WATER, false);
                        }

                        for (Block block: exit.getTriggerBlocks()) {
                            block.setType(Material.WATER, false);
                        }

                        player.playSound(player.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 1, 1);

                    } else {
                        player.sendMessage(ChatColor.RED + "[PowerPortals] Portal " + args[0] + " does not exist.");
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "[PowerPortals] No activated portal found.");
                }
            } else {
                player.sendMessage(ChatColor.RED + "[PowerPortals] Invalid arguments. Use /link <name>");
            }
        }
        return true;
    }
}
