package io.github.nsdigirolamo.powerportals.listeners;

import io.github.nsdigirolamo.powerportals.structures.PowerPortal;
import io.github.nsdigirolamo.powerportals.utils.PortalStorageUtil;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

/**
 * DestroyedPortalListener listens for any destroyed portals.
 */
public class DestroyedPortalListener implements Listener {

    /**
     * Listens for blocks being broken by players, and if any of those blocks belong to a portal, that portal is destroyed.
     * @param event Where a block was broken.
     */
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        // TODO: Players can currently break the lever of a portal using a piston, and the PowerPortal will not break.
        Block brokenBlock = event.getBlock();
        for (PowerPortal portal: PortalStorageUtil.getPortals()) {
            for (Block block: portal.getClearBlocks()) {
                if (block.equals(brokenBlock)) {
                    return;
                }
            }
            for (Block block: portal.getPortalBlocks()) {
                if (block.equals(brokenBlock)) {
                    Player player = portal.getOwner().getPlayer();
                    if (player != null) {
                        player.sendMessage(ChatColor.RED + "[PowerPortals] Your portal \"" + portal.getName() + "\" has been destroyed.");
                    }
                    PortalStorageUtil.deletePortal(portal);
                }
            }
        }
    }
}
