package io.github.nsdigirolamo.powerportals.utilities;

import io.github.nsdigirolamo.powerportals.structures.PowerPortal;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.block.data.type.Switch;
import org.bukkit.block.data.type.WallSign;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class LargePortalCreationUtility {

    private static final Material[][][] LARGE_PORTAL =
    {
        {
            {   // x = 0, y = 0, z = 0 - 6
                Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
            },
            {   // x = 0, y = 1, z = 0 - 6
                Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.LEVER
            },
            {   // x = 0, y = 2, z = 0 - 6
                Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
            },
            {   // x = 0, y = 3, z = 0 - 6
                Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
            },
            {   // x = 0, y = 4, z = 0 - 6
                Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
            },
            {   // x = 0, y = 5, z = 0 - 6
                Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
            },
            {   // x = 0, y = 6, z = 0 - 6
                Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
            }
        },
        {
            {   // x = 1, y = 0, z = 0 - 6
                Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.OBSIDIAN
            },
            {   // x = 1, y = 1, z = 0 - 6
                Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.OBSIDIAN
            },
            {   // x = 1, y = 2, z = 0 - 6
                Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
            },
            {   // x = 1, y = 3, z = 0 - 6
                Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
            },
            {   // x = 1, y = 4, z = 0 - 6
                Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
            },
            {   // x = 1, y = 5, z = 0 - 6
                Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
            },
            {   // x = 1, y = 6, z = 0 - 6
                Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
            },
        },
        {
            {   // x = 2, y = 0, z = 0 - 6
                Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
            },
            {   // x = 2, y = 1, z = 0 - 6
                Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
            },
            {   // x = 2, y = 2, z = 0 - 6
                Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
            },
            {   // x = 2, y = 3, z = 0 - 6
                Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
            },
            {   // x = 2, y = 4, z = 0 - 6
                Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
            },
            {   // x = 2, y = 5, z = 0 - 6
                Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
            },
            {   // x = 2, y = 6, z = 0 - 6
                Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
            },
        },
        {
            {   // x = 3, y = 0, z = 0 - 6
                Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
            },
            {   // x = 3, y = 1, z = 0 - 6
                Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
            },
            {   // x = 3, y = 2, z = 0 - 6
                Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
            },
            {   // x = 3, y = 3, z = 0 - 6
                Material.OAK_WALL_SIGN, Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
            },
            {   // x = 3, y = 4, z = 0 - 6
                Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
            },
            {   // x = 3, y = 5, z = 0 - 6
                Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
            },
            {   // x = 3, y = 6, z = 0 - 6
                Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR
            },
        },
        {
            {   // x = 4, y = 0, z = 0 - 6
                Material.AIR, Material.AIR, Material.OBSIDIAN, Material.OBSIDIAN, Material.OBSIDIAN, Material.AIR, Material.AIR
            },
            {   // x = 4, y = 1, z = 0 - 6
                Material.AIR, Material.OBSIDIAN, Material.AIR, Material.AIR, Material.AIR, Material.OBSIDIAN, Material.AIR
            },
            {   // x = 4, y = 2, z = 0 - 6
                Material.OBSIDIAN, Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.OBSIDIAN
            },
            {   // x = 4, y = 3, z = 0 - 6
                Material.OBSIDIAN, Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.OBSIDIAN
            },
            {   // x = 4, y = 4, z = 0 - 6
                Material.OBSIDIAN, Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.OBSIDIAN
            },
            {   // x = 4, y = 5, z = 0 - 6
                Material.AIR, Material.OBSIDIAN, Material.AIR, Material.AIR, Material.AIR, Material.OBSIDIAN, Material.AIR
            },
            {   // x = 4, y = 6, z = 0 - 6
                Material.AIR, Material.AIR, Material.OBSIDIAN, Material.OBSIDIAN, Material.OBSIDIAN, Material.AIR, Material.AIR
            },
        }
    };

    /**
     * Generates a 3D array of blocks representing a possible large PowerPortal in the game world.
     * @param direction the direction the portal is facing
     * @param lever the lever of the portal
     * @return a 3D array of blocks
     */
    private static Block[][][] generatePossiblePortal (BlockFace direction, Block lever) {

        Block[][][] possiblePortal;
        World world = lever.getWorld();
        possiblePortal = new Block[5][7][7];
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
                location = new Location(world, lever.getX() + 6, lever.getY() - 1, lever.getZ());
                modX = -1;
            } else {
                location = new Location(world, lever.getX() - 6, lever.getY() - 1, lever.getZ());
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
                location = new Location(world, lever.getX(), lever.getY() - 1, lever.getZ() + 6);
                modX = -1;
                modZ = -1;
            } else {
                location = new Location(world, lever.getX(), lever.getY() - 1, lever.getZ() - 6);
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
     * Attempt to create a large PowerPortal with the given lever.
     * @param lever the lever of the new portal
     * @return a new PowerPortal if one was detected, null if otherwise
     */
    public static PowerPortal attemptCreation (Player player, Block lever) {
        if (lever.getType().equals(Material.LEVER)) {

            BlockFace direction = ((Switch) lever.getBlockData()).getFacing();
            Location location;
            World world = lever.getWorld();

            if (direction.equals(BlockFace.NORTH)) {
                location = new Location(world, lever.getX() + 3.5, lever.getY() - 1.0, lever.getZ() - 0.5, -180, 0);
            } else if (direction.equals(BlockFace.SOUTH)) {
                location = new Location(world, lever.getX() - 2.5, lever.getY() - 1.0, lever.getZ() - 0.5, 0, 0);
            } else if (direction.equals(BlockFace.EAST)) {
                location = new Location(world, lever.getX() - 0.5, lever.getY() - 1.0, lever.getZ() + 3.5, -90, 0);
            } else {
                location = new Location(world, lever.getX() + 1.5, lever.getY() - 1.0, lever.getZ() - 2.5, 90, 0);
            }

            Block[][][] possiblePortal = generatePossiblePortal(direction, lever);

            boolean isPortal = true;

            // loop to check that every block in the portal array matches the respective block in the blueprint
            topLoop:
            for (int i = 0; i < possiblePortal.length; i++) {
                for (int j = 0; j < possiblePortal[i].length; j++) {
                    for (int k = 0; k < possiblePortal[i][j].length; k++) {
                        if (!LARGE_PORTAL[i][j][k].equals(possiblePortal[i][j][k].getType())) {
                            // this statement checks for any type of wall sign instead of just oak
                            if (!(LARGE_PORTAL[i][j][k].equals(Material.OAK_WALL_SIGN) &&
                                    possiblePortal[i][j][k].getBlockData() instanceof WallSign)) {

                                isPortal = false;
                                break topLoop;

                            }
                        }
                    }
                }
            }

            if (isPortal) {

                String name = ((Sign) possiblePortal[3][3][0].getState()).getLine(0);

                if (!name.matches("[\\w]+")) {

                    throw new IllegalArgumentException("[ P² ] A PowerPortal's name can only be letters and numbers and underscores.");

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
     * Creates a large portal with the given arguments.
     * @param location the location of the new portal
     * @param owner the owner of the new portal
     * @param portal the structure blocks of the new portal
     * @return the new portal
     */
    private static PowerPortal create (Location location, Player owner, Block[][][] portal) {

        ArrayList<Block> structure = new ArrayList<>();
        Block[] triggers = {
                                 portal[4][1][2], portal[4][1][3], portal[4][1][4],
                portal[4][2][1], portal[4][2][2], portal[4][2][3], portal[4][2][4], portal[4][2][5],
                portal[4][3][1], portal[4][3][2], portal[4][3][3], portal[4][3][4], portal[4][3][5],
                portal[4][4][1], portal[4][4][2], portal[4][4][3], portal[4][4][4], portal[4][4][5],
                                 portal[4][5][2], portal[4][5][3], portal[4][5][4],
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
