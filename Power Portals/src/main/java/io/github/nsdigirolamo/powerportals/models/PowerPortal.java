package io.github.nsdigirolamo.powerportals.models;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Switch;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class PowerPortal {
    // TODO: figure out how to get Gson to play nicely with Block interfaces, Location Objects, etc. from Bukkit/Spigot
    private final double[] origin;
    private final double[] lever;
    private final Vector facing;
    private ArrayList<double[]> triggers = new ArrayList<>();
    private final UUID portalID;
    private String portalName;
    private UUID ownerID;
    private String ownerName;
    private UUID worldID;
    private String worldName;

    public PowerPortal (Player player, String portalName, Location origin, Location lever, ArrayList<Location> triggers) {
        this.origin = new double[] {origin.getX(), origin.getY(), origin.getZ()};
        this.lever = new double[] {lever.getX(), lever.getY(), lever.getZ()};
        if (lever.getBlock().getType() == Material.LEVER) {
            this.facing = ((Switch) lever.getBlock().getBlockData()).getFacing().getDirection();
        } else {
            this.facing = BlockFace.NORTH.getDirection();
        }
        for (Location trigger: triggers) {
            this.triggers.add(new double[] {trigger.getX(), trigger.getY(), trigger.getZ()});
        }
        this.portalID = UUID.randomUUID();
        this.portalName = portalName;
        this.ownerID = player.getUniqueId();
        this.ownerName = Bukkit.getPlayer(ownerID).getName();
        this.worldID = origin.getWorld().getUID();
        this.worldName = origin.getWorld().getName();
    }

    public boolean equals (PowerPortal portal) {

        double[] originXYZ = new double[]{portal.getOriginX(), portal.getOriginY(), portal.getOriginZ()};
        double[] leverXYZ = new double[]{portal.getLeverX(), portal.getLeverY(), portal.getLeverZ()};

        return Arrays.equals(originXYZ, origin) && Arrays.equals(leverXYZ, lever) &&
                portal.getTriggers().equals(triggers) && portal.getID().equals(portalID) &&
                portal.getPortalName().equals(portalName) && portal.getOwnerID().equals(ownerID) &&
                portal.getOwnerName().equals(ownerName) && portal.getWorldID().equals(worldID) &&
                portal.getWorldName().equals(worldName);
    }

    public String toString() {
        return "origin:" + Arrays.toString(origin) + ",lever:" + Arrays.toString(lever) +
                ",triggers:" + triggers.toString() + ",portalID:" + portalID.toString() + ",portalName:" + portalName +
                ",ownerID:" + ownerID.toString() + ",ownerName:" + ownerName + ",worldID:" + worldID.toString() +
                ",worldName:" + worldName;
    }

    public Location getOrigin () {
        return new Location(Bukkit.getWorld(worldID), origin[0], origin[1], origin[2]);
    }

    public double getOriginX() {
        return origin[0];
    }

    public double getOriginY() {
        return origin[1];
    }

    public double getOriginZ() {
        return origin[2];
    }

    public Location getLever () {
        return new Location(Bukkit.getWorld(worldID), lever[0], lever[1], lever[2]);
    }

    public double getLeverX() {
        return lever[0];
    }

    public double getLeverY() {
        return lever[1];
    }

    public double getLeverZ() {
        return lever[2];
    }

    public Vector getFacing() {
       return facing;
    }

    public ArrayList<double[]> getTriggers() {
        return triggers;
    }

    public void setTriggers(ArrayList<double[]> newTriggers) {
        this.triggers = newTriggers;
    }

    public UUID getID () {
        return portalID;
    }

    public String getPortalName() {
        return portalName;
    }

    public void setPortalName (String gateName) {
        this.portalName = gateName;
    }

    public UUID getOwnerID () {
        return ownerID;
    }

    public void setOwnerID (UUID ownerID) {
        this.ownerID = ownerID;
    }

    public String getOwnerName () {
        return ownerName;
    }

    public void setOwnerName () {
        this.ownerName = ownerName;
    }

    public UUID getWorldID () {
        return worldID;
    }

    public void setWorldID (UUID worldID) {
        this.worldID = worldID;
    }

    public String getWorldName () {
        return worldName;
    }

    public void setWorldName () {
        this.worldName = worldName;
    }

}
