package io.github.nsdigirolamo.powerportals.utils;

import io.github.nsdigirolamo.powerportals.PowerPortals;
import io.github.nsdigirolamo.powerportals.models.PowerPortal;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Switch;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;

/**
 * A utility for activating PowerPortals.
 */
public class ActivationUtil {

    private static Plugin plugin = PowerPortals.getPlugin();

    /**
     * Finds a PowerPortal with the given lever, if it exists.
     * @param lever The lever of the PowerPortal
     * @return The PowerPortal that the given lever belongs to. Null if no such PowerPortal exists
     */
    public static PowerPortal findPortal (Block lever) {
        for(PowerPortal portal: StorageUtil.getPortals()) {
            if (portal.getLever().equals(lever)) {
                return portal;
            }
        }
        return null;
    }

    /**
     * Activates a PowerPortal for a given player. Marks the player with activatedPortal metadata to show that they
     * have activated a portal.
     * @param player The player activating the portal.
     * @param portal The portal to be activated.
     */
    public static void activatePortal (Player player, PowerPortal portal) {
        player.sendMessage(ChatColor.GREEN + "[PowerPortals] Portal activated! Use /link <name> to travel.");
        player.playSound(player.getLocation(), Sound.BLOCK_END_PORTAL_FRAME_FILL, 1, 1);
        player.setMetadata("activatedPortal", new FixedMetadataValue(plugin, portal));

        Switch lever = (Switch) portal.getLever().getBlockData();
        lever.setPowered(true);
        portal.getLever().setBlockData(lever);
    }
}
