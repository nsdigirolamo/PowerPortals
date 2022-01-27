package io.github.nsdigirolamo.powerportals.utils;

import io.github.nsdigirolamo.powerportals.structures.PowerPortal;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.ArrayList;

public class PortalStorageUtil {

    private static ArrayList<PowerPortal> portals = new ArrayList<>();

    public static ArrayList<PowerPortal> getPortals () {
        return portals;
    }

    public static void storePortal (PowerPortal portal) {
        portals.add(portal);
    }

    public static void deletePortal (PowerPortal portal) {
        portals.remove(portal);
    }

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

    public static PowerPortal findPortal (String name) {
        for (PowerPortal portal: portals) {
            if (portal.getName().equals(name)) {
                return portal;
            }
        }
        return null;
    }
}
