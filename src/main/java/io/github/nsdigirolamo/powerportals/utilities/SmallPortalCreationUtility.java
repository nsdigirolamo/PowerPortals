package io.github.nsdigirolamo.powerportals.utilities;

import io.github.nsdigirolamo.powerportals.structures.PowerPortal;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.block.data.type.Switch;
import org.bukkit.block.data.type.WallSign;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class SmallPortalCreationUtility {

    private static final Material[][][] SMALL_PORTAL =
    {
        {
            {   // x = 0, y = 0, z = 0 - 4
                Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
            },
            {   // x = 0, y = 1, z = 0 - 4
                Material.AIR, Material.OAK_WALL_SIGN, Material.AIR, Material.LEVER, Material.AIR
            },
            {   // x = 0, y = 2, z = 0 - 4
                Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
            },
            {   // x = 0, y = 3, z = 0 - 4
                Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
            },
        },
        {
            {   // x = 1, y = 0, z = 0 - 4
                Material.OBSIDIAN, Material.OBSIDIAN, Material.AIR, Material.OBSIDIAN, Material.OBSIDIAN
            },
            {   // x = 1, y = 1, z = 0 - 4
                Material.AIR, Material.OBSIDIAN, Material.AIR, Material.OBSIDIAN, Material.AIR
            },
            {   // x = 1, y = 2, z = 0 - 4
                Material.AIR, Material.OBSIDIAN, Material.AIR, Material.OBSIDIAN, Material.AIR
            },
            {   // x = 1, y = 3, z = 0 - 4
                Material.AIR, Material.AIR, Material.OBSIDIAN, Material.AIR, Material.AIR
            },
        }
    };

    /**
     * Generates a 3D array of blocks representing a possible small PowerPortal in the game world.
     * @param direction the direction the portal is facing
     * @param lever the lever of the portal
     * @return a 3D array of blocks
     */
    private static Block[][][] generatePossiblePortal (BlockFace direction, Block lever) {

        Block[][][] possiblePortal;
        World world = lever.getWorld();
        possiblePortal = new Block[2][4][5];
        Location location;

        int modX = 1;
        int modZ = 1;

        /*
        TODO: Condense these if statements and for loops
        These if statements and for loops are a massive pain. Essentially a 2x4x5 array is generated in the world and
        the for loops fill that array with the proper blocks for the given orientation. There is a way to do all this
        in one loop with one set of if statements, but I don't know how.
         */

        if (direction.equals(BlockFace.NORTH) || direction.equals(BlockFace.SOUTH)) {

            if (direction.equals(BlockFace.NORTH)) {
                location = new Location(world, lever.getX() + 3, lever.getY() - 1, lever.getZ());
                modX = -1;
            } else {
                location = new Location(world, lever.getX() - 3, lever.getY() - 1, lever.getZ());
                modZ = -1;
            }

            for (int i = 0; i < possiblePortal.length; i++) {
                for (int j = 0; j < possiblePortal[0].length; j++) {
                    for (int k = 0; k < possiblePortal[0][0].length; k++) {
                        possiblePortal[i][j][k] = world.getBlockAt(
                                location.getBlockX() + k * modX,
                                location.getBlockY() + j,
                                location.getBlockZ() + i * modZ);
                    }
                }
            }

        } else if (direction.equals(BlockFace.EAST) || direction.equals(BlockFace.WEST)) {

            if (direction.equals(BlockFace.EAST)) {
                location = new Location(world, lever.getX(), lever.getY() - 1, lever.getZ() + 3);
                modX = -1;
                modZ = -1;
            } else {
                location = new Location(world, lever.getX(), lever.getY() - 1, lever.getZ() - 3);
            }

            for (int i = 0; i < possiblePortal.length; i++) {
                for (int j = 0; j < possiblePortal[0].length; j++) {
                    for (int k = 0; k < possiblePortal[0][0].length; k++) {
                        possiblePortal[i][j][k] = world.getBlockAt(
                                location.getBlockX() + i * modX,
                                location.getBlockY() + j,
                                location.getBlockZ() + k * modZ);
                    }
                }
            }

        }

        return possiblePortal;
    }

    /**
     * Attempt to create a small PowerPortal with the given lever.
     * @param lever the lever of the new portal
     * @return a new PowerPortal if one was detected, null if otherwise
     */
    public static PowerPortal attemptCreation (Player player, Block lever) {
        if (lever.getType().equals(Material.LEVER)) {

            BlockFace direction = ((Switch) lever.getBlockData()).getFacing();
            Location location = null;
            World world = lever.getWorld();

            if (direction.equals(BlockFace.NORTH)) {
                location = new Location(world, lever.getX() + 1.5, lever.getY() - 1.0, lever.getZ() + 0.5, -180, 0);
            } else if (direction.equals(BlockFace.SOUTH)) {
                location = new Location(world, lever.getX() - 0.5, lever.getY() - 1.0, lever.getZ() + 0.5, 0, 0);
            } else if (direction.equals(BlockFace.EAST)) {
                location = new Location(world, lever.getX() + 0.5, lever.getY() - 1.0, lever.getZ() + 1.5, -90, 0);
            } else {
                location = new Location(world, lever.getX() + 0.5, lever.getY() - 1.0, lever.getZ() - 0.5, 90, 0);
            }

            Block[][][] possiblePortal = generatePossiblePortal(direction, lever);

            boolean isPortal = true;

            // loop to check that every block in the portal array matches the respective block in the blueprint
            topLoop:
            for (int i = 0; i < possiblePortal.length; i++) {
                for (int j = 0; j < possiblePortal[i].length; j++) {
                    for (int k = 0; k < possiblePortal[i][j].length; k++) {
                        if (!SMALL_PORTAL[i][j][k].equals(possiblePortal[i][j][k].getType())) {
                            // this statement checks for any type of wall sign instead of just oak
                            if (!(SMALL_PORTAL[i][j][k].equals(Material.OAK_WALL_SIGN) &&
                                    possiblePortal[i][j][k].getBlockData() instanceof WallSign)) {

                                isPortal = false;
                                break topLoop;

                            }
                        }
                    }
                }
            }

            if (isPortal) {

                String name = ((Sign) possiblePortal[0][1][1].getState()).getLine(0);

                if (!name.matches("[a-zA-Z0-9]+")) {

                    throw new IllegalArgumentException("[ P² ] A PowerPortal's name can only be letters and numbers.");

                } else if (StorageUtility.findPortal(name) != null) {

                    throw new IllegalArgumentException("[ P² ] That PowerPortal's name already exists.");

                } else {

                    return create(location, player, possiblePortal);

                }
            } else {

                return null;

            }
        } else {

            return null;

        }
    }

    /**
     * Creates a small portal with the given arguments.
     * @param location the location of the new portal
     * @param owner the owner of the new portal
     * @param portal the structure blocks of the new portal
     * @return the new portal
     */
    private static PowerPortal create (Location location, Player owner, Block[][][] portal) {

        ArrayList<Block> structure = new ArrayList<Block>();
        Block[] triggers = {
                portal[1][0][2],
                portal[1][1][2],
                portal[1][2][2]
        };
        String name = "unnamed";

        for (Block[][] xPlane : portal) {
            for (Block[] yPlane : xPlane) {
                for (Block block: yPlane) {
                    if (!block.getType().equals(Material.AIR)) {
                        structure.add(block);
                        if (block.getState() instanceof Sign) {
                            Sign sign = (Sign) block.getState();
                            sign.setLine(1, owner.getName());
                            sign.update();
                            name = sign.getLine(0);
                        }
                    }
                }
            }
        }

        return new PowerPortal(name, location, owner, structure.toArray(new Block[0]), triggers);
    }

}
