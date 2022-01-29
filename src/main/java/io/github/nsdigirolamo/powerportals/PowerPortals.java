package io.github.nsdigirolamo.powerportals;

import io.github.nsdigirolamo.powerportals.commands.Link;
import io.github.nsdigirolamo.powerportals.commands.Portals;
import io.github.nsdigirolamo.powerportals.listeners.ActivatedPortalListener;
import io.github.nsdigirolamo.powerportals.listeners.TriggeredPortalListener;
import io.github.nsdigirolamo.powerportals.listeners.WaterPortalListener;
import io.github.nsdigirolamo.powerportals.utils.PortalActivationUtil;
import io.github.nsdigirolamo.powerportals.utils.PortalCreationUtil;
import io.github.nsdigirolamo.powerportals.utils.PortalStorageUtil;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class PowerPortals extends JavaPlugin {

    private static PowerPortals plugin;
    private static FileConfiguration config;

    @Override
    public void onEnable() {

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
        PortalActivationUtil.deactivateAllPortals();
        PortalStorageUtil.savePortals();
    }

    public static PowerPortals getPlugin() {
        return plugin;
    }

    public static FileConfiguration config() {
        return config;
    }
}
