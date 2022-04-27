package io.github.nsdigirolamo.powerportals;

import io.github.nsdigirolamo.powerportals.commands.Link;
import io.github.nsdigirolamo.powerportals.commands.Portals;
import io.github.nsdigirolamo.powerportals.commands.Ppassword;
import io.github.nsdigirolamo.powerportals.commands.Pwarp;
import io.github.nsdigirolamo.powerportals.commands.tabcompleters.LinkTabCompleter;
import io.github.nsdigirolamo.powerportals.listeners.ActivationListener;
import io.github.nsdigirolamo.powerportals.listeners.DestructionListener;
import io.github.nsdigirolamo.powerportals.listeners.TriggerListener;
import io.github.nsdigirolamo.powerportals.listeners.WaterInteractionListener;
import io.github.nsdigirolamo.powerportals.utilities.StorageUtility;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class PowerPortals extends JavaPlugin {

    private static PowerPortals plugin;
    private static FileConfiguration config;

    /**
     * @return the PowerPortals plugin.
     */
    public static PowerPortals getPlugin () {
        return plugin;
    }

    /**
     * Performs plugin activities when the plugin is enabled.
     */
    @Override
    public void onEnable() {

        plugin = this;

        config = this.getConfig();
        config.addDefault("maxPortalCount", 20);
        config.options().copyDefaults(true);
        saveConfig();

        StorageUtility.load();
        getServer().getPluginManager().registerEvents(new ActivationListener(), this);
        getServer().getPluginManager().registerEvents(new TriggerListener(), this);
        getServer().getPluginManager().registerEvents(new WaterInteractionListener(), this);
        getServer().getPluginManager().registerEvents(new DestructionListener(), this);
        this.getCommand("link").setExecutor(new Link());
        this.getCommand("link").setTabCompleter(new LinkTabCompleter());
        this.getCommand("portals").setExecutor(new Portals());
        this.getCommand("pwarp").setExecutor(new Pwarp());
        this.getCommand("ppassword").setExecutor(new Ppassword());

        plugin.getLogger().info("PowerPortals is now enabled.");

    }

    /**
     * Performs plugin activities when the plugin is disabled.
     */
    @Override
    public void onDisable() {
        StorageUtility.save();
        plugin.getLogger().info("PowerPortals is now disabled.");
    }
}
