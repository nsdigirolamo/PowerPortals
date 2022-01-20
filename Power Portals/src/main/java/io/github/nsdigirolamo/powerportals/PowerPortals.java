package io.github.nsdigirolamo.powerportals;

import io.github.nsdigirolamo.powerportals.commands.LinkCommand;
import io.github.nsdigirolamo.powerportals.eventlisteners.PortalActivateListener;
import io.github.nsdigirolamo.powerportals.eventlisteners.PortalWaterListener;
import io.github.nsdigirolamo.powerportals.eventlisteners.TeleportTriggerListener;
import io.github.nsdigirolamo.powerportals.utils.StorageUtil;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.FileNotFoundException;

public final class PowerPortals extends JavaPlugin {

    public static PowerPortals plugin;
    private FileConfiguration config = getConfig();

    @Override
    public void onEnable() {
        plugin = this;

        config.addDefault("minDistance", 1);
        config.options().copyDefaults(true);
        saveConfig();

        getServer().getPluginManager().registerEvents(new PortalActivateListener(), this);
        getServer().getPluginManager().registerEvents(new TeleportTriggerListener(), this);
        getServer().getPluginManager().registerEvents(new PortalWaterListener(), this);

        this.getCommand("link").setExecutor(new LinkCommand());

        try {
            StorageUtil.loadPowerPortals();
        } catch (FileNotFoundException e) {
            getLogger().warning(ChatColor.RED + "failed to load powerportals.json!");
        }

        getLogger().info(ChatColor.GREEN + "PowerPortals is now enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info(ChatColor.YELLOW + "PowerPortals is now disabled!");
    }

    public static PowerPortals getPlugin() {
        return plugin;
    }
}
