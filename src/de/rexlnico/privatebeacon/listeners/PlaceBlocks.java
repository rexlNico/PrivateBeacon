package de.rexlnico.privatebeacon.listeners;

import de.rexlnico.privatebeacon.main.Main;
import de.rexlnico.privatebeacon.manager.BeaconClass;
import de.rexlnico.privatebeacon.methodes.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityPotionEffectEvent;

public class PlaceBlocks implements Listener {

    @EventHandler
    public void on(BlockPlaceEvent e) {
        if (e.getBlock().getType().equals(Material.BEACON)) {
            BeaconClass beaconClass = new BeaconClass(e.getBlock().getLocation());
            beaconClass.setOwner(e.getPlayer().getUniqueId().toString());
            Main.getBeaconManager().addBeacon(beaconClass);
            System.out.println(e.getBlock().getWorld());
        }
    }

    @EventHandler
    public void on(BlockBreakEvent e) {
        if (e.getBlock().getType().equals(Material.BEACON)) {
            BeaconClass beaconClass = Main.getBeaconManager().getBeacon(e.getBlock().getLocation());
            if (beaconClass != null && (beaconClass.getOwner().equals(e.getPlayer().getUniqueId().toString()) || (beaconClass.isCanWhitelistedEdit() && beaconClass.getWhitelist().contains(e.getPlayer().getUniqueId().toString())))) {
                Main.getBeaconManager().removeBeacon(e.getBlock().getLocation());
            }
        }
    }

}
