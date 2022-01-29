package io.github.nsdigirolamo.powerportals.listeners;

import io.github.nsdigirolamo.powerportals.structures.PowerPortal;
import io.github.nsdigirolamo.powerportals.utils.PortalActivationUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.metadata.MetadataValue;

import java.util.List;

/**
 * PlayerQuitListener listens for any players leaving the game.
 */
public class PlayerQuitListener implements Listener {

    /**
     * Listens for players leaving and deactivates any portals they might have activated.
     * @param event The PlayerQuitEvent
     */
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {

        Player player = event.getPlayer();
        List<MetadataValue> values = player.getMetadata("activatedPortal");

        if (!values.isEmpty() && values.get(0).value() instanceof PowerPortal) {
            PowerPortal portal = (PowerPortal) values.get(0).value();
            PortalActivationUtil.deactivatePortal(player, portal);
        }
    }
}
