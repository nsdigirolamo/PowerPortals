package io.github.nsdigirolamo.powerportals.utils;

import io.github.nsdigirolamo.powerportals.structures.PowerPortal;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;

import java.util.List;

public class PortalLinkUtil {

    public static boolean checkPlayerMetaData (Player player) {
        List<MetadataValue> values = player.getMetadata("activatedPortal");
        return !values.isEmpty() && values.get(0).value() instanceof PowerPortal;
    }

    public static void link (Player player, PowerPortal entrance, PowerPortal exit) {

        for (Block block: entrance.getClearBlocks()) {
            if (!block.getType().equals(Material.AIR) && !block.getType().equals(Material.WATER)) {
                player.sendMessage(ChatColor.RED + "[PowerPortals] Link failed. Entrance is blocked.");
                return;
            }
        }

        for (Block block: exit.getClearBlocks()) {
            if (block.getType() != Material.AIR && block.getType() != Material.WATER) {
                player.sendMessage(ChatColor.RED + "[PowerPortals] Link failed. Exit is blocked.");
                return;
            }
        }

        entrance.setEntrance(true);
        entrance.setExit(exit);

        for (Block block: entrance.getTriggerBlocks()) {
            block.setType(Material.WATER, false);
        }

        for (Block block: exit.getTriggerBlocks()) {
            block.setType(Material.WATER, false);
        }

        player.playSound(player.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 1, 1);
    }

    public static void unLink (PowerPortal entrance, PowerPortal exit) {
        entrance.setEntrance(false);
        entrance.removeExit();
    }
}
