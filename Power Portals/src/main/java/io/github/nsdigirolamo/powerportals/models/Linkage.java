package io.github.nsdigirolamo.powerportals.models;

import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.UUID;

public class Linkage {
    private final UUID id;
    private final PowerPortal entrance;
    private final ArrayList<Block> entranceTriggers;
    private final PowerPortal exit;
    private final ArrayList<Block> exitTriggers;

    public Linkage (PowerPortal entrance, PowerPortal exit, ArrayList<Block> entranceTriggers, ArrayList<Block> exitTriggers) {
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

    public ArrayList<Block> getEntranceTriggers () {
        return entranceTriggers;
    }

    public ArrayList<Block> getExitTriggers () {
        return exitTriggers;
    }

    public PowerPortal getExit () {
        return exit;
    }
}
