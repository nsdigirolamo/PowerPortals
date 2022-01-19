package io.github.nsdigirolamo.powerportals.eventlisteners;

import io.github.nsdigirolamo.powerportals.utils.PortalCreationUtil;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PortalCreationListener implements Listener {
    @EventHandler
    public void onLeverClicked(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getType() == Material.LEVER) {
            Player player = event.getPlayer();
            Block lever = event.getClickedBlock();
            if (PortalCreationUtil.attemptCreation(player, lever)) {
                event.setCancelled(true);
            }
        }
    }
}
