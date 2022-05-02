package io.github.nsdigirolamo.powerportals.utilities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.nsdigirolamo.powerportals.PowerPortals;
import io.github.nsdigirolamo.powerportals.structures.PowerPortal;
import io.github.nsdigirolamo.powerportals.utilities.adapters.BlockAdapter;
import io.github.nsdigirolamo.powerportals.utilities.adapters.BlockFaceAdapter;
import io.github.nsdigirolamo.powerportals.utilities.adapters.LocationAdapter;
import io.github.nsdigirolamo.powerportals.utilities.adapters.OfflinePlayerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class StorageUtility {

    private static ArrayList<PowerPortal> powerPortals = new ArrayList<>();

    /**
     * Store a PowerPortal.
     * @param portal the PowerPortal to be stored.
     */
    public static void storePortal (PowerPortal portal) {
        powerPortals.add(portal);
        save();
    }

    /**
     * Delete a PowerPortal.
     * @param portal the PowerPortal to be deleted.
     */
    public static void deletePortal (PowerPortal portal) {
        powerPortals.remove(portal);
        save();
    }

    /**
     * Provides an array of all PowerPortals. Should not be necessary for most use cases, instead use findPortals() or
     * findPortal() if searching for a particular portal.
     * @see StorageUtility#findPortals(Block)
     * @see StorageUtility#findPortals(Player)
     * @see StorageUtility#findPortals(OfflinePlayer)
     * @see StorageUtility#findPortal(UUID)
     * @see StorageUtility#findPortal(String)
     * @return an array of all PowerPortals
     */
    public static PowerPortal[] getPortals () {

        PowerPortal[] portals = new PowerPortal[powerPortals.size()];
        int index = 0;

        for (PowerPortal portal : powerPortals) {
            portals[index] = portal;
            index++;
        }

        return portals;
    }

    /**
     * Provides an array of PowerPortals with the given block.
     * @param block the block in a PowerPortal.
     * @return an array of PowerPortals with the given block.
     */
    public static PowerPortal[] findPortals (Block block) {

        ArrayList<PowerPortal> portals = new ArrayList<PowerPortal>();

        for (PowerPortal portal : powerPortals) {
            for (Block portalBlock : portal.getStructure()) {
                if (portalBlock.equals(block)) {
                    portals.add(portal);
                }
            }
        }

        return portals.toArray(new PowerPortal[portals.size()]);
    }

    /**
     * Provides an array of PowerPortals owned by the given player.
     * @param player the owner of a PowerPortal.
     * @return an array of PowerPortals owned by the given player.
     */
    public static PowerPortal[] findPortals (OfflinePlayer player) {

        ArrayList<PowerPortal> portals = new ArrayList<PowerPortal>();

        for (PowerPortal portal : powerPortals) {
            if (portal.getOwnerID().equals(player)) {
                portals.add(portal);
            }
        }

        return portals.toArray(new PowerPortal[portals.size()]);
    }

    /**
     * Provides an array of PowerPortals owned by the given player.
     * @param player the owner of a PowerPortal.
     * @return an array of PowerPortals owned by the given player.
     */
    public static PowerPortal[] findPortals (Player player) {

        ArrayList<PowerPortal> portals = new ArrayList<PowerPortal>();
        UUID ownerID = player.getUniqueId();

        for (PowerPortal portal : powerPortals) {
            if (portal.getOwnerID().equals(ownerID)) {
                portals.add(portal);
            }
        }

        return portals.toArray(new PowerPortal[portals.size()]);
    }

    /**
     * Provides a PowerPortal with the given ID.
     * @param id the ID of a PowerPortal.
     * @return a PowerPortal with the given ID if it exists, null if otherwise.
     */
    public static PowerPortal findPortal (UUID id) {

        for (PowerPortal portal : powerPortals) {
            if (portal.getId().equals(id)) {
                return portal;
            }
        }

        return null;
    }

    /**
     * Provides a  PowerPortal with the given name.
     * @param name the name of a PowerPortal.
     * @return a PowerPortal with the given name if it exists, null if otherwise.
     */
    public static PowerPortal findPortal (String name) {

        for (PowerPortal portal : powerPortals) {
            if (portal.getName().equals(name)) {
                return portal;
            }
        }

        return null;
    }

    /**
     * Provides an array of PowerPortals with the given trigger.
     * @param block the potential trigger block.
     * @return an array of PowerPortals with the given trigger.
     */
    public static PowerPortal[] findTriggers (Block block) {

        ArrayList<PowerPortal> portals = new ArrayList<PowerPortal>();

        for (PowerPortal portal : powerPortals) {
            for (Block triggerBlock : portal.getTriggers()) {
                if (triggerBlock.equals(block)) {
                    portals.add(portal);
                }
            }
        }

        return portals.toArray(new PowerPortal[portals.size()]);
    }

    /**
     * Saves portals to powerportals.json.
     */
    public static void save () {

        GsonBuilder builder = new GsonBuilder();
        builder.enableComplexMapKeySerialization().setPrettyPrinting();
        builder.registerTypeHierarchyAdapter(Block.class, new BlockAdapter());
        builder.registerTypeHierarchyAdapter(BlockFace.class, new BlockFaceAdapter());
        builder.registerTypeHierarchyAdapter(Location.class, new LocationAdapter());
        builder.registerTypeHierarchyAdapter(OfflinePlayer.class, new OfflinePlayerAdapter());

        Gson gson = builder.create();

        File file = new File(PowerPortals.getPlugin().getDataFolder().getAbsolutePath() + "/powerportals.json");

        try {
            Writer writer = new FileWriter(file, false);
            gson.toJson(powerPortals, writer);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads portals from powerportals.json.
     */
    public static void load () {

        PowerPortals.getPlugin().getLogger().info("Loading powerportals.json...");

        GsonBuilder builder = new GsonBuilder();
        builder.enableComplexMapKeySerialization().setPrettyPrinting();
        builder.registerTypeHierarchyAdapter(Block.class, new BlockAdapter());
        builder.registerTypeHierarchyAdapter(BlockFace.class, new BlockFaceAdapter());
        builder.registerTypeHierarchyAdapter(Location.class, new LocationAdapter());
        builder.registerTypeHierarchyAdapter(OfflinePlayer.class, new OfflinePlayerAdapter());

        Gson gson = builder.create();

        File file = new File(PowerPortals.getPlugin().getDataFolder().getAbsolutePath() + "/powerportals.json");

        if (file.exists()) {
            try {
                Reader reader = new FileReader(file);
                PowerPortal[] p = gson.fromJson(reader, PowerPortal[].class);
                powerPortals.addAll(Arrays.asList(p));
                PowerPortals.getPlugin().getLogger().info("Loaded powerportals.json");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

}
