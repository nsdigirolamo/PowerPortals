package io.github.nsdigirolamo.powerportals;

import io.github.nsdigirolamo.powerportals.commands.Link;
import io.github.nsdigirolamo.powerportals.listeners.ActivatedPortalListener;
import io.github.nsdigirolamo.powerportals.listeners.TriggeredPortalListener;
import io.github.nsdigirolamo.powerportals.utils.PortalCreationUtil;
import org.bukkit.plugin.java.JavaPlugin;

public final class PowerPortals extends JavaPlugin {

    private static PowerPortals plugin;

    @Override
    public void onEnable() {
        plugin = this;
        PortalCreationUtil.loadDesigns();
        this.getCommand("link").setExecutor(new Link());
        getServer().getPluginManager().registerEvents(new ActivatedPortalListener(), this);
        getServer().getPluginManager().registerEvents(new TriggeredPortalListener(), this);
    }

    @Override
    public void onDisable() {
    }

    public static PowerPortals getPlugin() {
        return plugin;
    }
}
