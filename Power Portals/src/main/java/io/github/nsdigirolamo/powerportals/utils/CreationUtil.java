package io.github.nsdigirolamo.powerportals.utils;

import io.github.nsdigirolamo.powerportals.models.PowerPortal;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.block.data.type.Switch;
import org.bukkit.block.data.type.WallSign;
import org.bukkit.entity.Player;

/**
 * A utility for creating PowerPortals.
 */
public class CreationUtil {

    private static final ChatColor RED = ChatColor.RED;
    private static final ChatColor GREEN = ChatColor.GREEN;

    /**
     * Attempts to create a new PowerPortal.
     * Fails if the PowerPortal's sign has no text.
     * Fails if there are no blocks under the PowerPortal.
     * Fails if the PowerPortal is too close to another PowerPortal.
     * Fails if the PowerPortal's name is already taken.
     * @param player The owner of the new PowerPortal.
     * @param lever The lever of the new PowerPortal.
     * @return True if the PowerPortal was successfully created. False if the creation failed.
     */
    public static boolean attemptCreation (Player player, Block lever) {

        Block[] portal;

        if (lever.getType() == Material.LEVER) {
            portal = createSmallPortal(lever);
        } else {
            return false;
        }

        if(checkSmallPortal(portal)) {

            Sign sign = (Sign) portal[13].getState();
            String name = sign.getLine(0);

            Block base = portal[8]; // Block directly below portal
            Block origin = portal[17]; // Origin block (directly in front of base block)

            // Marking the trigger blocks. If a player enters any trigger blocks, they will be teleported by the portal.
            int[] triggers = new int[] {5, 6, 7};

            if(name.isEmpty()) {
                player.sendMessage(RED + "[PowerPortals] Possible portal detected, but the sign is blank.");
            } else if (base.isPassable() || origin.isPassable()) {
                player.sendMessage(RED + "[PowerPortals] Possible portal detected, but there's nothing under it.");
            } else if (!checkDistance(origin.getLocation())) {
                player.sendMessage(RED + "[PowerPortals] Possible portal detected, but it's too close to another portal");
            } else if (!checkName(name)) {
                player.sendMessage(RED + "[PowerPortals] Possible portal detected, but its name is already taken.");
            } else {
                player.sendMessage(GREEN + "[PowerPortals] Valid gate detected!");

                PowerPortal newPortal = new PowerPortal(name, player, portal, 17, 18, triggers);
                StorageUtil.storePowerPortal(newPortal);

                sign.setLine(0, newPortal.getName());
                sign.setLine(1, newPortal.getOwnerName());
                int x = newPortal.getOrigin().getX();
                int y = newPortal.getOrigin().getY();
                int z = newPortal.getOrigin().getZ();
                sign.setLine(2, "(" + x + ", " + y + ", " + z + ")");
                sign.setLine(3, "");
                sign.update();

                // TODO: Figure out how to get signs on portals to face the same direction as the lever.

                String coordinates = "(" + x + ", " + y + ", " + z + ")";
                player.sendMessage(GREEN + "[PowerPortals] Gate " + newPortal.getName() + " created at " + coordinates);
                player.playSound(player.getLocation(), Sound.BLOCK_END_PORTAL_FRAME_FILL, 1, 1);
                return true;
            }
        }
        return false;
    }

    /**
     * Creates an array of Blocks that represents a Small PowerPortal.
     * @param lever The lever of the Small PowerPortal
     * @return An array of Blocks that make up a Small PowerPortal
     */
    private static Block[] createSmallPortal (Block lever) {
        /*
         * Creating a Portal with this pattern:
         *
         *  Plane 1       Plane 2
         *  0 1 2 3 4     0 1 2 3 4
         *      []     0             0
         *    []AA[]   1      AA     1
         *    []AA[]   2    SSAALL   2
         *  [][]AA[][] 3      AA     3
         *      GG     4      OO     4
         *
         * Plane 1 is in front of Plane 0.
         * AA are air blocks.
         * GG is any solid ground.
         * OO is the portal's origin block (also solid ground).
         * [] are obsidian blocks.
         * LL is a lever. It is attached to block 3-2 on Plane 0.
         * SS is a sign. It is attached to block 1-2 on Plane 0.
         *
         */

        BlockFace direction  = ((Switch) lever.getBlockData()).getFacing();
        // Only 19 blocks in our Small Portal template, so our array only needs 19 indexes.
        Block[] portal = new Block[19];
        Block origin;

        if (direction.toString().equals("NORTH")) {

            origin = lever.getRelative(1, -2, 0);
            // Plane 1
            portal[0] = origin.getRelative(2, 1, 1);
            portal[1] = origin.getRelative(1, 3, 1);
            portal[2] = origin.getRelative(1, 2, 1);
            portal[3] = origin.getRelative(1, 1, 1);
            portal[4] = origin.getRelative(0, 4, 1);
            portal[5] = origin.getRelative(0, 3, 1);
            portal[6] = origin.getRelative(0, 2, 1);
            portal[7] = origin.getRelative(0, 1, 1);
            portal[8] = origin.getRelative(0, 0, 1);
            portal[9] = origin.getRelative(-1, 3, 1);
            portal[10] = origin.getRelative(-1, 2, 1);
            portal[11] = origin.getRelative(-1, 1, 1);
            portal[12] = origin.getRelative(-2, 1, 1);
            // Plane 2
            portal[13] = origin.getRelative(1, 2, 0);
            portal[14] = origin.getRelative(0, 3, 0);
            portal[15] = origin.getRelative(0, 2, 0);
            portal[16] = origin.getRelative(0, 1, 0);
            portal[17] = origin.getRelative(0, 0, 0);
            portal[18] = origin.getRelative(-1, 2, 0);

        } else if (direction.toString().equals("SOUTH")) {

            origin = lever.getRelative(-1, -2, 0);
            // Plane 1
            portal[0] = origin.getRelative(-2, 1, -1);
            portal[1] = origin.getRelative(-1, 3, -1);
            portal[2] = origin.getRelative(-1, 2, -1);
            portal[3] = origin.getRelative(-1, 1, -1);
            portal[4] = origin.getRelative(0, 4, -1);
            portal[5] = origin.getRelative(0, 3, -1);
            portal[6] = origin.getRelative(0, 2, -1);
            portal[7] = origin.getRelative(0, 1, -1);
            portal[8] = origin.getRelative(0, 0, -1);
            portal[9] = origin.getRelative(1, 3, -1);
            portal[10] = origin.getRelative(1, 2, -1);
            portal[11] = origin.getRelative(1, 1, -1);
            portal[12] = origin.getRelative(2, 1, -1);
            // Plane 2
            portal[13] = origin.getRelative(-1, 2, 0);
            portal[14] = origin.getRelative(0, 3, 0);
            portal[15] = origin.getRelative(0, 2, 0);
            portal[16] = origin.getRelative(0, 1, 0);
            portal[17] = origin.getRelative(0, 0, 0);
            portal[18] = origin.getRelative(1, 2, 0);

        } else if (direction.toString().equals("EAST")) {

            origin = lever.getRelative(0, -2, 1);
            // Plane 1
            portal[0] = origin.getRelative(-1, 1, 2);
            portal[1] = origin.getRelative(-1, 3, 1);
            portal[2] = origin.getRelative(-1, 2, 1);
            portal[3] = origin.getRelative(-1, 1, 1);
            portal[4] = origin.getRelative(-1, 4, 0);
            portal[5] = origin.getRelative(-1, 3, 0);
            portal[6] = origin.getRelative(-1, 2, 0);
            portal[7] = origin.getRelative(-1, 1, 0);
            portal[8] = origin.getRelative(-1, 0, 0);
            portal[9] = origin.getRelative(-1, 3, 1);
            portal[10] = origin.getRelative(-1, 2, 1);
            portal[11] = origin.getRelative(-1, 1, 1);
            portal[12] = origin.getRelative(-1, 1, 2);
            // Plane 2
            portal[13] = origin.getRelative(0, 2, 1);
            portal[14] = origin.getRelative(0, 3, 0);
            portal[15] = origin.getRelative(0, 2, 0);
            portal[16] = origin.getRelative(0, 1, 0);
            portal[17] = origin.getRelative(0, 0, 0);
            portal[18] = origin.getRelative(0, 2, -1);

        } else if (direction.toString().equals("WEST")) {

            origin = lever.getRelative(0, -2, -1);
            // Plane 1
            portal[0] = origin.getRelative(1, 1, -2);
            portal[1] = origin.getRelative(1, 3, -1);
            portal[2] = origin.getRelative(1, 2, -1);
            portal[3] = origin.getRelative(1, 1, -1);
            portal[4] = origin.getRelative(1, 4, 0);
            portal[5] = origin.getRelative(1, 3, 0);
            portal[6] = origin.getRelative(1, 2, 0);
            portal[7] = origin.getRelative(1, 1, 0);
            portal[8] = origin.getRelative(1, 0, 0);
            portal[9] = origin.getRelative(1, 3, -1);
            portal[10] = origin.getRelative(1, 2, -1);
            portal[11] = origin.getRelative(1, 1, -1);
            portal[12] = origin.getRelative(1, 1, -2);
            // Plane 2
            portal[13] = origin.getRelative(0, 2, -1);
            portal[14] = origin.getRelative(0, 3, 0);
            portal[15] = origin.getRelative(0, 2, 0);
            portal[16] = origin.getRelative(0, 1, 0);
            portal[17] = origin.getRelative(0, 0, 0);
            portal[18] = origin.getRelative(0, 2, 1);

        }
        return portal;
    }

    /**
     * Checks a possible Small PowerPortal to see if the blocks are placed in valid locations and are valid types.
     * @param portal An array of Blocks representing a possible Small PowerPortal
     * @return True if the Small PowerPortal is valid
     */
    private static boolean checkSmallPortal(Block[] portal) {
        /*
         * Looking for a Portal with this pattern:
         *
         *  Plane 1       Plane 2
         *  0 1 2 3 4     0 1 2 3 4
         *      []     0             0
         *    []AA[]   1      AA     1
         *    []AA[]   2    SSAALL   2
         *  [][]AA[][] 3      AA     3
         *      GG     4      OO     4
         *
         * Plane 1 is in front of Plane 0.
         * AA are air blocks.
         * GG is any solid ground.
         * OO is the portal's origin block (also solid ground).
         * [] are obsidian blocks.
         * LL is a lever. It is attached to block 3-2 on Plane 0.
         * SS is a sign. It is attached to block 1-2 on Plane 0.
         *
         */
        return  portal[0].getType() == Material.OBSIDIAN      &&
                portal[1].getType() == Material.OBSIDIAN      &&
                portal[2].getType() == Material.OBSIDIAN      &&
                portal[3].getType() == Material.OBSIDIAN      &&
                portal[4].getType() == Material.OBSIDIAN      &&
                portal[5].getType() == Material.AIR           &&
                portal[6].getType() == Material.AIR           &&
                portal[7].getType() == Material.AIR           &&
                // !portal[8].isPassable()                    && // This gets checked later in AttemptCreation
                portal[9].getType() == Material.OBSIDIAN      &&
                portal[10].getType() == Material.OBSIDIAN     &&
                portal[11].getType() == Material.OBSIDIAN     &&
                portal[12].getType() == Material.OBSIDIAN     &&

                portal[13].getBlockData() instanceof WallSign && // Sign can be any kind of wall sign
                portal[14].getType() == Material.AIR          &&
                portal[15].getType() == Material.AIR          &&
                portal[16].getType() == Material.AIR          &&
                // !portal[17].isPassable()                   && // This gets checked later in AttemptCreation
                portal[18].getType() == Material.LEVER;
    }

    /**
     * Checks if an origin of a PowerPortal is too close to any of the other PowerPortals.
     * @param origin The origin of the PowerPortal
     * @return True if the PowerPortal is far enough away from other PowerPortals
     */
    private static boolean checkDistance(Location origin) {
        for (PowerPortal portal: StorageUtil.getPortals()) {
            if (origin.distance(portal.getOrigin().getLocation()) < 1) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if a PowerPortal name is already taken by another PowerPortal.
     * @param portalName The name.
     * @return True if the name is not taken
     */
    private static boolean checkName(String portalName) {
        for (PowerPortal portal: StorageUtil.getPortals()) {
            if (portal.getName().equals(portalName)) {
                return false;
            }
        }
        return true;
    }
}
