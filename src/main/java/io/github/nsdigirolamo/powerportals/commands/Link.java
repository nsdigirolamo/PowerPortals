package io.github.nsdigirolamo.powerportals.commands;

import io.github.nsdigirolamo.powerportals.PowerPortals;
import io.github.nsdigirolamo.powerportals.structures.PowerPortal;
import io.github.nsdigirolamo.powerportals.utilities.StorageUtility;
import io.github.nsdigirolamo.powerportals.utilities.interfaces.Messages;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;

/**
 * Controls execution and behavior for the /link command
 */
public class Link implements CommandExecutor {

    /**
     * Executes the /link command
     * @param sender Source of the command
     * @param command Command which was executed
     * @param label Alias of the command which was used
     * @param args Passed command arguments
     * @return true regardless of parameters
     */
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player)) {

            return false;

        } else {

            Player player = (Player) sender;

            if (!player.hasPermission("powerportals.commands.Link")) {

                player.sendMessage(Messages.NO_PERMISSION);

            } else {

                PowerPortal entrance = null;
                List<MetadataValue> metadata = player.getMetadata("activatedPortal");
                for (MetadataValue data : metadata) {
                    if (PowerPortals.getPlugin().equals(data.getOwningPlugin())) {
                        entrance = StorageUtility.findPortal((UUID)data.value());
                    }
                }

                if (entrance == null) {

                    player.sendMessage(Messages.RED_PREFIX + "Command failed. You need to activate an entrance.");

                } else if (args.length < 1) {

                    player.sendMessage(Messages.TOO_FEW_ARGS);

                } else if (2 < args.length) {

                    player.sendMessage(Messages.TOO_MANY_ARGS);

                } else {

                    PowerPortal exit = StorageUtility.findPortal(args[0]);

                    if (exit == null) {

                        player.sendMessage(Messages.PORTAL_DNE);

                    } else if (args.length == 1 &&
                            exit.getPassword() != null &&
                            !player.hasPermission("powerportals.portals.bypassPassword")) {

                        player.sendMessage(Messages.PORTAL_PASSPROT);

                    } else if (args.length == 2 &&
                            exit.getPassword() != null &&
                            !exit.getPassword().equals(args[1]) &&
                            !player.hasPermission("powerportals.portals.bypassPassword")) {

                        player.sendMessage(Messages.PORTAL_WRONGPASS);

                    } else {

                        entrance.setExit(exit);

                        player.removeMetadata("activatedPortal", PowerPortals.getPlugin());

                        for (Block trigger : entrance.getTriggers()) {
                            trigger.setType(Material.WATER, false);
                        }

                        for (Block trigger : exit.getTriggers()) {
                            trigger.setType(Material.WATER, false);
                        }

                        player.playSound(player.getLocation(), Sound.BLOCK_END_PORTAL_FRAME_FILL, 1, 1);
                    }
                }
            }
        }
        return true;
    }
}
