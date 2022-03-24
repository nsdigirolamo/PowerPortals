package io.github.nsdigirolamo.powerportals.utilities;

import io.github.nsdigirolamo.powerportals.PowerPortals;
import io.github.nsdigirolamo.powerportals.structures.PowerPortal;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Switch;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import javax.annotation.Nullable;

public class ActivationUtility {

    /**
     * Activates a portal and sets player metadata.
     * @param portal the portal to be activated.
     * @param player the player who activated a portal.
     */
    public static void activate(PowerPortal portal, Player player) {

        portal.setActivated(true);
        player.setMetadata("activatedPortal",
                new FixedMetadataValue(PowerPortals.getPlugin(), portal.getId()));

    }

    /**
     * Deactivates a portal and removes player metadata. Also deactivates the portal's exit if it exists.
     * @param portal the portal to be deactivated.
     * @param player the player who deactivated a portal.
     */
    public static void deactivate(PowerPortal portal, @Nullable Player player) {

        portal.setActivated(false);

        for (Block trigger : portal.getTriggers()) {
            if (trigger.getType().equals(Material.WATER)) {
                trigger.setType(Material.AIR);
            }
        }

        PowerPortal exit = portal.getExit();

        if (exit != null) {

            exit.setActivated(false);

            for (Block trigger: exit.getTriggers()) {
                if (trigger.getType().equals(Material.WATER)) {
                    trigger.setType(Material.AIR);
                }
            }

            portal.setExit(null);

            // turn off lever
            Switch lever = (Switch) portal.getLever().getBlockData();
            lever.setPowered(false);
            portal.getLever().setBlockData(lever);

            if (player != null) {
                player.removeMetadata("activatedPortal", PowerPortals.getPlugin());
            }
        }
    }

}
