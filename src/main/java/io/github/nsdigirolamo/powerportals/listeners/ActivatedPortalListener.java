package io.github.nsdigirolamo.powerportals.listeners;

import io.github.nsdigirolamo.powerportals.structures.PowerPortal;
import io.github.nsdigirolamo.powerportals.utils.PortalActivationUtil;
import io.github.nsdigirolamo.powerportals.utils.PortalCreationUtil;
import io.github.nsdigirolamo.powerportals.utils.PortalStorageUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Switch;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * ActivatedPortalListener listens for any portals being activated in the world.
 */
public class ActivatedPortalListener implements Listener {

    /**
     * Listens for levers being right-clicked by players, and then passes those levers to other methods to check for
     * a valid portal in that lever's area.
     * @param event A PlayerInteractEvent where the player has right-clicked a lever.
     */
    @EventHandler
    public void onLeverClick(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getType().equals(Material.LEVER)) {

            Block lever = event.getClickedBlock();
            Switch leverSwitch = (Switch) lever.getBlockData();
            Player player = event.getPlayer();
            PowerPortal portal = PortalStorageUtil.findPortal(lever);

            if (portal != null && !leverSwitch.isPowered()) {
                PortalActivationUtil.activatePortal(player, portal);
            } else if (portal != null && leverSwitch.isPowered()) {
                PortalActivationUtil.deactivatePortal(player, portal);
                player.sendMessage(ChatColor.YELLOW + "[PowerPortals] " + portal.getName() + " portal deactivated.");
            } else {
                boolean created = PortalCreationUtil.attemptCreation(player, lever);
                if (created) {
                    event.setCancelled(true);
                    leverSwitch.setPowered(false);
                    lever.setBlockData(leverSwitch);
                }
            }
        }
    }
}
