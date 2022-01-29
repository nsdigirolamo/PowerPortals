package io.github.nsdigirolamo.powerportals;

import io.github.nsdigirolamo.powerportals.commands.Link;
import io.github.nsdigirolamo.powerportals.commands.Portals;
import io.github.nsdigirolamo.powerportals.listeners.ActivatedPortalListener;
import io.github.nsdigirolamo.powerportals.listeners.TriggeredPortalListener;
import io.github.nsdigirolamo.powerportals.listeners.WaterPortalListener;
import io.github.nsdigirolamo.powerportals.utils.PortalCreationUtil;
import io.github.nsdigirolamo.powerportals.utils.PortalStorageUtil;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class PowerPortals extends JavaPlugin {

    private static PowerPortals plugin;
    private static FileConfiguration config;

    @Override
    public void onEnable() {

        config = this.getConfig();
        config.addDefault("database.host", "localhost");
        config.addDefault("database.port", 3306);
        config.addDefault("database.database", "powerportals");
        config.addDefault("database.user", "admin");
        config.addDefault("database.password", "password");
        config.options().copyDefaults(true);
        saveConfig();

        plugin = this;

        PortalCreationUtil.loadDesigns();
        PortalStorageUtil.loadPortals();

        this.getCommand("link").setExecutor(new Link());
        this.getCommand("portals").setExecutor(new Portals());
        getServer().getPluginManager().registerEvents(new ActivatedPortalListener(), this);
        getServer().getPluginManager().registerEvents(new TriggeredPortalListener(), this);
        getServer().getPluginManager().registerEvents(new WaterPortalListener(), this);
    }

    @Override
    public void onDisable() {
    }

    public static PowerPortals getPlugin() {
        return plugin;
    }

    public static FileConfiguration config() {
        return config;
    }
}
