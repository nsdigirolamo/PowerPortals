package io.github.nsdigirolamo.powerportals.listeners;

import io.github.nsdigirolamo.powerportals.PowerPortals;
import io.github.nsdigirolamo.powerportals.structures.PowerPortal;
import io.github.nsdigirolamo.powerportals.utilities.ActivationUtility;
import io.github.nsdigirolamo.powerportals.utilities.LargePortalCreationUtility;
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

/**
 * Listens for the activation or deactivation of a PowerPortal
 */
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

                            ActivationUtility.activate(portal, player);
                            player.sendMessage(ChatColor.GREEN + "[ P² ]" + ChatColor.GRAY + " Portal Activated. Use /link <name> to travel.");
                            player.playSound(player.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 1, 1);

                        } else if (portal.isActivated() && leverSwitch.isPowered()) {

                            ActivationUtility.deactivate(portal, player);
                            player.sendMessage(ChatColor.YELLOW + "[ P² ]" + ChatColor.GRAY + " Portal Deactivated.");
                            player.playSound(player.getLocation(), Sound.BLOCK_BEACON_DEACTIVATE, 1, 1);

                        }
                    }
                } else {

                    PowerPortal smallPortal = null;

                    try {
                        smallPortal = SmallPortalCreationUtility.attemptCreation(player, lever);
                    } catch (IllegalArgumentException e) {
                        player.sendMessage(e.getMessage());
                    }

                    PowerPortal largePortal = null;

                    try {
                        largePortal = LargePortalCreationUtility.attemptCreation(player, lever);
                    } catch (IllegalArgumentException e) {
                        player.sendMessage(e.getMessage());
                    }

                    PowerPortal portal = null;

                    if (smallPortal != null && largePortal == null) {

                        portal = smallPortal;

                    } else if (smallPortal == null && largePortal != null) {

                        portal = largePortal;

                    }

                    if (portal != null) {

                        int maxPortalCount = PowerPortals.getPlugin().getConfig().getInt("maxPortalCount");

                        if (StorageUtility.findPortals(player).length < maxPortalCount ||
                                player.hasPermission("powerportals.portals.bypassMax")) {

                            StorageUtility.storePortal(portal);
                            player.playSound(player.getLocation(), Sound.BLOCK_END_PORTAL_FRAME_FILL, 1, 1);

                            double x = portal.getX();
                            double y = portal.getY();
                            double z = portal.getZ();
                            String name = portal.getName();

                            PowerPortals.getPlugin().getLogger().info("Registered a new portal owned by " +
                                    player.getName() + " named \"" + name + "\" at " + portal.getLocation());

                            player.sendMessage(ChatColor.GREEN + "[ P² ]" + ChatColor.GRAY + " Portal \"" + name +
                                    "\" registered at (" + x + ", " + y + ", " + z + ")");

                            event.setCancelled(true);

                        } else {

                            player.sendMessage(ChatColor.RED + "[ P² ]" + ChatColor.GRAY +
                                    " Portal creation failed. You have reached the maximum allowed number of portals.");

                        }
                    }
                }
            }
        }
    }
}
