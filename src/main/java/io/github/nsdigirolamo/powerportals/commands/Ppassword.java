package io.github.nsdigirolamo.powerportals.commands;

import io.github.nsdigirolamo.powerportals.structures.PowerPortal;
import io.github.nsdigirolamo.powerportals.utilities.StorageUtility;
import io.github.nsdigirolamo.powerportals.utilities.interfaces.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * Controls execution and behavior for the /ppassword command
 */
public class Ppassword implements CommandExecutor {

    /**
     * Executes the /ppassword command
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

            if (!player.hasPermission("powerportals.commands.ppassword")) {

                player.sendMessage(Messages.NO_PERMISSION);
                return false;

            } else if (args.length < 2) {

                player.sendMessage(Messages.TOO_FEW_ARGS);
                return false;

            } else if (3 < args.length) {

                player.sendMessage(Messages.TOO_MANY_ARGS);
                return false;

            } else {

                String portalName = args[0];
                PowerPortal portal = StorageUtility.findPortal(portalName);

                if (portal == null) {

                    player.sendMessage(Messages.PORTAL_DNE);
                    return false;

                } else if (!portal.getOwnerID().equals(player.getUniqueId())) {

                    player.sendMessage(Messages.RED_PREFIX + "Command failed. That portal does not belong to you.");
                    return false;

                } else {

                    String action = args[1];

                    if (action.equals("delete")) {

                        portal.deletePassword();
                        player.sendMessage(Messages.GREEN_PREFIX + "Password deleted.");

                    } else if (action.equals("set")) {

                        String password = args[2];

                        if (password.matches("^[\\w]{1,20}$")) {

                            portal.setPassword(args[2]);
                            player.sendMessage(Messages.GREEN_PREFIX + "Password set to \"" + password + "\"");

                        } else {

                            player.sendMessage(Messages.RED_PREFIX + "Command failed. Password must be" +
                                    "alphanumeric and 1 to 20 characters long.");

                        }

                    } else {

                        player.sendMessage(Messages.INVALID_ARGS);
                        return false;

                    }
                }
            }
        }
        return true;
    }
}
