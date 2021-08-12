package de.rexlnico.privatebeacon.listeners;

import com.sun.org.apache.bcel.internal.generic.SWITCH;
import de.rexlnico.privatebeacon.main.Main;
import de.rexlnico.privatebeacon.manager.BeaconClass;
import de.rexlnico.privatebeacon.manager.BeaconEffects;
import de.rexlnico.privatebeacon.methodes.Config;
import de.rexlnico.privatebeacon.methodes.ConfigValuesOther;
import de.rexlnico.privatebeacon.methodes.ItemBuilder;
import org.bukkit.*;
import org.bukkit.block.Beacon;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InvClick implements Listener {

    public static HashMap<Player, Location> locs = new HashMap<>();
    public static HashMap<Player, Integer> whitelistPage = new HashMap<>();

    @EventHandler
    public void on(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        if (e.getView().getTitle().equals("§bBeacon")) {
            if (!(e.getClickedInventory() instanceof PlayerInventory) && (e.getSlot() != 49 || !Config.getValueAsBoolean(ConfigValuesOther.RequiresMaterialForEffectChange))) {
                e.setCancelled(true);
                Beacon beaconState = (Beacon) locs.get(player).getBlock().getState();
                BeaconClass beaconClass = Main.getBeaconManager().getBeacon(locs.get(player));
                if (e.getCurrentItem() == null) return;
                if (e.getCurrentItem().getType().equals(Material.ENDER_EYE)) {
                    if (e.getClickedInventory().getItem(49) != null) {
                        ItemStack item = e.getClickedInventory().getItem(49);
                        if (item.getType().equals(Material.DIAMOND) || item.getType().equals(Material.EMERALD) || item.getType().equals(Material.NETHERITE_INGOT) || item.getType().equals(Material.IRON_INGOT) || item.getType().equals(Material.GOLD_INGOT)) {
                            int range = 0;
                            switch (item.getType()) {
                                case IRON_INGOT:
                                    range = Config.getValueAsInteger(ConfigValuesOther.IronIngotRange);
                                    break;
                                case GOLD_INGOT:
                                    range = Config.getValueAsInteger(ConfigValuesOther.GoldIngotRange);
                                    break;
                                case DIAMOND:
                                    range = Config.getValueAsInteger(ConfigValuesOther.DiamondRange);
                                    break;
                                case EMERALD:
                                    range = Config.getValueAsInteger(ConfigValuesOther.EmeraldRange);
                                    break;
                                case NETHERITE_INGOT:
                                    range = Config.getValueAsInteger(ConfigValuesOther.NetheriteIngotRange);
                                    break;
                            }
                            if (range > 0 && (Config.getValueAsInteger(ConfigValuesOther.MaxRange) == -1 || beaconClass.getRange() + range <= Config.getValueAsInteger(ConfigValuesOther.MaxRange))) {
                                beaconClass.addRange(range);
                                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1.2f);
                                if (item.getAmount() > 1) {
                                    item.setAmount(item.getAmount() - 1);
                                } else {
                                    e.getClickedInventory().setItem(49, null);
                                    item = new ItemStack(Material.AIR);
                                }
                            }
                            Inventory inventory = Bukkit.createInventory(null, 9 * 6, "§bBeacon");
                            ItemBuilder.fillInv(inventory);
                            ItemStack noEffect = new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setName("§5").build();
                            ItemStack highlight = new ItemBuilder(Material.ORANGE_STAINED_GLASS_PANE).setName("§5").build();
                            inventory.setItem(37, new ItemBuilder(Material.PAPER).setName("§bWhitelist").build());
                            inventory.setItem(43, new ItemBuilder(Material.ENDER_EYE).setName("§aAdd Range").setLore(getLore(beaconClass.getRange())).build());
                            inventory.setItem(49, item);
                            inventory.setItem(48, highlight);
                            inventory.setItem(50, highlight);
                            inventory.setItem(40, highlight);
                            inventory.setItem(39, highlight);
                            inventory.setItem(41, highlight);
                            inventory.setItem(22, beaconClass.isCanWhitelistedEdit() ? new ItemBuilder(Material.LIME_DYE).setName("§aWhitelisted can edit Beacon").build() : new ItemBuilder(Material.RED_DYE).setName("§cWhitelisted cant edit Beacon").build());
                            inventory.setItem(11, beaconClass.getPrimary() == null ? new ItemBuilder(noEffect.clone()).setName("§cPrimary effect").build() : beaconClass.getPrimary().getIcon());
                            inventory.setItem(15, beaconClass.getSecondary() == null ? new ItemBuilder(noEffect.clone()).setName("§cSecondary effect").build() : beaconClass.getSecondary().getIcon());
                            Location loc = locs.remove(player);
                            player.openInventory(inventory);
                            locs.put(player, loc);
                        }
                    } else {
                        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_BREAK, 1, 1);
                    }
                } else if (e.getSlot() == 11) {
                    Inventory inventory = Bukkit.createInventory(null, 9 * 6, "§cPrimary effects");
                    ItemBuilder.fillInv(inventory);
                    int slot = 0;
                    for (BeaconEffects beaconEffects : BeaconEffects.effects) {
                        if (beaconEffects.getLv1() > 0) {
                            inventory.setItem(slot, beaconEffects.getIcon());
                            slot++;
                        }
                    }
                    Location loc = locs.remove(player);
                    if (!Config.getValueAsBoolean(ConfigValuesOther.RequiresMaterialForEffectChange)) {
                        if (e.getInventory().getItem(49) != null && Config.getValueAsBoolean(ConfigValuesOther.RequiresMaterialForEffectChange)) {
                            player.getWorld().dropItem(player.getLocation(), e.getInventory().getItem(49));
                        }
                    } else {
                        ItemStack highlight = new ItemBuilder(Material.ORANGE_STAINED_GLASS_PANE).setName("§5").build();
                        inventory.setItem(49, e.getInventory().getItem(49));
                        inventory.setItem(48, highlight);
                        inventory.setItem(50, highlight);
                        inventory.setItem(40, highlight);
                        inventory.setItem(39, highlight);
                        inventory.setItem(41, highlight);
                    }
                    player.openInventory(inventory);
                    locs.put(player, loc);
                } else if (e.getSlot() == 15) {
                    Inventory inventory = Bukkit.createInventory(null, 9 * 6, "§cSecondary effects");
                    ItemBuilder.fillInv(inventory);
                    int slot = 0;
                    for (BeaconEffects beaconEffects : BeaconEffects.effects) {
                        if (beaconEffects.getLv2() > 0) {
                            inventory.setItem(slot, beaconEffects.getIcon());
                            slot++;
                        }
                    }
                    Location loc = locs.remove(player);
                    if (!Config.getValueAsBoolean(ConfigValuesOther.RequiresMaterialForEffectChange)) {
                        if (e.getInventory().getItem(49) != null && Config.getValueAsBoolean(ConfigValuesOther.RequiresMaterialForEffectChange)) {
                            player.getWorld().dropItem(player.getLocation(), e.getInventory().getItem(49));
                        }
                    } else {
                        ItemStack highlight = new ItemBuilder(Material.ORANGE_STAINED_GLASS_PANE).setName("§5").build();
                        inventory.setItem(49, e.getInventory().getItem(49));
                        inventory.setItem(48, highlight);
                        inventory.setItem(50, highlight);
                        inventory.setItem(40, highlight);
                        inventory.setItem(39, highlight);
                        inventory.setItem(41, highlight);
                    }
                    player.openInventory(inventory);
                    locs.put(player, loc);
                } else if (e.getSlot() == 22 && beaconClass.getOwner().equals(player.getUniqueId().toString())) {
                    beaconClass.setCanWhitelistedEdit(!beaconClass.isCanWhitelistedEdit());
                    player.playSound(player.getLocation(), Sound.BLOCK_LAVA_POP, 1, 1);
                    e.getInventory().setItem(22, beaconClass.isCanWhitelistedEdit() ? new ItemBuilder(Material.LIME_DYE).setName("§aWhitelisted can edit Beacon").build() : new ItemBuilder(Material.RED_DYE).setName("§cWhitelisted cant edit Beacon").build());
                } else if (e.getCurrentItem().getType().equals(Material.PAPER)) {
                    Inventory inventory = Bukkit.createInventory(null, 9 * 6, "§bWhitelist >> 1");
                    ItemBuilder.fillInv(inventory);
                    for (int i = 0; i < Math.min(45, Bukkit.getOfflinePlayers().length); i++) {
                        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayers()[i];
                        if (offlinePlayer != null) {
                            inventory.setItem(i, new ItemBuilder(Material.PLAYER_HEAD).setName((beaconClass.getWhitelist().contains(offlinePlayer.getUniqueId().toString()) ? "§a" : "§c") + offlinePlayer.getName()).setSkullOwner(offlinePlayer.getName()).build());
                        }
                    }
                    Location loc = locs.remove(player);
                    if (e.getInventory().getItem(49) != null && Config.getValueAsBoolean(ConfigValuesOther.RequiresMaterialForEffectChange)) {
                        player.getWorld().dropItem(player.getLocation(), e.getInventory().getItem(49));
                    }
                    if (Bukkit.getOfflinePlayers().length >= 46) {
                        inventory.setItem(52, new ItemBuilder(Material.PLAYER_HEAD).setName("§7Next Page").setSkullTexture("http://textures.minecraft.net/texture/c86185b1d519ade585f184c34f3f3e20bb641deb879e81378e4eaf209287").build());
                    }
                    player.openInventory(inventory);
                    locs.put(player, loc);
                    whitelistPage.remove(player);
                    whitelistPage.put(player, 1);
                }
            }
        } else if (e.getView().getTitle().startsWith("§bWhitelist")) {
            if (!(e.getClickedInventory() instanceof PlayerInventory)) {
                e.setCancelled(true);
                BeaconClass beaconClass = Main.getBeaconManager().getBeacon(locs.get(player));
                if (e.getCurrentItem() != null && e.getCurrentItem().getType() != Material.BLACK_STAINED_GLASS_PANE) {
                    if (e.getSlot() == 52 && e.getInventory().getItem(52).getType() != Material.BLACK_STAINED_GLASS_PANE) {
                        Inventory inventory = Bukkit.createInventory(null, 9 * 6, "§bWhitelist >> " + (whitelistPage.get(player) + 1));
                        ItemBuilder.fillInv(inventory);
                        for (int i = (45 * whitelistPage.get(player)) - 45; i < 45 * (whitelistPage.get(player) + 1); i++) {
                            if (Bukkit.getOfflinePlayers().length < i)
                                break;
                            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayers()[i];
                            if (offlinePlayer != null) {
                                inventory.setItem(i, new ItemBuilder(Material.PLAYER_HEAD).setName((beaconClass.getWhitelist().contains(offlinePlayer.getUniqueId().toString()) ? "§a" : "§c") + offlinePlayer.getName()).setSkullOwner(offlinePlayer.getName()).build());
                            }
                        }
                        if (Bukkit.getOfflinePlayers().length >= 45 * (whitelistPage.get(player) + 1) + 1) {
                            inventory.setItem(52, new ItemBuilder(Material.PLAYER_HEAD).setName("§7Next Page").setSkullTexture("http://textures.minecraft.net/texture/c86185b1d519ade585f184c34f3f3e20bb641deb879e81378e4eaf209287").build());
                        }
                        if (Bukkit.getOfflinePlayers().length >= (45 * whitelistPage.get(player)) - 1) {
                            inventory.setItem(46, new ItemBuilder(Material.PLAYER_HEAD).setName("§7Previous Page").setSkullTexture("http://textures.minecraft.net/texture/ad73cf66d31b83cd8b8644c15958c1b73c8d97323b801170c1d8864bb6a846d").build());
                        }
                        int current = whitelistPage.remove(player);
                        whitelistPage.put(player, current + 1);
                        Location loc = locs.remove(player);
                        player.openInventory(inventory);
                        locs.put(player, loc);
                        return;
                    } else if (e.getSlot() == 46 && e.getInventory().getItem(52).getType() != Material.BLACK_STAINED_GLASS_PANE) {
                        Inventory inventory = Bukkit.createInventory(null, 9 * 6, "§bWhitelist >> " + (whitelistPage.get(player) + 1));
                        ItemBuilder.fillInv(inventory);
                        for (int i = (45 * whitelistPage.get(player)) - 45; i < 45 * (whitelistPage.get(player) - 1); i++) {
                            if (Bukkit.getOfflinePlayers().length <= i)
                                break;
                            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayers()[i];
                            if (offlinePlayer != null) {
                                inventory.setItem(i, new ItemBuilder(Material.PLAYER_HEAD).setName((beaconClass.getWhitelist().contains(offlinePlayer.getUniqueId().toString()) ? "§a" : "§c") + offlinePlayer.getName()).setSkullOwner(offlinePlayer.getName()).build());
                            }
                        }
                        if (Bukkit.getOfflinePlayers().length >= 45 * (whitelistPage.get(player) + 1) + 1) {
                            inventory.setItem(52, new ItemBuilder(Material.PLAYER_HEAD).setName("§7Next Page").setSkullTexture("http://textures.minecraft.net/texture/c86185b1d519ade585f184c34f3f3e20bb641deb879e81378e4eaf209287").build());
                        }
                        if (Bukkit.getOfflinePlayers().length >= (45 * whitelistPage.get(player)) - 1) {
                            inventory.setItem(46, new ItemBuilder(Material.PLAYER_HEAD).setName("§7Previous Page").setSkullTexture("http://textures.minecraft.net/texture/ad73cf66d31b83cd8b8644c15958c1b73c8d97323b801170c1d8864bb6a846d").build());
                        }
                        int current = whitelistPage.remove(player);
                        whitelistPage.put(player, current - 1);
                        Location loc = locs.remove(player);
                        player.openInventory(inventory);
                        locs.put(player, loc);
                        return;
                    }
                    OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(e.getCurrentItem().getItemMeta().getDisplayName().replaceFirst("§a", "").replaceFirst("§c", ""));
                    if (beaconClass.getWhitelist().contains(offlinePlayer.getUniqueId().toString())) {
                        beaconClass.removeWhitelist(offlinePlayer.getUniqueId().toString());
                        player.playSound(player.getLocation(), Sound.BLOCK_LAVA_POP, 1, 1.3f);
                    } else {
                        beaconClass.addWhitelist(offlinePlayer.getUniqueId().toString());
                        player.playSound(player.getLocation(), Sound.BLOCK_LAVA_POP, 1, 1.3f);
                    }
                    Inventory inventory = Bukkit.createInventory(null, 9 * 6, "§bWhitelist >> " + whitelistPage.get(player));
                    ItemBuilder.fillInv(inventory);
                    for (int i = (45 * whitelistPage.get(player)) - 45; i < 45 * (whitelistPage.get(player) + 1); i++) {
                        if (Bukkit.getOfflinePlayers().length <= i)
                            break;
                        offlinePlayer = Bukkit.getOfflinePlayers()[i];
                        if (offlinePlayer != null) {
                            inventory.setItem(i, new ItemBuilder(Material.PLAYER_HEAD).setName((beaconClass.getWhitelist().contains(offlinePlayer.getUniqueId().toString()) ? "§a" : "§c") + offlinePlayer.getName()).setSkullOwner(offlinePlayer.getName()).build());
                        }
                    }
                    if (Bukkit.getOfflinePlayers().length >= 45 * (whitelistPage.get(player) + 1) + 1) {
                        inventory.setItem(52, new ItemBuilder(Material.PLAYER_HEAD).setName("§7Next Page").setSkullTexture("http://textures.minecraft.net/texture/c86185b1d519ade585f184c34f3f3e20bb641deb879e81378e4eaf209287").build());
                    }
                    if (Bukkit.getOfflinePlayers().length >= (45 * whitelistPage.get(player)) - 1) {
                        inventory.setItem(46, new ItemBuilder(Material.PLAYER_HEAD).setName("§7Previous Page").setSkullTexture("http://textures.minecraft.net/texture/ad73cf66d31b83cd8b8644c15958c1b73c8d97323b801170c1d8864bb6a846d").build());
                    }
                    Location loc = locs.remove(player);
                    player.openInventory(inventory);
                    locs.put(player, loc);
                }
            }
        } else if (e.getView().getTitle().equals("§cPrimary effects")) {
            if (!(e.getClickedInventory() instanceof PlayerInventory) && (e.getSlot() != 49 || !Config.getValueAsBoolean(ConfigValuesOther.RequiresMaterialForEffectChange))) {
                e.setCancelled(true);
                Beacon beaconState = (Beacon) locs.get(player).getBlock().getState();
                BeaconClass beaconClass = Main.getBeaconManager().getBeacon(locs.get(player));
                if (e.getCurrentItem() != null && e.getCurrentItem().getType() != Material.BLACK_STAINED_GLASS_PANE && e.getCurrentItem().getType() != Material.BLACK_STAINED_GLASS_PANE && e.getSlot() != 49) {
                    BeaconEffects effect = BeaconEffects.effects.get(e.getSlot());
                    if (beaconState.getTier() >= effect.getTier()) {
                        if (Config.getValueAsBoolean(ConfigValuesOther.RequiresMaterialForEffectChange)) {
                            ItemStack item = e.getClickedInventory().getItem(49);
                            if (item != null && (item.getType() == Material.IRON_INGOT || item.getType() == Material.GOLD_INGOT || item.getType() == Material.DIAMOND || item.getType() == Material.EMERALD || item.getType() == Material.NETHERITE_INGOT)) {
                                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1.2f);
                                if (item.getAmount() > 1) {
                                    item.setAmount(item.getAmount() - 1);
                                } else {
                                    e.getClickedInventory().setItem(49, null);
                                    item = new ItemStack(Material.AIR);
                                }
                                beaconClass.setPrimary(effect);
                                Inventory inventory = Bukkit.createInventory(null, 9 * 6, "§bBeacon");
                                ItemBuilder.fillInv(inventory);
                                ItemStack noEffect = new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setName("§5").build();
                                ItemStack highlight = new ItemBuilder(Material.ORANGE_STAINED_GLASS_PANE).setName("§5").build();
                                inventory.setItem(37, new ItemBuilder(Material.PAPER).setName("§bWhitelist").build());
                                inventory.setItem(43, new ItemBuilder(Material.ENDER_EYE).setName("§aAdd Range").setLore(getLore(beaconClass.getRange())).build());
                                inventory.setItem(49, item);
                                inventory.setItem(48, highlight);
                                inventory.setItem(50, highlight);
                                inventory.setItem(40, highlight);
                                inventory.setItem(39, highlight);
                                inventory.setItem(41, highlight);
                                inventory.setItem(22, beaconClass.isCanWhitelistedEdit() ? new ItemBuilder(Material.LIME_DYE).setName("§aWhitelisted can edit Beacon").build() : new ItemBuilder(Material.RED_DYE).setName("§cWhitelisted cant edit Beacon").build());
                                inventory.setItem(11, beaconClass.getPrimary() == null ? new ItemBuilder(noEffect.clone()).setName("§cPrimary effect").build() : beaconClass.getPrimary().getIcon());
                                inventory.setItem(15, beaconClass.getSecondary() == null ? new ItemBuilder(noEffect.clone()).setName("§cSecondary effect").build() : beaconClass.getSecondary().getIcon());
                                Location loc = locs.remove(player);
                                player.openInventory(inventory);
                                locs.put(player, loc);
                            } else {
                                player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 1, 1);
                            }
                        } else {
                            beaconClass.setPrimary(effect);
                            Inventory inventory = Bukkit.createInventory(null, 9 * 6, "§bBeacon");
                            ItemBuilder.fillInv(inventory);
                            ItemStack noEffect = new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setName("§5").build();
                            inventory.setItem(37, new ItemBuilder(Material.PAPER).setName("§bWhitelist").build());
                            inventory.setItem(43, new ItemBuilder(Material.ENDER_EYE).setName("§aAdd Range").setLore(getLore(beaconClass.getRange())).build());
                            inventory.setItem(11, beaconClass.getPrimary() == null ? new ItemBuilder(noEffect.clone()).setName("§cPrimary effect").build() : beaconClass.getPrimary().getIcon());
                            inventory.setItem(15, beaconClass.getSecondary() == null ? new ItemBuilder(noEffect.clone()).setName("§cSecondary effect").build() : beaconClass.getSecondary().getIcon());
                            inventory.setItem(22, beaconClass.isCanWhitelistedEdit() ? new ItemBuilder(Material.LIME_DYE).setName("§aWhitelisted can edit Beacon").build() : new ItemBuilder(Material.RED_DYE).setName("§cWhitelisted cant edit Beacon").build());
                            Location loc = locs.remove(player);
                            player.openInventory(inventory);
                            locs.put(player, loc);
                        }
                    } else {
                        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 1, 1);
                    }

                }
            }
        } else if (e.getView().getTitle().equals("§cSecondary effects")) {
            if (!(e.getClickedInventory() instanceof PlayerInventory) && (e.getSlot() != 49 || !Config.getValueAsBoolean(ConfigValuesOther.RequiresMaterialForEffectChange))) {
                e.setCancelled(true);
                Beacon beaconState = (Beacon) locs.get(player).getBlock().getState();
                BeaconClass beaconClass = Main.getBeaconManager().getBeacon(locs.get(player));
                if (e.getCurrentItem() != null && e.getCurrentItem().getType() != Material.BLACK_STAINED_GLASS_PANE && e.getCurrentItem().getType() != Material.BLACK_STAINED_GLASS_PANE && e.getSlot() != 49) {
                    BeaconEffects effect = BeaconEffects.effects.get(e.getSlot());
                    if (beaconState.getTier() >= effect.getTier()) {
                        if (Config.getValueAsBoolean(ConfigValuesOther.RequiresMaterialForEffectChange)) {
                            ItemStack item = e.getClickedInventory().getItem(49);
                            if (item != null && (item.getType() == Material.IRON_INGOT || item.getType() == Material.GOLD_INGOT || item.getType() == Material.DIAMOND || item.getType() == Material.EMERALD || item.getType() == Material.NETHERITE_INGOT)) {
                                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1.2f);
                                if (item.getAmount() > 1) {
                                    item.setAmount(item.getAmount() - 1);
                                } else {
                                    e.getClickedInventory().setItem(49, null);
                                    item = new ItemStack(Material.AIR);
                                }
                                beaconClass.setSecondary(effect);
                                Inventory inventory = Bukkit.createInventory(null, 9 * 6, "§bBeacon");
                                ItemBuilder.fillInv(inventory);
                                ItemStack noEffect = new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setName("§5").build();
                                ItemStack highlight = new ItemBuilder(Material.ORANGE_STAINED_GLASS_PANE).setName("§5").build();
                                inventory.setItem(37, new ItemBuilder(Material.PAPER).setName("§bWhitelist").build());
                                inventory.setItem(43, new ItemBuilder(Material.ENDER_EYE).setName("§aAdd Range").setLore(getLore(beaconClass.getRange())).build());
                                inventory.setItem(49, item);
                                inventory.setItem(48, highlight);
                                inventory.setItem(50, highlight);
                                inventory.setItem(40, highlight);
                                inventory.setItem(39, highlight);
                                inventory.setItem(41, highlight);
                                inventory.setItem(22, beaconClass.isCanWhitelistedEdit() ? new ItemBuilder(Material.LIME_DYE).setName("§aWhitelisted can edit Beacon").build() : new ItemBuilder(Material.RED_DYE).setName("§cWhitelisted cant edit Beacon").build());
                                inventory.setItem(11, beaconClass.getPrimary() == null ? new ItemBuilder(noEffect.clone()).setName("§cPrimary effect").build() : beaconClass.getPrimary().getIcon());
                                inventory.setItem(15, beaconClass.getSecondary() == null ? new ItemBuilder(noEffect.clone()).setName("§cSecondary effect").build() : beaconClass.getSecondary().getIcon());
                                Location loc = locs.remove(player);
                                player.openInventory(inventory);
                                locs.put(player, loc);
                            } else {
                                player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 1, 1);
                            }
                        } else {
                            beaconClass.setSecondary(effect);
                            Inventory inventory = Bukkit.createInventory(null, 9 * 6, "§bBeacon");
                            ItemBuilder.fillInv(inventory);
                            ItemStack noEffect = new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setName("§5").build();
                            ItemStack highlight = new ItemBuilder(Material.ORANGE_STAINED_GLASS_PANE).setName("§5").build();
                            inventory.setItem(37, new ItemBuilder(Material.PAPER).setName("§bWhitelist").build());
                            inventory.setItem(43, new ItemBuilder(Material.ENDER_EYE).setName("§aAdd Range").setLore(getLore(beaconClass.getRange())).build());
                            inventory.setItem(11, beaconClass.getPrimary() == null ? new ItemBuilder(noEffect.clone()).setName("§cPrimary effect").build() : beaconClass.getPrimary().getIcon());
                            inventory.setItem(15, beaconClass.getSecondary() == null ? new ItemBuilder(noEffect.clone()).setName("§cSecondary effect").build() : beaconClass.getSecondary().getIcon());
                            inventory.setItem(22, beaconClass.isCanWhitelistedEdit() ? new ItemBuilder(Material.LIME_DYE).setName("§aWhitelisted can edit Beacon").build() : new ItemBuilder(Material.RED_DYE).setName("§cWhitelisted cant edit Beacon").build());
                            Location loc = locs.remove(player);
                            player.openInventory(inventory);
                            locs.put(player, loc);
                        }
                    } else {
                        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 1, 1);
                    }

                }
            }
        }
    }


    @EventHandler
    public void on(InventoryCloseEvent e) {
        if (e.getView().getTitle().equals("§bBeacon") && locs.containsKey(e.getPlayer()) && e.getInventory().getItem(49) != null && Config.getValueAsBoolean(ConfigValuesOther.RequiresMaterialForEffectChange)) {
            e.getPlayer().getWorld().dropItem(e.getPlayer().getLocation(), e.getInventory().getItem(49));
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
