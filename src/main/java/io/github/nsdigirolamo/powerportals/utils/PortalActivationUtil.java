package io.github.nsdigirolamo.powerportals.utils;

import io.github.nsdigirolamo.powerportals.PowerPortals;
import io.github.nsdigirolamo.powerportals.structures.PowerPortal;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.block.data.type.Switch;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.ArrayList;

/**
 * PortalActivationUtil is a utility that handles the activation of portals.
 */
public class PortalActivationUtil {

    private static ArrayList<PowerPortal> portals = PortalStorageUtil.getPortals();

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

    public static void deactivatePortal (Player player, PowerPortal portal) {
        portal.setEntrance(false);
        portal.removeExit();
        player.removeMetadata("activatedPortal", PowerPortals.getPlugin());
        Switch lever = (Switch) portal.getLever().getBlockData();
        lever.setPowered(false);
    }
}
