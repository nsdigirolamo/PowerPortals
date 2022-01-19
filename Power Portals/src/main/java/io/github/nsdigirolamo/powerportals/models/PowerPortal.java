package io.github.nsdigirolamo.powerportals.models;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PowerPortal {
    private final double x;
    private final double y;
    private final double z;
    private String portalName;
    private final UUID portalID;
    private UUID ownerID;
    private String ownerName;
    private final UUID worldID;
    private final String worldName;

    public PowerPortal (Player player, String portalName, Location origin) {
        this.x = origin.getX();
        this.y = origin.getY();
        this.z = origin.getZ();
        this.portalName = portalName;
        this.portalID = UUID.randomUUID();
        this.ownerID = player.getUniqueId();
        this.ownerName = Bukkit.getPlayer(ownerID).getName();
        this.worldID = origin.getWorld().getUID();
        this.worldName = Bukkit.getWorld(worldID).getName();
    }

    public Location getOrigin () {
        World world = Bukkit.getWorld(worldID);
        return new Location(world, x, y, z);
    }

    public String getPortalName() {
        return portalName;
    }

    public void setPortalName (String gateName) {
        this.portalName = gateName;
    }

    public UUID getID () {
        return portalID;
    }

    public UUID getOwnerID () {
        return ownerID;
    }

    public void setOwnerID (UUID ownerID) {
        this.ownerID = ownerID;
        this.ownerName = Bukkit.getPlayer(ownerID).getName();
    }

    public String getOwnerName () {
        return ownerName;
    }

    public UUID getWorldID () {
        return worldID;
    }

    public String getWorldName () {
        return worldName;
    }
}
