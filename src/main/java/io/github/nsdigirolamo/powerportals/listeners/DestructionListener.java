package io.github.nsdigirolamo.powerportals.listeners;

import io.github.nsdigirolamo.powerportals.structures.PowerPortal;
import io.github.nsdigirolamo.powerportals.utilities.ActivationUtility;
import io.github.nsdigirolamo.powerportals.utilities.StorageUtility;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Listens for the destruction of a PowerPortal
 */
public class DestructionListener implements Listener {

    /**
     * Removes a portal from storage and informs the owner.
     * @param portal The portal to be removed.
     */
    private void destroyPortal (PowerPortal portal) {
        ActivationUtility.deactivate(portal, null);
        Player owner = Bukkit.getPlayer(portal.getOwnerID());
        StorageUtility.deletePortal(portal);
        if (owner != null) {
            owner.sendMessage(ChatColor.RED + "[ P² ]" + ChatColor.GRAY + " Your portal named \"" + portal.getName() +
                    "\" at (" + portal.getX() + ", " + portal.getY() + ", " + portal.getZ() + ") has been destroyed.");
        }
    }

    /**
     * Calls destroyPortal() on any portals with broken blocks.
     * @param event the event passed when a block is broken.
     */
    @EventHandler
    public void onBlockBreak (BlockBreakEvent event) {
        Block brokenBlock = event.getBlock();
        PowerPortal[] brokenPortals = StorageUtility.findPortals(brokenBlock);

        for (PowerPortal portal : brokenPortals) {
            destroyPortal(portal);
            Player player = event.getPlayer();
            player.playSound(player.getLocation(), Sound.BLOCK_GLASS_BREAK, 1, 1);
        }
    }

    /**
     * Calls destroyPortal() on any portals that have been exploded by blocks.
     * @param event the event passed when a block is exploded.
     */
    @EventHandler
    public void onBlockExplode (BlockExplodeEvent event) {

        ArrayList<PowerPortal> brokenPortals = new ArrayList<>();

        for (Block explodedBlock : event.blockList()) {
            PowerPortal[] explodedPortals = StorageUtility.findPortals(explodedBlock);
            brokenPortals.addAll(Arrays.asList(explodedPortals));
        }

        for (PowerPortal portal : brokenPortals) {
            destroyPortal(portal);
        }
    }

    /**
     * Calls destroyPortal() on any portals that have been exploded by entities.
     * @param event the event passed when a block is exploded.
     */
    @EventHandler
    public void onEntityExplode (EntityExplodeEvent event) {

        ArrayList<PowerPortal> brokenPortals = new ArrayList<>();

        for (Block explodedBlock : event.blockList()) {
            PowerPortal[] explodedPortals = StorageUtility.findPortals(explodedBlock);
            brokenPortals.addAll(Arrays.asList(explodedPortals));
        }

        for (PowerPortal portal : brokenPortals) {
            destroyPortal(portal);
        }
    }

    /**
     * Calls destroyPortal() on any portals with moved blocks.
     * @param event the event passed when a piston extends.
     */
    @EventHandler
    public void onPistonExtend (BlockPistonExtendEvent event) {
        List<Block> movedBlocks = event.getBlocks();
        ArrayList<PowerPortal> brokenPortals = new ArrayList<>();

        for (Block movedBlock : movedBlocks) {
            PowerPortal[] movedPortals = StorageUtility.findPortals(movedBlock);
            brokenPortals.addAll(Arrays.asList(movedPortals));
        }

        for (PowerPortal portal : brokenPortals) {
            destroyPortal(portal);
        }
    }

    /**
     * Calls destroyPortal() on any portals with moved blocks.
     * @param event the event passed when a piston retracts.
     */
    @EventHandler
    public void onPistonRetract (BlockPistonRetractEvent event) {
        List<Block> movedBlocks = event.getBlocks();
        ArrayList<PowerPortal> brokenPortals = new ArrayList<>();

        for (Block movedBlock : movedBlocks) {
            PowerPortal[] movedPortals = StorageUtility.findPortals(movedBlock);
            brokenPortals.addAll(Arrays.asList(movedPortals));
        }

        for (PowerPortal portal : brokenPortals) {
            destroyPortal(portal);
        }
    }

}
