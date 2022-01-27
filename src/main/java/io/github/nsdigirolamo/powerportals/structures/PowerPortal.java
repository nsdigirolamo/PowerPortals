package io.github.nsdigirolamo.powerportals.structures;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Switch;
import org.bukkit.block.data.type.WallSign;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.UUID;

public class PowerPortal {
    private final UUID portalId;
    private final String portalName;
    private final Player owner;
    private final Block[] portalBlocks;
    private final World world;
    private final Block[] triggerBlocks;
    private final Block[] clearBlocks;
    private final Block origin;
    private final Block lever;
    private final Block sign;
    private final Vector facing;
    private PowerPortal exit;
    private boolean entrance;

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

        this.facing = ((Switch) lever.getBlockData()).getFacing().getDirection();
        this.exit = null;
        this.entrance = false;
    }

    public boolean equals (PowerPortal p) {
        return portalId.equals(p.getId()) && portalName.equals(p.getName()) && owner.equals(p.getOwner()) &&
                Arrays.equals(portalBlocks, p.getPortalBlocks()) && world.equals(p.getWorld()) &&
                Arrays.equals(triggerBlocks, p.getTriggerBlocks()) && origin.equals(p.getOrigin()) &&
                lever.equals(p.getLever()) && facing.equals(p.getFacing()) && exit.equals(p.getExit()) &&
                (entrance == p.isEntrance());
    }

    public UUID getId () {
        return portalId;
    }

    public String getName () {
        return portalName;
    }

    public Player getOwner () {
        return owner;
    }

    public Block[] getPortalBlocks () {
        return portalBlocks;
    }

    public World getWorld () {
        return world;
    }

    public Block[] getTriggerBlocks () {
        return triggerBlocks;
    }

    public Block getOrigin () {
        return origin;
    }

    public int getX () {
        return origin.getX();
    }

    public int getY () {
        return origin.getY();
    }

    public int getZ () {
        return origin.getZ();
    }

    public Block getLever () {
        return lever;
    }

    public Vector getFacing () {
        return facing;
    }

    public PowerPortal getExit () {
        return exit;
    }

    public void setExit (PowerPortal portal) {
        this.exit = portal;
    }

    public void removeExit () {
        this.exit = null;
    }

    public boolean isEntrance () {
        return entrance;
    }

    public void setEntrance (boolean state) {
        this.entrance = state;
    }
}
