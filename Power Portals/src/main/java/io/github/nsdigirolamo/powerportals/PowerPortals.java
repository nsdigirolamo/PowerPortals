package io.github.nsdigirolamo.powerportals;

import io.github.nsdigirolamo.powerportals.commands.LinkCommand;
import io.github.nsdigirolamo.powerportals.eventlisteners.PortalCreationListener;
import io.github.nsdigirolamo.powerportals.utils.PortalStorageUtil;
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

        getServer().getPluginManager().registerEvents(new PortalCreationListener(), this);

        this.getCommand("link").setExecutor(new LinkCommand());

        try {
            PortalStorageUtil.loadPowerPortals();
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
