package de.rexlnico.privatebeacon.manager;

import net.minecraft.server.v1_16_R3.IBeaconBeam;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.block.Beacon;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class BeaconClass {

    private Location location;
    private int range = 40;
    private ArrayList<String> whitelist;
    private String owner;
    private boolean canWhitelistedEdit;

    private BeaconEffects primary;
    private BeaconEffects secondary;

    private File file;
    private YamlConfiguration cfg;

    public BeaconClass(Location location) {
        this.location = location;
        file = new File("plugins/PrivateBeacon/Beacons/" + location.getWorld().getUID() + ";" + location.getBlockX() + ";" + location.getBlockY() + ";" + location.getBlockZ() + ".yml");
        cfg = YamlConfiguration.loadConfiguration(file);
        if (file.exists()) {
            owner = cfg.getString("Owner");
            whitelist = (ArrayList<String>) cfg.getStringList("Whitelist");
            primary = BeaconEffects.getEffect(cfg.getString("Primary"));
            secondary = BeaconEffects.getEffect(cfg.getString("Secondary"));
            range = cfg.getInt("Range");
            canWhitelistedEdit = cfg.getBoolean("CanWhitelistedEdit");
        } else {
            owner = "";
            primary = null;
            secondary = null;
            whitelist = new ArrayList<>();
            canWhitelistedEdit = false;
        }

    }

    public Location getLocation() {
        return location;
    }

    public int getRange() {
        return range;
    }

    public String getOwner() {
        return owner;
    }

    public void save() throws IOException {
        cfg.set("Owner", owner);
        cfg.set("Whitelist", whitelist);
        cfg.set("Primary", primary == null ? null : primary.getName());
        cfg.set("Secondary", secondary == null ? null : secondary.getName());
        cfg.set("Range", range);
        cfg.set("CanWhitelistedEdit", canWhitelistedEdit);
        cfg.save(file);
    }

    public boolean isCanWhitelistedEdit() {
        return canWhitelistedEdit;
    }

    public void setCanWhitelistedEdit(boolean canWhitelistedEdit) {
        this.canWhitelistedEdit = canWhitelistedEdit;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public void setWhitelist(ArrayList<String> whitelist) {
        this.whitelist = whitelist;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setPrimary(BeaconEffects primary) {
        this.primary = primary;
    }

    public void setSecondary(BeaconEffects secondary) {
        this.secondary = secondary;
    }

    public void delete() {
        file.delete();
    }

    public void addRange(int range) {
        this.range = this.range + range;
    }

    public BeaconEffects getPrimary() {
        return primary;
    }

    public BeaconEffects getSecondary() {
        return secondary;
    }

    public ArrayList<String> getWhitelist() {
        return whitelist;
    }

    public void addWhitelist(String uuid) {
        whitelist.add(uuid);
    }

    public void removeWhitelist(String uuid) {
        whitelist.remove(uuid);
    }

    public void update() {
        for (String list : whitelist) {
            Player player = Bukkit.getPlayer(UUID.fromString(list));
            if (player != null && player.getLocation().getWorld().equals(location.getWorld())) {
                if (player.getLocation().distance(location) <= range) {
                    Beacon beaconState = (Beacon) location.getBlock().getState();
                    if (beaconState.getTier() >= primary.getTier()) {
                        try {
                            if (secondary == null) {
                                player.addPotionEffect(new PotionEffect(primary.getPotionEffect(), 20 * 10, primary.getLv1()-1));
                            } else if (secondary == primary) {
                                if (beaconState.getTier() == 4) {
                                    player.addPotionEffect(new PotionEffect(secondary.getPotionEffect(), 20 * 10, secondary.getLv2()-1));
                                }
                            } else {
                                player.addPotionEffect(new PotionEffect(primary.getPotionEffect(), 20 * 10, primary.getLv1()-1));
                                if (beaconState.getTier() >= secondary.getTier()) {
                                    player.addPotionEffect(new PotionEffect(secondary.getPotionEffect(), 20 * 10, secondary.getLv1()-1));
                                }
                            }
                        } catch (Exception ignored) {
                        }
                    }
                }
            }
        }
    }

}
