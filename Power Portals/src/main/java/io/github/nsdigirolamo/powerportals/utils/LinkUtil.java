package io.github.nsdigirolamo.powerportals.utils;

import io.github.nsdigirolamo.powerportals.PowerPortals;
import io.github.nsdigirolamo.powerportals.models.Linkage;
import io.github.nsdigirolamo.powerportals.models.PowerPortal;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Utility for creating and using Linkages
 */
public class LinkUtil {

    private static ArrayList<Linkage> linkages = new ArrayList<>();
    static Plugin plugin = PowerPortals.getPlugin();

    public static ArrayList<Linkage> getLinkages () {
        return linkages;
    }

    /**
     * Creates a Linkage between two PowerPortals. Fails if the Linkage's entrance is already the entrance for an
     * already existing Linkage.
     * @param entrance The entrance of the Linkage.
     * @param exit The exit of the Linkage.
     * @return The new Linkage, null if the Linkage creation failed
     */
    public static Linkage createLinkage (Player player, PowerPortal entrance, PowerPortal exit) {

        for (Linkage linkage: linkages) {
            // Check to make sure we're not duplicating an entrance
            if (linkage.getEntrance().equals(entrance)) {
                player.sendMessage(ChatColor.RED + "[PowerPortals] Link Failed. Portal is already an entrance!");
                return null;
            }
        }

        // TODO: Check for any blocks blocking the portal water or teleport destination

        for (Block block: entrance.getTriggers()) {
            block.setType(Material.WATER, false);
        }
        for (Block block: exit.getTriggers()) {
            block.setType(Material.WATER, false);
        }

        Linkage linkage = new Linkage(entrance, exit, entrance.getTriggers(), exit.getTriggers());
        linkages.add(linkage);
        player.playSound(player.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 1, 1);
        return linkage;
    }

    /**
     * Triggers a linkage between two PowerPortals. If a player enters the triggers for an entrance PowerPortal, they
     * will be teleported to the origin of the exit PowerPortal.
     * @param player The player entering the triggers
     * @param linkage The Linkage being triggered
     * @param trigger The trigger block being entered by the player
     */
    public static void triggerLinkage (Player player, Linkage linkage, Block trigger) {
        for (Block block: linkage.getEntranceTriggers()) {
            if (block.equals(trigger)) {
                // TODO: Do some kind of check to make sure the teleport destination is not blocked.
                Location exitOrigin = linkage.getExit().getOrigin().getLocation();
                Vector exitFacing = linkage.getExit().getFacing();
                double x = 0.5;
                double y = 1;
                double z = 0.5;
                player.teleport(exitOrigin.setDirection(exitFacing).add(x, y, z));
                player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
                break;
            }
        }

        for (Block block: linkage.getEntranceTriggers()) {
            block.setType(Material.AIR);
        }

        for (Block block: linkage.getExitTriggers()) {
            block.setType(Material.AIR);
        }

        player.removeMetadata("activatedPortal", plugin);
        linkages.remove(linkage);
    }

    /**
     * Remove a Linkage.
     * @param id The id of the Linkage.
     */
    public static void removeLinkage (UUID id) {
        for (Linkage linkage: linkages) {
            if (linkage.getID().equals(id)) {
                linkages.remove(linkage);
                break;
            }
        }
    }

    /**
     * Read a Linkage.
     * @param id The id of the Linkage.
     * @return The Linkage of the ID. Null if the ID was not found.
     */
    public static Linkage readLinkage (UUID id) {
        for (Linkage linkage: linkages) {
            if (linkage.getID().equals(id)) {
                return linkage;
            }
        }
        return null;
    }

    /**
     * Find a Linkage based on its entrance and exit.
     * @param entrance The entrance PowerPortal.
     * @param exit The exit PowerPortal.
     * @return A Linkage with the given entrance and exit. Null if no matching Linkage was found.
     */
    public static Linkage findLinkage (PowerPortal entrance, PowerPortal exit) {
        for (Linkage linkage: linkages) {
            if (linkage.getEntrance().equals(entrance) && linkage.getExit().equals(exit)) {
                return linkage;
            }
        }
        return null;
    }
}
