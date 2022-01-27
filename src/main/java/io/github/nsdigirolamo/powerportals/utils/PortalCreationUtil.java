package io.github.nsdigirolamo.powerportals.utils;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.world.block.BaseBlock;
import io.github.nsdigirolamo.powerportals.PowerPortals;
import io.github.nsdigirolamo.powerportals.structures.PowerPortal;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Switch;
import org.bukkit.block.data.type.WallSign;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * PortalCreationUtil is a utility that handles the creation of PowerPortals and storage of their design schematics.
 */
public class PortalCreationUtil {

    private static ArrayList<Clipboard> designs = new ArrayList<>();

    /**
     * Loads portal design schematics from the portaldesigns directory.
     */
    public static void loadDesigns () {

        File portalDesignsDir = new File(PowerPortals.getPlugin().getDataFolder().getAbsolutePath() + "/portaldesigns");
        File[] portalDesigns = portalDesignsDir.listFiles();

        if (portalDesigns != null) {

            for (File design: portalDesigns) {

                Bukkit.getLogger().info("[PowerPortals] Reading file " + design.getAbsolutePath());
                Clipboard clipboard = null;

                try {

                    ClipboardFormat format = ClipboardFormats.findByFile(design);
                    ClipboardReader reader = format.getReader(new FileInputStream(design));
                    clipboard = reader.read();

                } catch (IOException e) {

                    e.printStackTrace();

                }

                if (checkComponents(clipboard, design)) {
                    designs.add(clipboard);
                }
            }
        } else {
            Bukkit.getLogger().warning("No portaldesigns directory found. Cannot load portal designs.");
        }
    }

    /**
     * Creates a portal, depending on the stored portal designs and if a valid design exists at the lever's location.
     * @param owner The owner of the new portal.
     * @param lever The lever of the new portal.
     * @return The new portal, or null if a portal was not able to be created.
     */
    public static PowerPortal createPortal (Player owner, Block lever) {

        if (!lever.getType().equals(Material.LEVER)) {
            throw new IllegalArgumentException("lever must be a lever block");
        }

        for (Clipboard clipboard : designs) {

            Switch leverSwitch = (Switch) lever.getBlockData();
            BlockFace direction = leverSwitch.getFacing();

            PowerPortal portal = checkClipboard(clipboard, owner, lever, direction);

            if (portal != null) {

                owner.sendMessage(ChatColor.GREEN + "[PowerPortals] Created a new portal for " + portal.getOwner().getName() + " with name \"" + portal.getName() + "\" at (" + portal.getX() + ", " + portal.getY() + ", " + portal.getZ() + ")");
                owner.playSound(owner.getLocation(), Sound.BLOCK_END_PORTAL_FRAME_FILL, 1, 1);

                PortalStorageUtil.storePortal(portal);

                Bukkit.getLogger().info("[PowerPortals] " + portal.getOwner().getName() +
                        " created a new portal named \"" + portal.getName() + "\" at (" + portal.getX() +
                        ", " + portal.getY() + ", " + portal.getZ() + ")");

                return portal;
            }

        }
        return null;
    }

    /**
     * Checks to make sure a given portal design schematic has all the proper elements of a portal design. A proper
     * portal design has at least one trigger block (blue stained glass), exactly one lever, exactly one bedrock, and
     * exactly one wall sign.
     * @param clipboard The portal design as a clipboard.
     * @param file The portal design schematic file.
     * @return True if the given portal design has all the necessary components.
     */
    private static boolean checkComponents(Clipboard clipboard, File file) {

        int minX = clipboard.getMinimumPoint().getBlockX();
        int minY = clipboard.getMinimumPoint().getBlockY();
        int minZ = clipboard.getMinimumPoint().getBlockZ();

        int maxX = clipboard.getMaximumPoint().getBlockX();
        int maxY = clipboard.getMaximumPoint().getBlockY();
        int maxZ = clipboard.getMaximumPoint().getBlockZ();

        int triggerCount = 0;
        int leverCount = 0;
        int bedrockCount = 0;
        int signCount = 0;

        for (int x = minX; x < maxX + 1; x++) {
            for (int y = minY; y < maxY + 1; y++) {
                for (int z = minZ; z < maxZ + 1; z++) {

                    BaseBlock block = clipboard.getFullBlock(BlockVector3.at(x, y, z));
                    Material blockMat = BukkitAdapter.adapt(block).getMaterial();

                    if (blockMat.equals(Material.BLUE_STAINED_GLASS)) {
                        triggerCount++;
                    } else if (blockMat.equals(Material.LEVER)) {
                        leverCount++;
                    } else if (blockMat.equals(Material.BEDROCK)) {
                        bedrockCount++;
                    } else if (BukkitAdapter.adapt(block) instanceof WallSign) {
                        signCount++;
                    }
                }
            }
        }

        if (triggerCount < 1) {
            Bukkit.getLogger().warning("Could not load " + file.getName() + " because it does not have at least one trigger block");
            return false;
        } else if (leverCount != 1) {
            Bukkit.getLogger().warning("Could not load " + file.getName() + " because it does not have exactly one lever");
            return false;
        } else if (bedrockCount != 1) {
            Bukkit.getLogger().warning("Could not load " + file.getName() + " because it does not have exactly one bedrock");
            return false;
        } else if (signCount != 1) {
            Bukkit.getLogger().warning("Could not load " + file.getName() + " because it does not have exactly one wall sign");
            return false;
        } else {
            return true;
        }
    }

    /**
     * Checks a portal design's clipboard against an in-game location to see if the blocks of that location match up
     * with the BlockData stored in the clipboard. If all the blocks in the in-game location match the clipboard,
     * it means a portal exists at that location in the game.
     * @param clipboard The portal design's clipboard.
     * @param owner The owner of the portal.
     * @param lever The lever of the portal in the game world.
     * @param direction The direction the lever is facing.
     * @return A new PowerPortal if the blocks in the game world are valid.
     */
    private static PowerPortal checkClipboard (Clipboard clipboard, Player owner, Block lever, BlockFace direction) {

        PowerPortal portal;

        int minX = clipboard.getMinimumPoint().getBlockX();
        int minY = clipboard.getMinimumPoint().getBlockY();
        int minZ = clipboard.getMinimumPoint().getBlockZ();

        int maxX = clipboard.getMaximumPoint().getBlockX();
        int maxY = clipboard.getMaximumPoint().getBlockY();
        int maxZ = clipboard.getMaximumPoint().getBlockZ();

        int[] clipboardLever = new int[3];

        for (int x = minX; x < maxX + 1; x++) {
            for (int y = minY; y < maxY + 1; y++) {
                for (int z = minZ; z < maxZ + 1; z++) {
                    BaseBlock block = clipboard.getFullBlock(BlockVector3.at(x, y, z));
                    Material blockMat = BukkitAdapter.adapt(block).getMaterial();
                    if (blockMat.equals(Material.LEVER)) {
                        clipboardLever[0] = x;
                        clipboardLever[1] = y;
                        clipboardLever[2] = z;
                    }
                }
            }
        }

        ArrayList<Block> portalBlocks = new ArrayList<>();
        ArrayList<Integer> triggerIndexes = new ArrayList<>();
        ArrayList<Integer> clearIndexes = new ArrayList<>();
        int originIndex = -1;
        int leverIndex = -1;
        int signIndex = -1;

        boolean match = true;

        /*
         * This section of code makes sure each block in the game matches the relative block stored in the clipboard.
         * It also stores the blocks if they match properly.
         */
        checkloop:
        for (int x = minX; x < maxX + 1; x++) {
            for (int y = minY; y < maxY + 1; y++) {
                for (int z = minZ; z < maxZ + 1; z++) {

                    BlockData clipboardData = BukkitAdapter.adapt(clipboard.getFullBlock(BlockVector3.at(x, y, z)));

                    int relativeX = x - clipboardLever[0];
                    int relativeZ = z - clipboardLever[2];
                    int relativeY = y - clipboardLever[1];

                    if(direction.toString().equals("NORTH")) {

                        int placeholder = relativeZ;
                        relativeZ = relativeX;
                        relativeX = -1 * placeholder;

                    } else if (direction.toString().equals("SOUTH")) {

                        int placeholder = relativeZ;
                        relativeZ = -1 * relativeX;
                        relativeX = placeholder;

                    } else if (direction.toString().equals("EAST")) {

                        relativeZ = -1 * relativeZ;
                        relativeX = -1 * relativeX;

                    } // west needs no adjustment

                    Block relativeBlock = lever.getRelative(relativeX, relativeY, relativeZ);

                    boolean isIgnoredBlock = clipboardData.getMaterial().equals(Material.AIR) ||
                            clipboardData.getMaterial().equals(Material.BEDROCK) ||
                            clipboardData.getMaterial().equals(Material.BLUE_STAINED_GLASS) ||
                            clipboardData.getMaterial().equals(Material.WHITE_STAINED_GLASS);

                    if (!isIgnoredBlock) {
                        if (clipboardData.getMaterial().equals(relativeBlock.getType())) {

                            portalBlocks.add(relativeBlock);

                            if (relativeBlock.getBlockData() instanceof WallSign) {

                                signIndex = portalBlocks.indexOf(relativeBlock);

                            } else if (relativeBlock.getType().equals(Material.LEVER)) {

                                leverIndex = portalBlocks.indexOf(relativeBlock);

                            }
                        } else {

                            match = false;
                            break checkloop;

                        }
                    } else if (clipboardData.getMaterial().equals(Material.BEDROCK)) {

                        portalBlocks.add(relativeBlock);
                        originIndex = portalBlocks.indexOf(relativeBlock);

                    } else if (clipboardData.getMaterial().equals(Material.BLUE_STAINED_GLASS)) {
                        if (relativeBlock.getType().equals(Material.AIR)) {

                            portalBlocks.add(relativeBlock);
                            triggerIndexes.add(portalBlocks.indexOf(relativeBlock));
                            clearIndexes.add(portalBlocks.indexOf(relativeBlock));

                        }
                    } else if (clipboardData.getMaterial().equals(Material.WHITE_STAINED_GLASS)) {
                        if (relativeBlock.getType().equals(Material.AIR)) {

                            portalBlocks.add(relativeBlock);
                            clearIndexes.add(portalBlocks.indexOf(relativeBlock));
                        }
                    }
                }
            }
        }

        boolean isMatch = match && !portalBlocks.isEmpty() && !triggerIndexes.isEmpty() &&
                !clearIndexes.isEmpty() && originIndex > -1 && leverIndex > -1 && signIndex > -1;

        if (isMatch) {
            String name = "null";
            if (portalBlocks.get(signIndex).getState() instanceof Sign) {
                name = ((Sign) portalBlocks.get(signIndex).getState()).getLine(0);

                Block[] portals = new Block[portalBlocks.size()];
                for (Block block: portalBlocks) {
                    portals[portalBlocks.indexOf(block)] = block;
                }

                int[] triggers = new int[triggerIndexes.size()];
                for (Integer num: triggerIndexes) {
                    triggers[triggerIndexes.indexOf(num)] = num;
                }

                int[] clears = new int[clearIndexes.size()];
                for (Integer num: clearIndexes) {
                    clears[clearIndexes.indexOf(num)] = num;
                }

                portal = new PowerPortal(name, owner, portals, triggers, clears, originIndex, leverIndex, signIndex);

                return portal;
            }
        }
        return null;
    }
}
