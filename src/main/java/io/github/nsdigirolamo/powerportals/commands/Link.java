package io.github.nsdigirolamo.powerportals.commands;

import io.github.nsdigirolamo.powerportals.PowerPortals;
import io.github.nsdigirolamo.powerportals.structures.PowerPortal;
import io.github.nsdigirolamo.powerportals.utilities.StorageUtility;
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
import java.util.UUID;

public class Link implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {

            Player player = (Player) sender;

            if (player.hasPermission("powerportals.commands.Link")) {

                PowerPortal entrance = null;
                List<MetadataValue> metadata = player.getMetadata("activatedPortal");

                for (MetadataValue value : metadata) {
                    if (PowerPortals.getPlugin().equals(value.getOwningPlugin())) {
                        entrance = StorageUtility.findPortal((UUID) value.value());
                    }
                }

                if (entrance != null) {
                    // TODO: having no arguments prints an incorrect error message
                    if (args.length == 1) {

                        PowerPortal exit = StorageUtility.findPortal(args[0]);

                        if (exit != null) {

                            entrance.setExit(exit);

                            player.removeMetadata("activatedPortal", PowerPortals.getPlugin());

                            for (Block trigger : entrance.getTriggers()) {
                                trigger.setType(Material.WATER, false);
                            }

                            for (Block trigger : exit.getTriggers()) {
                                trigger.setType(Material.WATER, false);
                            }

                            player.playSound(player.getLocation(), Sound.BLOCK_END_PORTAL_FRAME_FILL, 1, 1);

                        } else {
                            player.sendMessage(ChatColor.RED + "[ P² ] " + ChatColor.GRAY + "Command failed. There is no portal named \"" + args[0] + "\".");
                        }
                    } else if (args.length < 1) {
                        player.sendMessage(ChatColor.RED + "[ P² ] " + ChatColor.GRAY + "Command failed. Too few arguments.");
                    } else {
                        player.sendMessage(ChatColor.RED + "[ P² ] " + ChatColor.GRAY + "Command failed. Too many arguments.");
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "[ P² ] " + ChatColor.GRAY + "Command failed. You need to activate an entrance.");
                }
            } else {
                player.sendMessage(ChatColor.RED + "[ P² ] " + ChatColor.GRAY + "Command failed. You do not have permission to use this command.");
            }
        }
            return true;
    }
}
