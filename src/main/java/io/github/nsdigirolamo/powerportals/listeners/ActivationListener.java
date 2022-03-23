package io.github.nsdigirolamo.powerportals.listeners;

import io.github.nsdigirolamo.powerportals.structures.PowerPortal;
import io.github.nsdigirolamo.powerportals.utilities.ActivationUtility;
import io.github.nsdigirolamo.powerportals.utilities.SmallPortalCreationUtility;
import io.github.nsdigirolamo.powerportals.utilities.StorageUtility;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Switch;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class ActivationListener implements Listener {

    /**
     * Activates or deactivates a portal when its lever is clicked
     * @param event the event passed when a lever is clicked
     */
    @EventHandler
    public void onLeverClick(PlayerInteractEvent event) {

        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && event.getClickedBlock() != null) {
            if (event.getClickedBlock().getType().equals(Material.LEVER)) {

                Block lever = event.getClickedBlock();
                Switch leverSwitch = (Switch) lever.getBlockData();
                Player player = event.getPlayer();
                PowerPortal[] portals = StorageUtility.findPortals(lever);

                if (portals.length > 0) {
                    for (PowerPortal portal : portals) {
                        if (!portal.isActivated() && !leverSwitch.isPowered()) {

                            ActivationUtility.activate(player, portal);
                            player.sendMessage(ChatColor.GREEN + "[ P² ]" + ChatColor.GRAY + " Portal Activated. Use /link <name> to travel.");
                            player.playSound(player.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 1, 1);

                        } else if (portal.isActivated() && leverSwitch.isPowered()) {

                            ActivationUtility.deactivate(player, portal);
                            player.sendMessage(ChatColor.YELLOW + "[ P² ]" + ChatColor.GRAY + " Portal Deactivated.");
                            player.playSound(player.getLocation(), Sound.BLOCK_BEACON_DEACTIVATE, 1, 1);

                        }
                    }
                } else {

                    PowerPortal portal = null;

                    try {
                        portal = SmallPortalCreationUtility.attemptCreation(player, lever);
                    } catch (IllegalArgumentException e) {
                        player.sendMessage(ChatColor.RED + e.getMessage());
                    }

                    if (portal != null) {

                        StorageUtility.storePortal(portal);
                        player.playSound(player.getLocation(), Sound.BLOCK_END_PORTAL_FRAME_FILL, 1, 1);

                        double x = portal.getX();
                        double y = portal.getY();
                        double z = portal.getZ();
                        String name = portal.getName();
                        player.sendMessage(ChatColor.GREEN + "[ P² ]" + ChatColor.GRAY + " Portal \"" + name +
                                "\" created at (" + x + ", " + y + ", " + z + ")");
                        event.setCancelled(true);
                    }
                }
            }
        }
    }
}
