package io.github.nsdigirolamo.powerportals.utils;

import io.github.nsdigirolamo.powerportals.PowerPortals;
import io.github.nsdigirolamo.powerportals.structures.PowerPortal;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;

public class PortalActivationUtil {

    private static ArrayList<PowerPortal> portals = PortalStorageUtil.getPortals();

    public static void activatePortal (Player player, PowerPortal portal) {
        portal.setEntrance(true);
        player.setMetadata("activatedPortal", new FixedMetadataValue(PowerPortals.getPlugin(), portal));
    }
}
