package io.github.nsdigirolamo.powerportals.utils;

import io.github.nsdigirolamo.powerportals.structures.PowerPortal;
import org.bukkit.entity.Player;

public class PortalTeleportUtil {

    public static void useEntrance(Player player, PowerPortal entrance) {
        PowerPortal exit = entrance.getExit();
        player.teleport(exit.getOrigin().getLocation().add(0.5, 1, 0.5));
        entrance.removeExit();
        entrance.setEntrance(false);
    }
}
