package io.github.nsdigirolamo.powerportals.listeners;

import io.github.nsdigirolamo.powerportals.structures.PowerPortal;
import io.github.nsdigirolamo.powerportals.utils.PortalStorageUtil;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;

/**
 * WaterPortalListener listens for water movement and cancels it if the water belongs to a portal.
 */
public class WaterPortalListener implements Listener {

    /**
     * Stops water movement if the water belongs to a portal.
     * @param event The BlockFromToEvent where water flows.
     */
    @EventHandler
    public void onFromTo(BlockFromToEvent event) {
        Block water = event.getBlock();
        if (water.getType() == Material.WATER) {
            for (PowerPortal portal: PortalStorageUtil.getPortals()) {

                if (portal.isEntrance()) {
                    for (Block trigger: portal.getTriggerBlocks()) {
                        if (trigger.equals(water)) {
                            event.setCancelled(true);
                            return;
                        }
                    }
                }

                if (portal.hasExit()) {
                    for (Block trigger: portal.getExit().getTriggerBlocks()) {
                        if (trigger.equals(water)) {
                            event.setCancelled(true);
                            return;
                        }
                    }
                }

            }
        }
    }
}
