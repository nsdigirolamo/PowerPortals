package io.github.nsdigirolamo.powerportals.listeners;

import io.github.nsdigirolamo.powerportals.structures.PowerPortal;
import io.github.nsdigirolamo.powerportals.utils.PortalStorageUtil;
import io.github.nsdigirolamo.powerportals.utils.PortalTeleportUtil;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class TriggeredPortalListener implements Listener {
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        for (PowerPortal portal: PortalStorageUtil.getPortals()) {
            if (portal.isEntrance()) {
                for (Block block: portal.getTriggerBlocks()) {
                    if (block.getLocation().add(0.5, 0.5, 0.5).distance(player.getLocation()) <= 0.5) {
                        PortalTeleportUtil.useEntrance(player, portal);
                        PortalTeleportUtil.useEntrance(player, portal);
                    }
                }
            }
        }
    }
}
