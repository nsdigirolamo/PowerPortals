package io.github.nsdigirolamo.powerportals.listeners;

import io.github.nsdigirolamo.powerportals.structures.PowerPortal;
import io.github.nsdigirolamo.powerportals.utilities.ActivationUtility;
import io.github.nsdigirolamo.powerportals.utilities.StorageUtility;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class TriggerListener implements Listener {

    /**
     * Teleports a player when it detects they have entered a portal with an exit.
     * @param event the event passed when a player moves.
     */
    @EventHandler
    public void onTriggerEntry(PlayerMoveEvent event) {

        Player player = event.getPlayer();
        Block block = player.getWorld().getBlockAt(player.getLocation());

        PowerPortal[] portals = StorageUtility.findTriggers(block);

        if (portals.length == 1) {
            if (portals[0].getExit() != null) {

                PowerPortal entrance = portals[0];
                PowerPortal exit = portals[0].getExit();

                player.teleport(exit.getLocation());
                player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);

                ActivationUtility.deactivate(entrance, player);

            }
        }
    }
}
