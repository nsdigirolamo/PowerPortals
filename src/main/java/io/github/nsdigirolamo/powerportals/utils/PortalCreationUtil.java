package io.github.nsdigirolamo.powerportals.utils;

import io.github.nsdigirolamo.powerportals.PowerPortals;
import io.github.nsdigirolamo.powerportals.structures.PowerPortal;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * PortalCreationUtil is a utility that handles the creation of PowerPortals.
 */
public class PortalCreationUtil {

    private static final FileConfiguration config = PowerPortals.getPlugin().getConfig();

    /**
     * Creates a portal, depending on the stored portal designs and if a valid design exists at the lever's location.
     * @param owner The owner of the new portal.
     * @param lever The lever of the new portal.
     * @return The new portal, or null if a portal was not able to be created.
     */
    public static boolean attemptCreation (Player owner, Block lever) {

        if (!lever.getType().equals(Material.LEVER)) {
            return false;
        }

        ArrayList<PowerPortal> ownedPortals = new ArrayList<>();

        for (PowerPortal portal: PortalStorageUtil.getPortals()) {
            if (portal.getOwner().getUniqueId().equals(owner.getUniqueId())) {
                ownedPortals.add(portal);
            }
        }

        if (!(ownedPortals.size() < config.getInt("maxPortalCount")) && !(config.getInt("maxPortalCount") < 0)) {
            owner.sendMessage(ChatColor.RED + "[PowerPortal] Portal creation failed. You have reached the maximum number of allowed portals.");
            return false;
        }

        PowerPortal portal = null;

        if (config.getBoolean("customPortalDesigns")) {

            portal = CustomDesignUtil.attemptCreation(owner, lever);

        } else {

            portal = DefaultDesignUtil.attemptSmallDefaultPortal(owner, lever);

        }

        if (portal != null) {

            owner.playSound(owner.getLocation(), Sound.BLOCK_END_PORTAL_FRAME_FILL, 1, 1);

            PortalStorageUtil.addPortal(portal);

            Bukkit.getLogger().info("[PowerPortals] " + portal.getOwner().getName() +
                    " created a new portal named \"" + portal.getName() + "\" at (" + portal.getX() +
                    ", " + portal.getY() + ", " + portal.getZ() + ")");

            return true;
        }

        return false;
    }

    public static PowerPortal createPortal (Player owner, Block[] portalBlocks, int[] triggerIndexes, int[] clearIndexes, int originIndex, int leverIndex, int signIndex) {

        String name = "null";

        if (portalBlocks[signIndex].getState() instanceof Sign) {
            Sign sign = (Sign) portalBlocks[signIndex].getState();
            Block origin = portalBlocks[originIndex];

            name = (sign).getLine(0);

            if (!name.matches("[a-zA-Z0-9]+")) {
                owner.sendMessage(ChatColor.RED + "[PowerPortals] A portal's name must be made of only letters and numbers.");
                return null;
            }

            for (PowerPortal existingPortal : PortalStorageUtil.getPortals()) {
                if (existingPortal.getName().equals(name)) {
                    owner.sendMessage(ChatColor.RED + "[PowerPortals] A portal with that name already exists.");
                    return null;
                }
            }

            sign.setLine(1, owner.getName());
            sign.setLine(2, "( " + origin.getX() + ", " + origin.getY() + ", " + origin.getZ() + ")");
            sign.setLine(3, "");
            sign.update();

            PowerPortal portal = new PowerPortal(name, owner, portalBlocks, triggerIndexes, clearIndexes, originIndex, leverIndex, signIndex);

            return portal;
        }
        return null;
    }
}
