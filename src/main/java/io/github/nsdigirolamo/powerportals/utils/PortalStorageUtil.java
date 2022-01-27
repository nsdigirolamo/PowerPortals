package io.github.nsdigirolamo.powerportals.utils;

import io.github.nsdigirolamo.powerportals.structures.PowerPortal;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.ArrayList;

/**
 * PortalStorageUtil is a utility that handles the storage of PowerPortals.
 */
public class PortalStorageUtil {

    private static ArrayList<PowerPortal> portals = new ArrayList<>();

    /**
     * Gets stored PowerPortals.
     * @return All stored PowerPortals.
     */
    public static PowerPortal[] getPortals () {
        PowerPortal[] list = new PowerPortal[portals.size()];
        for (PowerPortal p: portals) {
            list[portals.indexOf(p)] = p;
        }
        return list;
    }

    /**
     * Stores a PowerPortal.
     * @param portal The PowerPortal to be stored.
     */
    public static void storePortal (PowerPortal portal) {
        portals.add(portal);
    }

    /**
     * Removes a PowerPortal from storage.
     * @param portal The PowerPortal to be deleted.
     */
    public static void deletePortal (PowerPortal portal) {
        portals.remove(portal);
    }

    /**
     * Finds a PowerPortal based on its lever in the game world.
     * @param lever The PowerPortal's lever.
     * @return The PowerPortal the given lever belongs to. Null if no such PowerPortal exists.
     */
    public static PowerPortal findPortal (Block lever) {
        if (lever.getType().equals(Material.LEVER)) {
            for (PowerPortal portal: portals) {
                if (portal.getLever().equals(lever)) {
                    return portal;
                }
            }
        }
        return null;
    }

    /**
     * Finds a PowerPortal based on its name.
     * @param name The PowerPortal's name.
     * @return The PowerPortal the given name belongs to. Null if no such PowerPortal exists.
     */
    public static PowerPortal findPortal (String name) {
        for (PowerPortal portal: portals) {
            if (portal.getName().equals(name)) {
                return portal;
            }
        }
        return null;
    }
}
