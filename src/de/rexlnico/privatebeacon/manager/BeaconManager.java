package de.rexlnico.privatebeacon.manager;

import de.rexlnico.privatebeacon.main.Main;
import net.minecraft.server.v1_16_R3.Position;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class BeaconManager {

    private static HashMap<Location, BeaconClass> beacons;

    public BeaconManager() {
        beacons = new HashMap<>();
        File file = new File("plugins/PrivateBeacon/Beacons.yml");
        YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        if (file.exists()) {
            for (String sLoc : cfg.getStringList("Beacons")) {
                String[] split = sLoc.split("/");
                Location loc = new Location(Bukkit.getWorld(UUID.fromString(split[0])), Integer.parseInt(split[1]), Integer.parseInt(split[2]), Integer.parseInt(split[3]));
                beacons.put(loc, new BeaconClass(loc));
            }
        }
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getMain(), () -> {
            for (BeaconClass beaconClass : beacons.values()) {
                if (beaconClass.getLocation().getChunk().isLoaded()) {
                    beaconClass.update();
                }
            }
        }, 0, 20 * 8);
    }

    public void save() throws IOException {
        ArrayList<String> locs = new ArrayList<>();
        for (Location location : beacons.keySet()) {
            beacons.get(location).save();
            locs.add(location.getWorld().getUID() + "/" + location.getBlockX() + "/" + location.getBlockY() + "/" + location.getBlockZ());
        }
        File file = new File("plugins/PrivateBeacon/Beacons.yml");
        YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        cfg.set("Beacons", locs);
        cfg.save(file);
    }

    public void addBeacon(BeaconClass beaconClass) {
        beacons.put(beaconClass.getLocation(), beaconClass);
    }

    public void removeBeacon(Location location) {
        BeaconClass beaconClass = beacons.remove(location);
        beaconClass.delete();
        try {
            save();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public BeaconClass getBeacon(Location location) {
        return beacons.get(location);
    }

}
