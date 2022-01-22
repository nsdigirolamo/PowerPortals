package io.github.nsdigirolamo.powerportals.models;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Switch;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.UUID;

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

    public UUID getId () {
        return portalId;
    }

    public String getName () {
        return portalName;
    }

    public UUID getOwnerId () {
        return ownerId;
    }

    public String getOwnerName () {
        return ownerName;
    }

    public Block[] getPortal () {
        return portal;
    }

    public World getWorld () {
        return world;
    }

    public Block getOrigin () {
        return origin;
    }

    public Block getLever () {
        return lever;
    }

    public Block[] getTriggers () {
        return triggers;
    }

    public Vector getFacing () {
        return facing;
    }
}
