package io.github.nsdigirolamo.powerportals.utils;

import io.github.nsdigirolamo.powerportals.structures.PowerPortal;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

/**
 * PortalTeleportUtil is a utility that handles the teleportation of players between linked PowerPortals.
 */
public class PortalTeleportUtil {

    /**
     * Teleport a player between two linked portals.
     * @param player The player.
     * @param entrance The entrance portal the player is using.
     */
    public static void useEntrance(Player player, PowerPortal entrance) {
        PowerPortal exit = entrance.getExit();
        Vector facing = exit.getFacing().getDirection();
        player.teleport(exit.getOrigin().getLocation().setDirection(facing).add(0.5, 1, 0.5));
        PortalActivationUtil.deactivatePortal(player, entrance);
    }
}
