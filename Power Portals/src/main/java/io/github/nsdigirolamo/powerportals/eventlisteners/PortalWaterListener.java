package io.github.nsdigirolamo.powerportals.eventlisteners;

import io.github.nsdigirolamo.powerportals.models.Linkage;
import io.github.nsdigirolamo.powerportals.utils.LinkUtil;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;

import java.util.ArrayList;

/**
 * Listens for water flowing and cancels that flow if the water belongs to a PowerPortal.
 */
public class PortalWaterListener implements Listener {

    public static ArrayList<Linkage> linkages = LinkUtil.getLinkages();

    /**
     * Listens for any water beginning to flow. Cancels the water flow if the water belongs to the trigger blocks of a
     * PowerPortal.
     * @param event The event where water began flowing
     */
    @EventHandler
    public void onFromTo(BlockFromToEvent event) {
        Block eventBlock = event.getBlock();
        if (eventBlock.getType() == Material.WATER) {
            for (Linkage linkage: linkages) {
                for (Block trigger: linkage.getEntranceTriggers()) {
                    if (trigger.equals(eventBlock)) {
                        event.setCancelled(true);
                        return;
                    }
                }
                for (Block trigger: linkage.getExitTriggers()) {
                    if (trigger.equals(eventBlock)) {
                        event.setCancelled(true);
                        return;
                    }
                }
            }
        }
    }
}
