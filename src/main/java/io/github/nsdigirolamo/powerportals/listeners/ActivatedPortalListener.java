package io.github.nsdigirolamo.powerportals.listeners;

import io.github.nsdigirolamo.powerportals.structures.PowerPortal;
import io.github.nsdigirolamo.powerportals.utils.PortalActivationUtil;
import io.github.nsdigirolamo.powerportals.utils.PortalCreationUtil;
import io.github.nsdigirolamo.powerportals.utils.PortalStorageUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class ActivatedPortalListener implements Listener {
    @EventHandler
    public void onLeverClick(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getType().equals(Material.LEVER)) {

            Block lever = event.getClickedBlock();
            Player player = event.getPlayer();
            PowerPortal portal = PortalStorageUtil.findPortal(lever);

            if (portal != null) {
                PortalActivationUtil.activatePortal(player, portal);
            } else {
                PowerPortal newPortal = PortalCreationUtil.createPortal(player, lever);
                if (newPortal != null) {
                    event.setCancelled(true);
                    player.sendMessage(ChatColor.GREEN + "[PowerPortals] Created new portal named \"" +
                            newPortal.getName() + "\" at (" + newPortal.getX() + ", " + newPortal.getY() + ", " +
                            newPortal.getZ() + ")");
                    player.playSound(player.getLocation(), Sound.BLOCK_END_PORTAL_FRAME_FILL, 1, 1);
                }
            }
        }
    }
}
