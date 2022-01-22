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

public class ActivationUtil {

    private static Plugin plugin = PowerPortals.getPlugin();

    public static PowerPortal findPortal (Block lever) {
        for(PowerPortal portal: StorageUtil.getPortals()) {
            if (portal.getLever().equals(lever)) {
                return portal;
            }
        }
        return null;
    }

    public static void activatePortal (Player player, PowerPortal portal) {
        player.sendMessage(ChatColor.GREEN + "[PowerPortals] Portal activated! Use /link <name> to travel.");
        player.playSound(player.getLocation(), Sound.BLOCK_END_PORTAL_FRAME_FILL, 1, 1);
        player.setMetadata("activatedPortal", new FixedMetadataValue(plugin, portal));

        Switch lever = (Switch) portal.getLever().getBlockData();
        lever.setPowered(true);
        portal.getLever().setBlockData(lever);
    }

    public static void linkPortals (PowerPortal entrance, PowerPortal exit) {

    }
}
