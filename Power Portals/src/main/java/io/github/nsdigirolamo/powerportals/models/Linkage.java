package io.github.nsdigirolamo.powerportals.models;

import org.bukkit.block.Block;

import java.util.UUID;

/**
 * A Linkage is a connection between two PowerPortals. Players can travel between PowerPortals if there is a Linkage
 * between them.
 */
public class Linkage {
    private final UUID id;
    private final PowerPortal entrance;
    private final Block[] entranceTriggers;
    private final PowerPortal exit;
    private final Block[] exitTriggers;

    /**
     * Class constructor for Linkage.
     * @param entrance The entrance PowerPortal
     * @param exit The exit PowerPortal
     * @param entranceTriggers An array of the trigger blocks for the entrance PowerPortal
     * @param exitTriggers An array of the trigger blocks for the exit PowerPortal
     */
    public Linkage (PowerPortal entrance, PowerPortal exit, Block[] entranceTriggers, Block[] exitTriggers) {
        this.id = UUID.randomUUID();
        this.entrance = entrance;
        this.entranceTriggers = entranceTriggers;
        this.exit = exit;
        this.exitTriggers = exitTriggers;
    }

    /**
     * Gets the ID for this Linkage.
     * @return This Linkage's id
     */
    public UUID getID () {
        return id;
    }

    /**
     * Gets the entrance PowerPortal for this Linkage.
     * @return This Linkage's entrance
     */
    public PowerPortal getEntrance () {
        return entrance;
    }

    /**
     * Gets the triggers for the entrance of this Linkage.
     * @return An array of Blocks that are the triggers for this Linkage's entrance
     */
    public Block[] getEntranceTriggers () {
        return entranceTriggers;
    }

    /**
     * Gets the exit PowerPortal for this Linkage.
     * @return This Linkage's entrance
     */
    public PowerPortal getExit () {
        return exit;
    }

    /**
     * Gets the triggers for the exit of this Linkage.
     * @return An array of Blocks that are the triggers for this Linkage's xit
     */
    public Block[] getExitTriggers () {
        return exitTriggers;
    }
}
