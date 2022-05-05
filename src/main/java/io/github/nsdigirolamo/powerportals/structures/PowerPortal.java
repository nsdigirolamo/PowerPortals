package io.github.nsdigirolamo.powerportals.structures;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.WallSign;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.UUID;

/**
 * Represents a PowerPortal in the game world
 */
public class PowerPortal {

    private final UUID id;
    private String name;
    private Location location;
    private UUID ownerID;
    private String password;
    private Block[] structure;
    private Block[] triggers;
    private Block sign;
    private Block lever;
    private boolean isActivated;
    private PowerPortal exit;

    /**
     * Constructor method.
     * @param name the name of the PowerPortal
     * @param location the location of the PowerPortal
     * @param owner the PowerPortal's owner
     * @param structure the blocks that make up the PowerPortal
     * @param triggers the blocks that will trigger teleportation
     * @throws IllegalArgumentException if there is not exactly one wall sign in the structure
     * @throws IllegalArgumentException if there is not exactly one lever in the structure
     */
    public PowerPortal (String name, Location location, Player owner, Block[] structure, Block[] triggers) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.location = location;
        this.ownerID = owner.getUniqueId();
        this.password = null;
        this.structure = structure;
        this.triggers = triggers;

        int signCount = 0;
        Block sign = null;
        int leverCount = 0;
        Block lever = null;

        for (Block block : structure) {
            if (block.getBlockData() instanceof WallSign) {
                signCount++;
                sign = block;
            } else if (block.getType().equals(Material.LEVER)) {
                leverCount++;
                lever = block;
            }
        }

        if (signCount != 1) {
            throw new IllegalArgumentException ("structure must contain exactly one wall sign.");
        } else if (leverCount != 1) {
            throw new IllegalArgumentException ("structure must contain exactly one lever.");
        } else {
            this.sign = sign;
            this.lever = lever;
        }

        this.isActivated = false;
        this.exit = null;
    }

    /**
     * @return this PowerPortal's id
     */
    public UUID getId () {
        return this.id;
    }

    /**
     * @return this PowerPortal's name
     */
    public String getName () {
        return this.name;
    }

    /**
     * @return this PowerPortal's location
     */
    public Location getLocation () {
        return this.location;
    }

    /**
     * @return the x coordinate of this PowerPortal's location
     */
    public double getX () {
        return this.location.getX();
    }

    /**
     * @return the y coordinate of this PowerPortal's location
     */
    public double getY () {
        return this.location.getY();
    }

    /**
     * @return the z coordinate of this PowerPortal's location
     */
    public double getZ () {
        return this.location.getZ();
    }

    /**
     * @return this PowerPortal's owner's ID
     */
    public UUID getOwnerID () {
        return this.ownerID;
    }

    /**
     * @return this PowerPortal's password
     */
    public String getPassword () {
        return this.password;
    }

    /**
     * @return this PowerPortal's structure
     */
    public Block[] getStructure () {
        return this.structure;
    }

    /**
     * @return this PowerPortal's triggers
     */
    public Block[] getTriggers () {
        return this.triggers;
    }

    /**
     * @return this PowerPortal's sign
     */
    public Block getSign () {
        return this.sign;
    }

    /**
     * @return this PowerPortal's lever
     */
    public Block getLever () {
        return this.lever;
    }

    /**
     * @return whether this PowerPortal is activated or not
     */
    public boolean isActivated () {
        return this.isActivated;
    }

    /**
     * @return this PowerPortal's exit
     */
    public PowerPortal getExit () {
        return this.exit;
    }

    /**
     * Sets the name of the PowerPortal.
     * @param name the new name
     */
    public void setName (String name) {
        this.name = name;
    }

    /**
     * Sets the location of the PowerPortal.
     * @param location the new location
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * Sets the owner of the PowerPortal.
     * @param ownerID the new owner's ID
     */
    public void setOwnerID (UUID ownerID) {
        this.ownerID = ownerID;
    }

    /**
     * Sets the password of the PowerPortal.
     * @param password the new password
     */
    public void setPassword (String password) {
        this.password = password;
    }

    /**
     * Deletes the password of the PowerPortal.
     */
    public void deletePassword () {
        this.password = null;
    }

    /**
     * Sets the structure of the PowerPortal. Also reassigns the PowerPortal's sign and lever based on the new structure.
     * @param structure the new structure
     * @throws IllegalArgumentException if there is not exactly one wall sign in the structure
     * @throws IllegalArgumentException if there is not exactly one lever in the structure
     */
    public void setStructure (Block[] structure) {
        int signCount = 0;
        Block sign = null;
        int leverCount = 0;
        Block lever = null;

        for (Block block : structure) {
            if (block.getBlockData() instanceof WallSign) {
                signCount++;
                sign = block;
            } else if (block.getType().equals(Material.LEVER)) {
                leverCount++;
                lever = block;
            }
        }

        if (signCount != 1) {
            throw new IllegalArgumentException ("structure must contain exactly one wall sign.");
        } else if (leverCount != 1) {
            throw new IllegalArgumentException ("structure must contain exactly one lever.");
        } else {
            this.sign = sign;
            this.lever = lever;
        }

        this.structure = structure;
    }

    /**
     * Sets the triggers of the PowerPortal.
     * @param triggers the new triggers
     */
    public void setTriggers (Block[] triggers) {
        this.triggers = triggers;
    }

    /**
     * Sets the activation state of the PowerPortal.
     * @param state whether the PowerPortal is activated or not
     */
    public void setActivated (boolean state) {
        this.isActivated = state;
    }

    /**
     * Sets the exit of the PowerPortal.
     * @param exit the PowerPortal's exit
     */
    public void setExit (@Nullable PowerPortal exit) {
        this.exit = exit;
    }
}
