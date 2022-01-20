package io.github.nsdigirolamo.powerportals.eventlisteners;

import io.github.nsdigirolamo.powerportals.utils.CreationUtil;
import io.github.nsdigirolamo.powerportals.utils.ActivationUtil;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.UUID;

import static org.bukkit.Bukkit.getLogger;

public class PortalActivateListener implements Listener {
    @EventHandler
    public void onLeverClicked(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getType() == Material.LEVER) {
            Player player = event.getPlayer();
            Block lever = event.getClickedBlock();
            UUID id = ActivationUtil.findPortal(lever);

            if(id != null) {
                event.setCancelled(true);
                ActivationUtil.activatePortal(player, id);
            } else if (CreationUtil.attemptCreation(player, lever)) {
                event.setCancelled(true);
            }
        }
    }
}
