package io.github.nsdigirolamo.powerportals.utils;

import io.github.nsdigirolamo.powerportals.structures.PowerPortal;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Switch;
import org.bukkit.block.data.type.WallSign;
import org.bukkit.entity.Player;

/**
 * DefaultDesignUtil is a utility in charge of checking the portal designs if customPortalDesigns config is false.
 * Default PowerPortal designs are hardcoded into this class so the WorldEdit API is not needed.
 */
public class DefaultDesignUtil {

    /**
     * Attempts to create a new small default PowerPortal owned by the given player.
     *
     * @param owner The owner of the new PowerPortal.
     * @param lever The lever of the new PowerPortal.
     * @return The new PowerPortal if a valid structure was found in the game world. Null if not.
     */
    public static PowerPortal attemptSmallDefaultPortal(Player owner, Block lever) {

        Block[] portalBlocks = new Block[17];
        int[] triggerIndexes = new int[3];
        int[] clearIndexes = new int[5];
        int originIndex;
        int leverIndex;
        int signIndex;

        if (lever.getType().equals(Material.LEVER) && checkSmallDefaultPortal(lever)) {

            BlockFace direction = ((Switch) lever.getBlockData()).getFacing();

            int xMod = 1;
            int zMod = 1;

            if (direction.equals(BlockFace.NORTH) || direction.equals(BlockFace.SOUTH)) {

                if (direction.equals(BlockFace.SOUTH)) {
                    xMod = -1;
                    zMod = -1;
                }

                portalBlocks[0]  = lever.getRelative( 1 * xMod, -2, 0 * zMod); // origin

                portalBlocks[1]  = lever.getRelative( 1 * xMod, -1, 0 * zMod); // clear block

                portalBlocks[2]  = lever.getRelative( 2 * xMod,  0, 0 * zMod); // wall sign
                portalBlocks[3]  = lever.getRelative( 1 * xMod,  0, 0 * zMod); // clear block
                portalBlocks[4]  = lever.getRelative( 0 * xMod,  0, 0 * zMod); // lever

                portalBlocks[5]  = lever.getRelative( 3 * xMod, -1, 1 * zMod); // obsidian
                portalBlocks[6]  = lever.getRelative( 2 * xMod, -1, 1 * zMod); // obsidian
                portalBlocks[7]  = lever.getRelative( 1 * xMod, -1, 1 * zMod); // trigger block (and clear block)
                portalBlocks[8]  = lever.getRelative( 0 * xMod, -1, 1 * zMod); // obsidian
                portalBlocks[9]  = lever.getRelative(-1 * xMod, -1, 1 * zMod); // obsidian

                portalBlocks[10] = lever.getRelative( 2 * xMod,  0, 1 * zMod); // obsidian
                portalBlocks[11] = lever.getRelative( 1 * xMod,  0, 1 * zMod); // trigger block (and clear block)
                portalBlocks[12] = lever.getRelative( 0 * xMod,  0, 1 * zMod); // obsidian

                portalBlocks[13] = lever.getRelative( 2 * xMod,  1, 1 * zMod); // obsidian
                portalBlocks[14] = lever.getRelative( 1 * xMod,  1, 1 * zMod); // trigger block (and clear block)
                portalBlocks[15] = lever.getRelative( 0 * xMod,  1, 1 * zMod); // obsidian

                portalBlocks[16] = lever.getRelative( 1 * xMod,  2, 1 * zMod); // obsidian

            } else if (direction.equals(BlockFace.EAST) || direction.equals(BlockFace.WEST)) {

                if (direction.equals(BlockFace.WEST)) {
                    xMod = -1;
                    zMod = -1;
                }

                portalBlocks[0]  = lever.getRelative( 0 * xMod, -2,  1 * zMod); // origin

                portalBlocks[1]  = lever.getRelative( 0 * xMod, -1,  1 * zMod); // clear block

                portalBlocks[2]  = lever.getRelative( 0 * xMod,  0,  2 * zMod); // wall sign
                portalBlocks[3]  = lever.getRelative( 0 * xMod,  0,  1 * zMod); // clear block
                portalBlocks[4]  = lever.getRelative( 0 * xMod,  0,  0 * zMod); // lever

                portalBlocks[5]  = lever.getRelative(-1 * xMod, -1,  3 * zMod); // obsidian
                portalBlocks[6]  = lever.getRelative(-1 * xMod, -1,  2 * zMod); // obsidian
                portalBlocks[7]  = lever.getRelative(-1 * xMod, -1,  1 * zMod); // trigger block (and clear block)
                portalBlocks[8]  = lever.getRelative(-1 * xMod, -1,  0 * zMod); // obsidian
                portalBlocks[9]  = lever.getRelative(-1 * xMod, -1, -1 * zMod); // obsidian

                portalBlocks[10] = lever.getRelative(-1 * xMod,  0,  2 * zMod); // obsidian
                portalBlocks[11] = lever.getRelative(-1 * xMod,  0,  1 * zMod); // trigger block (and clear block)
                portalBlocks[12] = lever.getRelative(-1 * xMod,  0,  0 * zMod); // obsidian

                portalBlocks[13] = lever.getRelative(-1 * xMod,  1,  2 * zMod); // obsidian
                portalBlocks[14] = lever.getRelative(-1 * xMod,  1,  1 * zMod); // trigger block (and clear block)
                portalBlocks[15] = lever.getRelative(-1 * xMod,  1,  0 * zMod); // obsidian

                portalBlocks[16] = lever.getRelative(-1 * xMod,  2,  1 * zMod); // obsidian

            }

            triggerIndexes[0] = 7;
            triggerIndexes[1] = 11;
            triggerIndexes[2] = 14;

            clearIndexes[0] = 1;
            clearIndexes[1] = 3;
            clearIndexes[2] = 7;
            clearIndexes[3] = 11;
            clearIndexes[4] = 14;

            originIndex = 0;
            leverIndex = 4;
            signIndex = 2;

            return PortalCreationUtil.createPortal(owner, portalBlocks, triggerIndexes, clearIndexes, originIndex, leverIndex, signIndex);
        }
        return null;
    }

    /**
     * Checks for a small default PowerPortal around the given lever.
     *
     * @param lever The lever of the possible small default PowerPortal.
     * @return true if a small default PowerPortal is found.
     */
    public static boolean checkSmallDefaultPortal (Block lever) {

        if (lever.getType().equals(Material.LEVER)) {

            BlockFace direction = ((Switch) lever.getBlockData()).getFacing();

            int xMod = 1;
            int zMod = 1;

            if (direction.equals(BlockFace.NORTH) || direction.equals(BlockFace.SOUTH)) {

                if (direction.equals(BlockFace.SOUTH)) {
                    xMod = -1;
                    zMod = -1;
                }

                return  lever.getRelative( 2 * xMod,  0, 0 * zMod).getBlockData() instanceof WallSign &&
                        lever.getRelative( 3 * xMod, -1, 1 * zMod).getType().equals(Material.OBSIDIAN) &&
                        lever.getRelative( 2 * xMod, -1, 1 * zMod).getType().equals(Material.OBSIDIAN) &&
                        lever.getRelative( 0 * xMod, -1, 1 * zMod).getType().equals(Material.OBSIDIAN) &&
                        lever.getRelative(-1 * xMod, -1, 1 * zMod).getType().equals(Material.OBSIDIAN) &&
                        lever.getRelative( 2 * xMod,  0, 1 * zMod).getType().equals(Material.OBSIDIAN) &&
                        lever.getRelative( 0 * xMod,  0, 1 * zMod).getType().equals(Material.OBSIDIAN) &&
                        lever.getRelative( 2 * xMod,  1, 1 * zMod).getType().equals(Material.OBSIDIAN) &&
                        lever.getRelative( 0 * xMod,  1, 1 * zMod).getType().equals(Material.OBSIDIAN) &&
                        lever.getRelative( 1 * xMod,  2, 1 * zMod).getType().equals(Material.OBSIDIAN);

            } else if (direction.equals(BlockFace.EAST) || direction.equals(BlockFace.WEST)) {

                if (direction.equals(BlockFace.WEST)) {
                    xMod = -1;
                    zMod = -1;
                }

                return  lever.getRelative( 0 * xMod,  0,  2 * zMod).getBlockData() instanceof WallSign &&
                        lever.getRelative(-1 * xMod, -1,  3 * zMod).getType().equals(Material.OBSIDIAN) &&
                        lever.getRelative(-1 * xMod, -1,  2 * zMod).getType().equals(Material.OBSIDIAN) &&
                        lever.getRelative(-1 * xMod, -1,  0 * zMod).getType().equals(Material.OBSIDIAN) &&
                        lever.getRelative(-1 * xMod, -1, -1 * zMod).getType().equals(Material.OBSIDIAN) &&
                        lever.getRelative(-1 * xMod,  0,  2 * zMod).getType().equals(Material.OBSIDIAN) &&
                        lever.getRelative(-1 * xMod,  0,  0 * zMod).getType().equals(Material.OBSIDIAN) &&
                        lever.getRelative(-1 * xMod,  1,  2 * zMod).getType().equals(Material.OBSIDIAN) &&
                        lever.getRelative(-1 * xMod,  1,  0 * zMod).getType().equals(Material.OBSIDIAN) &&
                        lever.getRelative(-1 * xMod,  2,  1 * zMod).getType().equals(Material.OBSIDIAN);
            }
        }
        return false;
    }
}
