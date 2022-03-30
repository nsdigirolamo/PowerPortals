package io.github.nsdigirolamo.powerportals.commands;

import io.github.nsdigirolamo.powerportals.structures.PowerPortal;
import io.github.nsdigirolamo.powerportals.utilities.StorageUtility;
import io.github.nsdigirolamo.powerportals.utilities.interfaces.Messages;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Pwarp implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {

            Player player = (Player) sender;

            if (player.hasPermission("powerportals.commands.pwarp")) {
                if (args.length == 1) {

                    PowerPortal destination = StorageUtility.findPortal(args[0]);

                    if (destination != null) {

                        player.teleport(destination.getLocation());
                        player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);

                    } else {
                        player.sendMessage(Messages.PORTAL_DNE);
                    }
                } else if (args.length < 1) {
                    player.sendMessage(Messages.TOO_FEW_ARGS);
                } else {
                    player.sendMessage(Messages.TOO_MANY_ARGS);
                }
            }
        }
        return true;
    }
}
