package io.github.nsdigirolamo.powerportals.utilities;

import io.github.nsdigirolamo.powerportals.PowerPortals;
import io.github.nsdigirolamo.powerportals.structures.PowerPortal;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Switch;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

public class ActivationUtility {

    /**
     * Activates a portal and sets player metadata.
     * @param player the player who activated a portal.
     * @param portal the portal to be activated.
     */
    public static void activate (Player player, PowerPortal portal) {

        portal.setActivated(true);
        player.setMetadata("activatedPortal",
                new FixedMetadataValue(PowerPortals.getPlugin(), portal.getId()));

    }

    /**
     * Deactivates a portal and removes player metadata. Also deactivates the portal's exit if it exists.
     * @param player the player who deactivated a portal.
     * @param portal the portal to be deactivated.
     */
    public static void deactivate (Player player, PowerPortal portal) {

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

            player.removeMetadata("activatedPortal", PowerPortals.getPlugin());
        }
    }

}
