package io.github.nsdigirolamo.powerportals.structures;

import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.block.data.type.Switch;
import org.bukkit.block.data.type.WallSign;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * A PowerPortal is an in-game structure that players can use to teleport throughout the game world. PowerPortal designs
 * are based of the schematic files found in the portaldesigns directory of the plugin's data folder.
 */
public class PowerPortal {
    private final UUID portalId;
    private String portalName;
    private OfflinePlayer owner;
    private final Block[] portalBlocks;
    private final World world;
    private final Block[] triggerBlocks;
    private final Block[] clearBlocks;
    private final Block origin;
    private final Block lever;
    private final Block sign;
    private final BlockFace facing;
    private PowerPortal exit;
    private boolean entrance;

    /**
     * Class constructor.
     * @param portalName The name of the PowerPortal.
     * @param owner The owner of the PowerPortal.
     * @param portalBlocks All blocks that make up the PowerPortal.
     * @param triggerIndexes The indexes of portalBlocks where trigger blocks can be found.
     * @param clearIndexes The indexes of portalBlocks where clear blocks can be found.
     * @param originIndex The index of portalBlocks where the origin block can be found.
     * @param leverIndex The index of portalBlocks where the lever block can be found.
     * @param signIndex The index of PortalBlocks where the sign block can be found.
     */
    public PowerPortal (String portalName, Player owner, Block[] portalBlocks, int[] triggerIndexes, int[] clearIndexes, int originIndex, int leverIndex, int signIndex) {
        this.portalId = UUID.randomUUID();
        this.portalName = portalName;
        this.owner = owner;
        this.portalBlocks = portalBlocks;
        this.world = portalBlocks[0].getWorld();

        for (Block block: portalBlocks) {
            if (!block.getWorld().equals(world)) {
                throw new IllegalArgumentException ("all blocks in portal must be in the same world");
            }
        }

        this.triggerBlocks = new Block[triggerIndexes.length];

        for (int i = 0; i < triggerBlocks.length; i++) {
            if (triggerIndexes[i] > portalBlocks.length - 1 || triggerIndexes[i] < 0) {
                throw new IllegalArgumentException("triggerIndexes[" + i + "] must be an index within bounds of portal");
            } else {
                triggerBlocks[i] = portalBlocks[triggerIndexes[i]];
            }
        }

        this.clearBlocks = new Block[clearIndexes.length];

        for (int i = 0; i < clearBlocks.length; i++) {
            if (clearIndexes[i] > portalBlocks.length - 1 || clearIndexes[i] < 0) {
                throw new IllegalArgumentException("clearIndexes[" + i + "] must be an index within bounds of portal");
            } else {
                clearBlocks[i] = portalBlocks[clearIndexes[i]];
            }
        }

        if (originIndex > portalBlocks.length - 1 || originIndex < 0) {
            throw new IllegalArgumentException ("originIndex must be an index within bounds of portal");
        } else {
            this.origin = portalBlocks[originIndex];
        }

        if (leverIndex > portalBlocks.length - 1 || leverIndex < 0) {
            throw new IllegalArgumentException ("leverIndex must be an index within bounds of portal");
        } else {
            this.lever = portalBlocks[leverIndex];
            if (lever.getType() != Material.LEVER) {
                throw new IllegalArgumentException ("leverIndex must be the index of a lever block");
            }
        }

        if (signIndex > portalBlocks.length - 1 || signIndex < 0) {
            throw new IllegalArgumentException ("signIndex must be an index within bounds of portal");
        } else {
            this.sign = portalBlocks[signIndex];
            if (!(sign.getBlockData() instanceof WallSign)) {
                throw new IllegalArgumentException ("signIndex must be the index of a wall sign block");
            }
        }

        this.facing = ((Switch) lever.getBlockData()).getFacing();
        this.exit = null;
        this.entrance = false;
    }

    /**
     * Get the ID of the PowerPortal.
     * @return The PowerPortal's portalId.
     */
    public UUID getId () {
        return portalId;
    }

    /**
     * Get the name of the PowerPortal.
     * @return The PowerPortal's portalName.
     */
    public String getName () {
        return portalName;
    }

    /**
     * Sets the name of the PowerPortal. Also changes the PowerPortal's sign to reflect the new name.
     * @param name The PowerPortal's new portalName.
     */
    public void setName (String name) {
        this.portalName = name;
        Sign sign = (Sign) this.sign.getState();
        sign.setLine(0, "name");
        sign.update();
    }

    /**
     * Get the owner of the PowerPortal.
     * @return The PowerPortal's owner.
     */
    public OfflinePlayer getOwner () {
        return owner;
    }

    /**
     * Sets the owner of the PowerPortal. Also changes the PowerPortal's sign to reflect the new owner.
     * @param newOwner The PowerPortal's new owner;
     */
    public void setOwner (Player newOwner) {
        this.owner = newOwner;
        Sign sign = (Sign) this.sign.getState();
        sign.setLine(1, newOwner.getName());
        sign.update();
    }

    /**
     * Get all blocks that make up the PowerPortal, including trigger blocks, clear blocks, and the origin block.
     * @return The PowerPortal's portalBlocks.
     */
    public Block[] getPortalBlocks () {
        return portalBlocks;
    }

    /**
     * Get the world the PowerPortal exists in.
     * @return The PowerPortal's world.
     */
    public World getWorld () {
        return world;
    }

    /**
     * Get the trigger blocks of the PowerPortal.
     * @return The PowerPortal's triggerBlocks.
     */
    public Block[] getTriggerBlocks () {
        return triggerBlocks;
    }

    public Block[] getClearBlocks () {
        return clearBlocks;
    }

    /**
     * Get the origin block of the PowerPortal.
     * @return The PowerPortal's origin.
     */
    public Block getOrigin () {
        return origin;
    }

    /**
     * Get the x position of the PowerPortal.
     * @return The x position of the PowerPortal's origin.
     */
    public int getX () {
        return origin.getX();
    }

    /**
     * Get the y position of the PowerPortal.
     * @return The y position of the PowerPortal's origin.
     */
    public int getY () {
        return origin.getY();
    }

    /**
     * Get the z position of the PowerPortal.
     * @return The z position of the PowerPortal's origin.
     */
    public int getZ () {
        return origin.getZ();
    }

    /**
     * Get the lever of the PowerPortal.
     * @return The PowerPortal's lever.
     */
    public Block getLever () {
        return lever;
    }

    public Block getSign () {
        return sign;
    }

    /**
     * Get the direction the PowerPortal is facing.
     * @return The PowerPortal's direction.
     */
    public BlockFace getFacing () {
        return facing;
    }

    /**
     * Get the exit of the PowerPortal if it exists.
     * @return The PowerPortal's exit.
     */
    public PowerPortal getExit () {
        return exit;
    }

    /**
     * Set the exit of the PowerPortal.
     * @param portal The PowerPortal's exit.
     */
    public void setExit (PowerPortal portal) {
        this.exit = portal;
    }

    /**
     * Check to see if this portal has an exit.
     * @return True if an exit exists.
     */
    public boolean hasExit () {
        return exit != null;
    }

    /**
     * Remove the exit of the PowerPortal.
     */
    public void removeExit () {
        this.exit = null;
    }

    /**
     * Check to see if the PowerPortal is an entrance.
     * @return True if the PowerPortal is an entrance.
     */
    public boolean isEntrance () {
        return entrance;
    }

    /**
     * Set whether the PowerPortal is an entrance.
     * @param state True if the PowerPortal is an entrance.
     */
    public void setEntrance (boolean state) {
        this.entrance = state;
    }
}
