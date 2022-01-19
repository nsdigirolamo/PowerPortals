package io.github.nsdigirolamo.powerportals.utils;

import com.google.gson.Gson;
import io.github.nsdigirolamo.powerportals.PowerPortals;
import io.github.nsdigirolamo.powerportals.models.PowerPortal;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import static org.bukkit.Bukkit.getLogger;

public class PortalStorageUtil {

    private static ArrayList<PowerPortal> powerPortals = new ArrayList<>();

    public static ArrayList<PowerPortal> getPowerPortals() {
        return powerPortals;
    }

    /**
     * Creates and stores a new portal.
     * @param player The owner of the portal.
     * @param portalName The name of the portal.
     * @param origin The origin of the portal.
     * @return The newly created portal.
     */
    public static PowerPortal createPowerPortal (Player player, String portalName, Location origin) {
        PowerPortal powerPortal = new PowerPortal(player, portalName, origin);
        powerPortals.add(powerPortal);
        try {
            savePowerPortals();
            return powerPortal;
        } catch (IOException e) {
            getLogger().warning(ChatColor.RED + "[PowerPortals] failed to save powerportals.json!");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Reads a portal.
     * @param id The portalID of the portal.
     * @return The portal if the id was found.
     */
    public static PowerPortal readPowerPortal (UUID id) {
        for (PowerPortal powerPortal: powerPortals) {
            if (powerPortal.getID().equals(id)) {
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
        for (PowerPortal powerPortal: powerPortals) {
            if (powerPortal.getID().equals(id)) {
                powerPortals.remove(powerPortal);
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
        gson.toJson(powerPortals, writer);
        writer.flush();
        writer.close();

        getLogger().info(ChatColor.GREEN + "[PowerPortals] saved powerportals.json");
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
            powerPortals = new ArrayList<>(Arrays.asList(p));

            getLogger().info(ChatColor.GREEN + "[PowerPortals] loaded powerportals.json");
        }
    }
}
