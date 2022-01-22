package io.github.nsdigirolamo.powerportals.models;

import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.UUID;

public class Linkage {
    private final UUID id;
    private final PowerPortal entrance;
    private final Block[] entranceTriggers;
    private final PowerPortal exit;
    private final Block[] exitTriggers;

    public Linkage (PowerPortal entrance, PowerPortal exit, Block[] entranceTriggers, Block[] exitTriggers) {
        this.id = UUID.randomUUID();
        this.entrance = entrance;
        this.entranceTriggers = entranceTriggers;
        this.exit = exit;
        this.exitTriggers = exitTriggers;
    }

    public UUID getID () {
        return id;
    }

    public PowerPortal getEntrance () {
        return entrance;
    }

    public Block[] getEntranceTriggers () {
        return entranceTriggers;
    }

    public Block[] getExitTriggers () {
        return exitTriggers;
    }

    public PowerPortal getExit () {
        return exit;
    }
}
