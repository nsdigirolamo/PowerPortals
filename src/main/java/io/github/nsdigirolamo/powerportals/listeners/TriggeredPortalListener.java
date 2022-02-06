package io.github.nsdigirolamo.powerportals.listeners;

import io.github.nsdigirolamo.powerportals.structures.PowerPortal;
import io.github.nsdigirolamo.powerportals.utils.PortalStorageUtil;
import io.github.nsdigirolamo.powerportals.utils.PortalTeleportUtil;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * TriggeredPortalListener listens for players entering the trigger zones of any portals marked as entrances.
 */
public class TriggeredPortalListener implements Listener {

    /**
     * Listens for player movements and checks to see if a player has entered the trigger blocks of an entrance portal.
     * If a player has entered the trigger blocks of an entrance, they will be teleported.
     * @param event A PlayerMoveEvent where the player has entered the trigger blocks of an entrance portal.
     */
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        for (PowerPortal portal: PortalStorageUtil.getPortals()) {
            if (portal.isEntrance()) {
                for (Block block: portal.getTriggerBlocks()) {
                    if (block.getLocation().add(0.5, 0, 0.5).distance(player.getLocation()) <= 0.5) {
                        PortalTeleportUtil.useEntrance(player, portal);
                    }
                }
            }
        }
    }
}
