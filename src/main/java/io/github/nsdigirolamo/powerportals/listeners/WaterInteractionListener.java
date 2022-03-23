package io.github.nsdigirolamo.powerportals.listeners;

import io.github.nsdigirolamo.powerportals.structures.PowerPortal;
import io.github.nsdigirolamo.powerportals.utilities.StorageUtility;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;

public class WaterInteractionListener implements Listener {

    /**
     * Cancels water physics for portal trigger blocks.
     * @param event the event passed when a block tries to change state (such as when water tries to flow)
     */
    @EventHandler
    public void onFromTo(BlockFromToEvent event) {
        if (event.getBlock().getType().equals(Material.WATER)) {

            Block water = event.getBlock();
            PowerPortal[] portals = StorageUtility.findTriggers(water);

            if (portals.length != 0) {
                event.setCancelled(true);
            }

        }
    }
}
