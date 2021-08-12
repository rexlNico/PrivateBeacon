package de.rexlnico.privatebeacon.listeners;

import de.rexlnico.privatebeacon.main.Main;
import de.rexlnico.privatebeacon.manager.BeaconClass;
import de.rexlnico.privatebeacon.methodes.Config;
import de.rexlnico.privatebeacon.methodes.ConfigValuesOther;
import de.rexlnico.privatebeacon.methodes.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class OpenBeacon implements Listener {

    @EventHandler
    public void on(PlayerInteractEvent e) {
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (e.getClickedBlock().getType().equals(Material.BEACON)) {
                e.setCancelled(true);
                BeaconClass beaconClass = Main.getBeaconManager().getBeacon(e.getClickedBlock().getLocation());
                if (beaconClass == null) {
                    beaconClass = new BeaconClass(e.getClickedBlock().getLocation());
                    beaconClass.setOwner(e.getPlayer().getUniqueId().toString());
                    Main.getBeaconManager().addBeacon(beaconClass);
                }
                if (beaconClass.getOwner().equals(e.getPlayer().getUniqueId().toString()) || (beaconClass.getWhitelist().contains(e.getPlayer().getUniqueId().toString()) && beaconClass.isCanWhitelistedEdit())) {
                    Inventory inventory = Bukkit.createInventory(null, 9 * 6, "§bBeacon");
                    ItemBuilder.fillInv(inventory);
                    ItemStack noEffect = new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setName("§5").build();
                    ItemStack highlight = new ItemBuilder(Material.ORANGE_STAINED_GLASS_PANE).setName("§5").build();
                    inventory.setItem(37, new ItemBuilder(Material.PAPER).setName("§bWhitelist").build());
                    inventory.setItem(43, new ItemBuilder(Material.ENDER_EYE).setName("§aAdd Range").setLore(getLore(beaconClass.getRange())).build());
                    if (Config.getValueAsBoolean(ConfigValuesOther.RequiresMaterialForEffectChange)) {
                        inventory.setItem(49, new ItemBuilder(Material.AIR).build());
                        inventory.setItem(48, highlight);
                        inventory.setItem(50, highlight);
                        inventory.setItem(40, highlight);
                        inventory.setItem(39, highlight);
                        inventory.setItem(41, highlight);
                    }
                    inventory.setItem(22, beaconClass.isCanWhitelistedEdit() ? new ItemBuilder(Material.LIME_DYE).setName("§aWhitelisted can edit Beacon").build() : new ItemBuilder(Material.RED_DYE).setName("§cWhitelisted cant edit Beacon").build());
                    inventory.setItem(11, beaconClass.getPrimary() == null ? new ItemBuilder(noEffect.clone()).setName("§cPrimary effect").build() : beaconClass.getPrimary().getIcon());
                    inventory.setItem(15, beaconClass.getSecondary() == null ? new ItemBuilder(noEffect.clone()).setName("§cSecondary effect").build() : beaconClass.getSecondary().getIcon());
                    InvClick.locs.put(e.getPlayer(), e.getClickedBlock().getLocation());
                    e.getPlayer().openInventory(inventory);
                } else {
                    e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.BLOCK_ANVIL_BREAK, 1, 1);
                }

            }
        }
    }

    public List<String> getLore(int range) {
        List<String> list = new ArrayList<>();
        list.add("§cRange: §c" + range + " / " + (Config.getValueAsInteger(ConfigValuesOther.MaxRange) == -1 ? "∞" : Config.getValueAsInteger(ConfigValuesOther.MaxRange)));
        list.add("");
        if (Config.getValueAsInteger(ConfigValuesOther.IronIngotRange) > 0)
            list.add("§7Iron: " + Config.getValueAsInteger(ConfigValuesOther.IronIngotRange));
        if (Config.getValueAsInteger(ConfigValuesOther.GoldIngotRange) > 0)
            list.add("§6Gold: " + Config.getValueAsInteger(ConfigValuesOther.GoldIngotRange));
        if (Config.getValueAsInteger(ConfigValuesOther.EmeraldRange) > 0)
            list.add("§aEmerald: " + Config.getValueAsInteger(ConfigValuesOther.EmeraldRange));
        if (Config.getValueAsInteger(ConfigValuesOther.DiamondRange) > 0)
            list.add("§bDiamond: " + Config.getValueAsInteger(ConfigValuesOther.DiamondRange));
        if (Config.getValueAsInteger(ConfigValuesOther.NetheriteIngotRange) > 0)
            list.add("§8Netherite: " + Config.getValueAsInteger(ConfigValuesOther.NetheriteIngotRange));
        return list;
    }

}
