package de.rexlnico.privatebeacon.main;

import de.rexlnico.privatebeacon.listeners.InvClick;
import de.rexlnico.privatebeacon.listeners.OpenBeacon;
import de.rexlnico.privatebeacon.listeners.PlaceBlocks;
import de.rexlnico.privatebeacon.manager.BeaconEffects;
import de.rexlnico.privatebeacon.manager.BeaconManager;
import de.rexlnico.privatebeacon.methodes.Config;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class Main extends JavaPlugin {

    private PluginManager pm;
    private static Main main;
    private static BeaconManager beaconManager;

    @Override
    public void onEnable() {
        main = this;
        pm = Bukkit.getPluginManager();
        try {
            Config.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        BeaconEffects.load();
        beaconManager = new BeaconManager();

        pm.registerEvents(new InvClick(), this);
        pm.registerEvents(new OpenBeacon(), this);
        pm.registerEvents(new PlaceBlocks(), this);
    }

    @Override
    public void onDisable() {
        try {
            beaconManager.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.onDisable();
    }

    public static Main getMain() {
        return main;
    }

    public static BeaconManager getBeaconManager() {
        return beaconManager;
    }
}
