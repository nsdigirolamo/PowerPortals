package io.github.nsdigirolamo.powerportals.utils;

import io.github.nsdigirolamo.powerportals.PowerPortals;
import io.github.nsdigirolamo.powerportals.models.PowerPortal;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Switch;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;

import java.util.UUID;

public class ActivationUtil {

    private static Plugin plugin = PowerPortals.getPlugin();

    /**
     * Looks for a portal by using the location of the lever.
     * @param lever The lever belonging to the portal.
     * @return The portal's ID if it's lever was located.
     */
    public static UUID findPortal (Block lever) {
        for(PowerPortal powerPortal: StorageUtil.getPortals()) {
            boolean x = lever.getX() == powerPortal.getLeverX();
            boolean y = lever.getY() == powerPortal.getLeverY();
            boolean z = lever.getZ() == powerPortal.getLeverZ();
            if (x && y && z) {
                return powerPortal.getID();
            }
        }
        return null;
    }

    /**
     * Activates a portal so a player can use it with /link <name>
     * @param player The player activating the portal.
     * @param id The portal's ID.
     */
    public static void activatePortal (Player player, UUID id) {

        PowerPortal portal = StorageUtil.readPowerPortal(id);
        player.sendMessage(ChatColor.GREEN + "[PowerPortals] Portal activated! Use /link <name> to travel.");
        player.playSound(player.getLocation(), Sound.BLOCK_END_PORTAL_FRAME_FILL, 1, 1);
        player.setMetadata("activatedPortal", new FixedMetadataValue(plugin, portal));

        int x = (int) portal.getLeverX();
        int y = (int) portal.getLeverY();
        int z = (int) portal.getLeverZ();
        Block block = Bukkit.getWorld(portal.getWorldID()).getBlockAt(x, y, z);

        if (block.getType() == Material.LEVER) {
            Switch lever = (Switch) block.getBlockData();
            lever.setPowered(true);
            block.setBlockData(lever);
        }
    }

    public static void linkPortals (PowerPortal entrance, PowerPortal exit) {

    }
}
