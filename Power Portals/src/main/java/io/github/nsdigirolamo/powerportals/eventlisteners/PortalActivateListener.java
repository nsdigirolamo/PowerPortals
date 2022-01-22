package io.github.nsdigirolamo.powerportals.eventlisteners;

import io.github.nsdigirolamo.powerportals.models.PowerPortal;
import io.github.nsdigirolamo.powerportals.utils.CreationUtil;
import io.github.nsdigirolamo.powerportals.utils.ActivationUtil;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Listens for any portals being activated.
 */
public class PortalActivateListener implements Listener {
    /**
     * Listens for a player right-clicking a lever. Calls various methods in case that lever belongs to a PowerPortal.
     * @param event The event where a player interacted with the lever
     */
    @EventHandler
    public void onLeverClicked(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getType() == Material.LEVER) {
            Player player = event.getPlayer();
            Block lever = event.getClickedBlock();
            PowerPortal portal = ActivationUtil.findPortal(lever);

            if(portal != null) {
                event.setCancelled(true);
                ActivationUtil.activatePortal(player, portal);
            } else if (CreationUtil.attemptCreation(player, lever)) {
                event.setCancelled(true);
            }
        }
    }
}
