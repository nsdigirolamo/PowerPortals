package io.github.nsdigirolamo.powerportals.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.nsdigirolamo.powerportals.PowerPortals;
import io.github.nsdigirolamo.powerportals.adapters.BlockAdapter;
import io.github.nsdigirolamo.powerportals.adapters.BlockFaceAdapter;
import io.github.nsdigirolamo.powerportals.adapters.PlayerAdapter;
import io.github.nsdigirolamo.powerportals.adapters.WorldAdapter;
import io.github.nsdigirolamo.powerportals.structures.PowerPortal;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * PortalStorageUtil is a utility that handles the storage of PowerPortals.
 */
public class PortalStorageUtil {

    private static ArrayList<PowerPortal> portals = new ArrayList<>();

    /**
     * Gets stored PowerPortals.
     * @return All stored PowerPortals.
     */
    public static PowerPortal[] getPortals () {
        PowerPortal[] list = new PowerPortal[portals.size()];
        for (PowerPortal p: portals) {
            list[portals.indexOf(p)] = p;
        }
        return list;
    }

    /**
     * Stores a PowerPortal and saves it to powerportals.json
     * @param portal The PowerPortal to be stored.
     */
    public static void addPortal(PowerPortal portal) {
        portals.add(portal);
        savePortals();
    }

    /**
     * Saves all PowerPortals to powerportals.json
     */
    public static void savePortals () {

        GsonBuilder builder = new GsonBuilder();
        builder.enableComplexMapKeySerialization().setPrettyPrinting();
        builder.registerTypeHierarchyAdapter(Block.class, new BlockAdapter());
        builder.registerTypeHierarchyAdapter(BlockFace.class, new BlockFaceAdapter());
        builder.registerTypeHierarchyAdapter(Player.class, new PlayerAdapter());
        builder.registerTypeHierarchyAdapter(World.class, new WorldAdapter());
        Gson gson = builder.create();

        File file = new File(PowerPortals.getPlugin().getDataFolder().getAbsolutePath() + "/powerportals.json");

        try {
            Writer writer = new FileWriter(file, false);
            gson.toJson(portals, writer);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads all PowerPortals from powerportals.json
     */
    public static void loadPortals () {

        GsonBuilder builder = new GsonBuilder();
        builder.enableComplexMapKeySerialization().setPrettyPrinting();
        builder.registerTypeHierarchyAdapter(Block.class, new BlockAdapter());
        builder.registerTypeHierarchyAdapter(BlockFace.class, new BlockFaceAdapter());
        builder.registerTypeHierarchyAdapter(Player.class, new PlayerAdapter());
        builder.registerTypeHierarchyAdapter(World.class, new WorldAdapter());
        Gson gson = builder.create();

        File file = new File(PowerPortals.getPlugin().getDataFolder().getAbsolutePath() + "/powerportals.json");

        if (file.exists()) {
            try {
                Reader reader = new FileReader(file);
                PowerPortal[] p = gson.fromJson(reader, PowerPortal[].class);
                portals.addAll(Arrays.asList(p));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Removes a PowerPortal from storage.
     * @param portal The PowerPortal to be deleted.
     */
    public static void deletePortal (PowerPortal portal) {
        portals.remove(portal);
    }

    /**
     * Finds a PowerPortal based on its lever in the game world.
     * @param lever The PowerPortal's lever.
     * @return The PowerPortal the given lever belongs to. Null if no such PowerPortal exists.
     */
    public static PowerPortal findPortal (Block lever) {
        if (lever.getType().equals(Material.LEVER)) {
            for (PowerPortal portal: portals) {
                if (portal.getLever().equals(lever)) {
                    return portal;
                }
            }
        }
        return null;
    }

    /**
     * Finds a PowerPortal based on its name.
     * @param name The PowerPortal's name.
     * @return The PowerPortal the given name belongs to. Null if no such PowerPortal exists.
     */
    public static PowerPortal findPortal (String name) {
        for (PowerPortal portal: portals) {
            if (portal.getName().equals(name)) {
                return portal;
            }
        }
        return null;
    }
}
