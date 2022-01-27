package io.github.nsdigirolamo.powerportals.utils;

import io.github.nsdigirolamo.powerportals.PowerPortals;
import io.github.nsdigirolamo.powerportals.structures.PowerPortal;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Switch;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.ArrayList;

/**
 * PortalActivationUtil is a utility that handles the activation of portals.
 */
public class PortalActivationUtil {

    /**
     * Marks a player if they have activated a portal. Sets the activated portal as an entrance.
     * @param player The player that activated the portal.
     * @param portal The portal to be activated.
     */
    public static void activatePortal (Player player, PowerPortal portal) {
        portal.setEntrance(true);
        player.setMetadata("activatedPortal", new FixedMetadataValue(PowerPortals.getPlugin(), portal));

        player.sendMessage(ChatColor.GREEN + "[PowerPortals] " + portal.getName() +
                " portal activated! Use /link <name> to connect to another portal.");
    }

    /**
     * Deactivates a portal.
     * @param player The player who deactivated the portal.
     * @param portal The portal to be deactivated.
     */
    public static void deactivatePortal (Player player, PowerPortal portal) {

        for (Block block: portal.getTriggerBlocks()) {
            block.setType(Material.AIR);
        }

        if (portal.hasExit()) {
            for (Block block: portal.getExit().getTriggerBlocks()) {
                block.setType(Material.AIR);
            }
        }

        portal.setEntrance(false);
        portal.removeExit();
        player.removeMetadata("activatedPortal", PowerPortals.getPlugin());

        Switch lever = (Switch) portal.getLever().getBlockData();
        lever.setPowered(false);
        portal.getLever().setBlockData(lever);

    }

}
