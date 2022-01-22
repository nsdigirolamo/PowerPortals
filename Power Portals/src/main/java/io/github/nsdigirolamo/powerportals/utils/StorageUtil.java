package io.github.nsdigirolamo.powerportals.utils;

import com.google.gson.Gson;
import io.github.nsdigirolamo.powerportals.PowerPortals;
import io.github.nsdigirolamo.powerportals.models.PowerPortal;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class StorageUtil {

    private static ArrayList<PowerPortal> portals = new ArrayList<>();

    public static ArrayList<PowerPortal> getPortals() {
        return portals;
    }

    public static void storePowerPortal (PowerPortal portal) {
        portals.add(portal);
    }

    public static PowerPortal readPowerPortal (UUID id) {
        for (PowerPortal powerPortal: portals) {
            if (powerPortal.getId().equals(id)) {
                return powerPortal;
            }
        }
        return null;
    }

    /**
     * Searches for a portal with the given gateName
     * @param gateName The name of the gate.
     * @return
     */
    public static PowerPortal searchPowerPortal (String gateName) {
        for (PowerPortal powerPortal: portals) {
            if (powerPortal.getName().equals(gateName)) {
                return powerPortal;
            }
        }
        return null;
    }

    /**
     * Deletes a portal.
     * @param id The portalID of the portal.
     */
    public static void deletePowerPortal (UUID id) {
        for (PowerPortal powerPortal: portals) {
            if (powerPortal.getId().equals(id)) {
                portals.remove(powerPortal);
                break;
            }
        }
    }

    /**
     * Saves portals to powerportals.json
     * @throws IOException
     */
    public static void savePowerPortals() throws IOException {
        Gson gson = new Gson();

        File file = new File(PowerPortals.getPlugin().getDataFolder().getAbsolutePath() + "/powerportals.json");

        Writer writer = new FileWriter(file, false);
        gson.toJson(portals, writer);
        writer.flush();
        writer.close();

        Bukkit.getLogger().info(ChatColor.GREEN + "[PowerPortals] saved powerportals.json");
    }

    /**
     * Loads saved portals from powerportals.json
     * @throws FileNotFoundException
     */
    public static void loadPowerPortals() throws FileNotFoundException {
        Gson gson = new Gson();
        File file = new File(PowerPortals.getPlugin().getDataFolder().getAbsolutePath() + "/powerportals.json");

        if (file.exists()) {
            Reader reader = new FileReader(file);
            PowerPortal[] p = gson.fromJson(reader, PowerPortal[].class);
            portals = new ArrayList<>(Arrays.asList(p));

            Bukkit.getLogger().info(ChatColor.GREEN + "[PowerPortals] loaded powerportals.json");
        }
    }
}
