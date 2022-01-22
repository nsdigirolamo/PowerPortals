package io.github.nsdigirolamo.powerportals.models;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Switch;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.UUID;

/**
 * A portal that players can use to teleport to and from.
 */
public class PowerPortal {
    private final UUID portalId;
    private final String portalName;
    private final UUID ownerId;
    private final String ownerName;
    private final Block[] portal;
    private final World world;
    private final Block origin;
    private final Block lever;
    private final Block[] triggers;
    private final Vector facing;


    /**
     * Class constructor.
     * @param portalName Name of the portal
     * @param player Owner of the portal
     * @param portal The blocks that make up the portal
     * @param origin The block a player will be transported to
     * @param lever The portal's lever
     * @param triggers The blocks that portal use
     */
    public PowerPortal (String portalName, Player player, Block[] portal, int origin, int lever, int[] triggers) {
        this.portalId = UUID.randomUUID();
        this.portalName = portalName;
        this.ownerId = player.getUniqueId();
        this.ownerName = player.getName();

        if (portal.length > 0) {
            World world = portal[0].getWorld();
            for (Block block: portal) {
                if (!block.getWorld().equals(world)) {
                    throw new IllegalArgumentException ("all Blocks in portal must be in same world");
                }
            }
            this.portal = portal;
            this.world = world;
        } else {
            throw new IllegalArgumentException ("portal must have length greater than 0");
        }

        try {
            this.origin = portal[origin];
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException ("origin must be an index within bounds of portal");
        }

        try {
            if (portal[lever].getType() == Material.LEVER) {
                this.lever = portal[lever];
            } else {
                throw new IllegalArgumentException ("lever must be the index of a block in portal with Material equal to LEVER");
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException ("lever must be an index within bounds of portal");
        }

        this.triggers = new Block[triggers.length];
        try {
            for (int i = 0; i < this.triggers.length; i++) {
                this.triggers[i] = portal[triggers[i]];
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException ("all triggers must be an index within bounds of portal");
        }

        this.facing = ((Switch) this.lever.getBlockData()).getFacing().getDirection();
    }

    /**
     * Checks equality between two PowerPortals.
     * @param p The portal to be compared
     * @return True if the portals are the same
     */
    public boolean equals (PowerPortal p) {
        return
                portalId.equals(p.getId()) &&
                portalName.equals(p.getName()) &&
                ownerId.equals(p.getOwnerId()) &&
                ownerName.equals(p.getOwnerName()) &&
                Arrays.equals(portal, p.getPortal()) &&
                world.equals(p.getWorld()) &&
                origin.equals(p.getOrigin()) &&
                lever.equals(p.getLever()) &&
                Arrays.equals(triggers, p.getTriggers());
    }

    /**
     * Gets the ID of this PowerPortal.
     * @return The portal's portalId
     */
    public UUID getId () {
        return portalId;
    }

    /**
     * Gets the name of this PowerPortal.
     * @return This PowerPortal's portalName
     */
    public String getName () {
        return portalName;
    }

    /**
     * Gets the ID of this PowerPortal's owner.
     * @return This PowerPortal's ownerId
     */
    public UUID getOwnerId () {
        return ownerId;
    }

    /**
     * Gets the name of this PowerPortal's owner.
     * @return This PowerPortal's ownerId
     */
    public String getOwnerName () {
        return ownerName;
    }

    /**
     * Gets the blocks making up this PowerPortal.
     * @return An array of the blocks making up this PowerPortal
     */
    public Block[] getPortal () {
        return portal;
    }

    /**
     * Gets the world this PowerPortal exists in.
     * @return This PowerPortal's world
     */
    public World getWorld () {
        return world;
    }

    /**
     * Gets the origin of this PowerPortal. The origin block is the block a player will be teleported on top of if this
     * portal is their destination.
     * @return This PowerPortal's origin
     */
    public Block getOrigin () {
        return origin;
    }

    /**
     * Gets the lever of this PowerPortal. The lever block is the lever a player will use to activate this PowerPortal.
     * @return This PowerPortal's lever
     */
    public Block getLever () {
        return lever;
    }

    /**
     * Gets the triggers of this PowerPortal. The triggers are the blocks the player will enter to use this PowerPortal.
     * @return An array of blocks making up the triggers of this PowerPortal
     */
    public Block[] getTriggers () {
        return triggers;
    }

    /**
     * Gets the direction this PowerPortal is facing.
     * @return The direction this PowerPortal is facing
     */
    public Vector getFacing () {
        return facing;
    }
}
