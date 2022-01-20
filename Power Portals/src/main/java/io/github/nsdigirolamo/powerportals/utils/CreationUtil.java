package io.github.nsdigirolamo.powerportals.utils;

import io.github.nsdigirolamo.powerportals.models.PowerPortal;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.block.data.type.Switch;
import org.bukkit.block.data.type.WallSign;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class CreationUtil {

    private static final ChatColor RED = ChatColor.RED;
    private static final ChatColor GREEN = ChatColor.GREEN;

    /**
     * Attempts to create a new Power Portal after a player activates a lever.
     * @param player The player
     * @param lever The activated lever.
     * @return True if the creation was successful.
     */
    public static boolean attemptCreation (Player player, Block lever) {

        Block[][][] portal;

        if (lever.getType() == Material.LEVER) {
            portal = createPossibleSmallPortal(lever);
        } else {
            return false;
        }

        if(checkPossibleSmallPortal(portal)) {

            Sign sign = (Sign) portal[1][1][2].getState();
            String gateName = sign.getLine(0);

            Block base = portal[0][2][4]; // Block directly below portal
            Block origin = portal[1][2][4]; // Block in front of base block

            // Marking the trigger blocks. If a player enters any trigger blocks, they will be teleported by the portal.
            ArrayList<Block> triggers = new ArrayList<>();
            triggers.add(portal[0][2][1]);
            triggers.add(portal[0][2][2]);
            triggers.add(portal[0][2][3]);

            if(gateName.isEmpty()) {
                player.sendMessage(RED + "[PowerPortals] Possible portal detected, but the sign is blank.");
            } else if (base.isPassable() || origin.isPassable()) {
                player.sendMessage(RED + "[PowerPortals] Possible portal detected, but there's nothing under it.");
            } else if (!checkDistance(origin.getLocation())) {
                player.sendMessage(RED + "[PowerPortals] Possible portal detected, but it's too close to another portal");
            } else if (!checkName(gateName)) {
                player.sendMessage(RED + "[PowerPortals] Possible portal detected, but its name is already taken.");
            } else {
                player.sendMessage(GREEN + "[PowerPortals] Valid gate detected!");

                PowerPortal powerPortal = StorageUtil.createPowerPortal(player, gateName, origin, lever, triggers);

                sign.setLine(0, powerPortal.getPortalName());
                sign.setLine(1, powerPortal.getOwnerName());
                int x = (int)powerPortal.getOrigin().getX();
                int y = (int)powerPortal.getOrigin().getY();
                int z = (int)powerPortal.getOrigin().getZ();
                sign.setLine(2, "(" + x + ", " + y + ", " + z + ")");
                sign.setLine(3, "");
                sign.update();

                // TODO: Figure out how to get signs on portals to face the same direction as the lever.

                player.sendMessage(GREEN + "[PowerPortals] Gate " + powerPortal.getPortalName() +
                        " created at (" + x + ", " + y + ", " + z + ")"
                );
                player.playSound(player.getLocation(), Sound.BLOCK_END_PORTAL_FRAME_FILL, 1, 1);
                return true;
            }
        }
        return false;
    }

    /**
     * Creates a 3D Block array of a possible new small portal.
     * @param lever The portal's lever.
     * @return A Block[][][] representing the possible small portal.
     */
    private static Block[][][] createPossibleSmallPortal (Block lever) {
        /*
         * Looking for a Portal with this pattern:
         *
         *  Plane 0       Plane 1
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
        Block[][][] portal = new Block[2][5][5];
        Block origin;

        if (direction.toString().equals("NORTH")) {

            origin = lever.getRelative(1, -2, 0);

            portal[0][0][3] = origin.getRelative(2, 1, 1);
            portal[0][1][1] = origin.getRelative(1, 3, 1);
            portal[0][1][2] = origin.getRelative(1, 2, 1);
            portal[0][1][3] = origin.getRelative(1, 1, 1);
            portal[0][2][0] = origin.getRelative(0, 4, 1);
            portal[0][2][1] = origin.getRelative(0, 3, 1);
            portal[0][2][2] = origin.getRelative(0, 2, 1);
            portal[0][2][3] = origin.getRelative(0, 1, 1);
            portal[0][2][4] = origin.getRelative(0, 0, 1);
            portal[0][3][1] = origin.getRelative(-1, 3, 1);
            portal[0][3][2] = origin.getRelative(-1, 2, 1);
            portal[0][3][3] = origin.getRelative(-1, 1, 1);
            portal[0][4][3] = origin.getRelative(-2, 1, 1);

            portal[1][1][2] = origin.getRelative(1, 2, 0);
            portal[1][2][1] = origin.getRelative(0, 3, 0);
            portal[1][2][2] = origin.getRelative(0, 2, 0);
            portal[1][2][3] = origin.getRelative(0, 1, 0);
            portal[1][2][4] = origin.getRelative(0, 0, 0);
            portal[1][3][2] = origin.getRelative(-1, 2, 0);

        } else if (direction.toString().equals("SOUTH")) {

            origin = lever.getRelative(-1, -2, 0);

            portal[0][0][3] = origin.getRelative(-2, 1, -1);
            portal[0][1][1] = origin.getRelative(-1, 3, -1);
            portal[0][1][2] = origin.getRelative(-1, 2, -1);
            portal[0][1][3] = origin.getRelative(-1, 1, -1);
            portal[0][2][0] = origin.getRelative(0, 4, -1);
            portal[0][2][1] = origin.getRelative(0, 3, -1);
            portal[0][2][2] = origin.getRelative(0, 2, -1);
            portal[0][2][3] = origin.getRelative(0, 1, -1);
            portal[0][2][4] = origin.getRelative(0, 0, -1);
            portal[0][3][1] = origin.getRelative(1, 3, -1);
            portal[0][3][2] = origin.getRelative(1, 2, -1);
            portal[0][3][3] = origin.getRelative(1, 1, -1);
            portal[0][4][3] = origin.getRelative(2, 1, -1);

            portal[1][1][2] = origin.getRelative(-1, 2, 0);
            portal[1][2][1] = origin.getRelative(0, 3, 0);
            portal[1][2][2] = origin.getRelative(0, 2, 0);
            portal[1][2][3] = origin.getRelative(0, 1, 0);
            portal[1][2][4] = origin.getRelative(0, 0, 0);
            portal[1][3][2] = origin.getRelative(1, 2, 0);

        } else if (direction.toString().equals("EAST")) {

            origin = lever.getRelative(0, -2, 1);

            portal[0][0][3] = origin.getRelative(-1, 1, 2);
            portal[0][1][1] = origin.getRelative(-1, 3, 1);
            portal[0][1][2] = origin.getRelative(-1, 2, 1);
            portal[0][1][3] = origin.getRelative(-1, 1, 1);
            portal[0][2][0] = origin.getRelative(-1, 4, 0);
            portal[0][2][1] = origin.getRelative(-1, 3, 0);
            portal[0][2][2] = origin.getRelative(-1, 2, 0);
            portal[0][2][3] = origin.getRelative(-1, 1, 0);
            portal[0][2][4] = origin.getRelative(-1, 0, 0);
            portal[0][3][1] = origin.getRelative(-1, 3, 1);
            portal[0][3][2] = origin.getRelative(-1, 2, 1);
            portal[0][3][3] = origin.getRelative(-1, 1, 1);
            portal[0][4][3] = origin.getRelative(-1, 1, 2);

            portal[1][1][2] = origin.getRelative(0, 2, 1);
            portal[1][2][1] = origin.getRelative(0, 3, 0);
            portal[1][2][2] = origin.getRelative(0, 2, 0);
            portal[1][2][3] = origin.getRelative(0, 1, 0);
            portal[1][2][4] = origin.getRelative(0, 0, 0);
            portal[1][3][2] = origin.getRelative(0, 2, -1);

        } else if (direction.toString().equals("WEST")) {

            origin = lever.getRelative(0, -2, -1);

            portal[0][0][3] = origin.getRelative(1, 1, -2);
            portal[0][1][1] = origin.getRelative(1, 3, -1);
            portal[0][1][2] = origin.getRelative(1, 2, -1);
            portal[0][1][3] = origin.getRelative(1, 1, -1);
            portal[0][2][0] = origin.getRelative(1, 4, 0);
            portal[0][2][1] = origin.getRelative(1, 3, 0);
            portal[0][2][2] = origin.getRelative(1, 2, 0);
            portal[0][2][3] = origin.getRelative(1, 1, 0);
            portal[0][2][4] = origin.getRelative(1, 0, 0);
            portal[0][3][1] = origin.getRelative(1, 3, -1);
            portal[0][3][2] = origin.getRelative(1, 2, -1);
            portal[0][3][3] = origin.getRelative(1, 1, -1);
            portal[0][4][3] = origin.getRelative(1, 1, -2);

            portal[1][1][2] = origin.getRelative(0, 2, -1);
            portal[1][2][1] = origin.getRelative(0, 3, 0);
            portal[1][2][2] = origin.getRelative(0, 2, 0);
            portal[1][2][3] = origin.getRelative(0, 1, 0);
            portal[1][2][4] = origin.getRelative(0, 0, 0);
            portal[1][3][2] = origin.getRelative(0, 2, 1);

        }
        return portal;
    }

    /**
     * Checks a possible small portal to see if the blocks are placed in valid locations.
     * @param portal A Block[][][] representing a possible small portal.
     * @return True if the portal is valid.
     */
    private static boolean checkPossibleSmallPortal(Block[][][] portal) {
        return  portal[0][0][3].getType() == Material.OBSIDIAN     &&
                portal[0][1][1].getType() == Material.OBSIDIAN     &&
                portal[0][1][2].getType() == Material.OBSIDIAN     &&
                portal[0][1][3].getType() == Material.OBSIDIAN     &&
                portal[0][2][0].getType() == Material.OBSIDIAN     &&
                portal[0][2][1].getType() == Material.AIR          &&
                portal[0][2][2].getType() == Material.AIR          &&
                portal[0][2][3].getType() == Material.AIR          &&
                // !portal[0][2][4].isPassable()                   && // This gets checked later in AttemptCreation
                portal[0][3][1].getType() == Material.OBSIDIAN     &&
                portal[0][3][2].getType() == Material.OBSIDIAN     &&
                portal[0][3][3].getType() == Material.OBSIDIAN     &&
                portal[0][4][3].getType() == Material.OBSIDIAN     &&

                portal[1][1][2].getBlockData() instanceof WallSign && // Sign can be any kind of wall sign
                portal[1][2][1].getType() == Material.AIR          &&
                portal[1][2][2].getType() == Material.AIR          &&
                portal[1][2][3].getType() == Material.AIR          &&
                // !portal[1][2][4].isPassable()                   && // This gets checked later in AttemptCreation
                portal[1][3][2].getType() == Material.LEVER;
    }

    /**
     * Checks if an origin of a portal is too close to any of the other portals.
     * @param origin The origin of the portal.
     * @return True if the origin is far enough away from the other portals.
     */
    private static boolean checkDistance(Location origin) {
        for (PowerPortal portal: StorageUtil.getPortals()) {
            if (origin.distance(portal.getOrigin()) < 1) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if a portal name is already taken by another portal.
     * @param portalName The portal name.
     * @return True if the name is not taken.
     */
    private static boolean checkName(String portalName) {
        for (PowerPortal portal: StorageUtil.getPortals()) {
            if (portal.getPortalName().equals(portalName)) {
                return false;
            }
        }
        return true;
    }
}
