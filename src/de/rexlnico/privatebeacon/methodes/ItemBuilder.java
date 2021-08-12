package de.rexlnico.privatebeacon.methodes;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.craftbukkit.libs.org.apache.commons.codec.binary.Base64;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffectType;


public class ItemBuilder {
    private ItemStack item;

    public ItemBuilder(Material material, int amount) {
        this.item = new ItemStack(material, amount);
    }

    public ItemBuilder(Material material) {
        this.item = new ItemStack(material, 1);
    }

    public ItemBuilder(Material material, int amount, int data) {
        /*  30 */
        this.item = new ItemStack(material, amount, (short) data);
    }

    public ItemBuilder enchantIftrue(Boolean b) {
        if (b) {
            enchant(Enchantment.ARROW_DAMAGE, 1);
            addFlags(ItemFlag.HIDE_ENCHANTS);
        }
        return this;
    }


    public static void fillInv(Inventory inv, Material material) {
        int slots = inv.getSize();
        for (int i = 0; i < slots; i++) {
            inv.setItem(i, new ItemStack(material));
        }
    }

    public static void fillInv(Inventory inv, ItemStack material) {
        int slots = inv.getSize();
        for (int i = 0; i < slots; i++) {
            inv.setItem(i, material);
        }
    }

    public static void fillInv(Inventory inv) {
        int slots = inv.getSize();
        for (int i = 0; i < slots; i++) {
            inv.setItem(i, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE, 1, 15).setName("§8").build());
        }
    }

    public ItemBuilder setTexture(String url) {
        ItemStack head = this.item;
        if (head.getType() != Material.PLAYER_HEAD) return this;
        if (url.isEmpty()) return this;


        SkullMeta headMeta = (SkullMeta) head.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        byte[] encodedData = Base64.encodeBase64(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes());
        profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
        Field profileField = null;
        try {
            profileField = headMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(headMeta, profile);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        head.setItemMeta(headMeta);
        this.item = head;
        return this;
    }

    public ItemBuilder(ItemStack item) {
        this.item = item;
    }

    public ItemBuilder setData(int data) {
        this.item.setDurability((short) data);
        return this;
    }

    public ItemBuilder setMaterial(Material m) {
        this.item.setType(m);
        return this;
    }

    public ItemBuilder setAmount(int amount) {
        this.item.setAmount(amount);
        return this;
    }

    public ItemBuilder setName(String name) {
        ItemMeta m = this.item.getItemMeta();
        m.setDisplayName(name);
        this.item.setItemMeta(m);
        return this;
    }

    public ItemBuilder setLore(String lore) {
        String[] loreS = lore.split("//");
        ItemMeta m = this.item.getItemMeta();
        m.setLore(Arrays.asList(loreS));
        this.item.setItemMeta(m);
        return this;
    }

    public ItemBuilder setLore(List<String> lore) {
        ItemMeta m = this.item.getItemMeta();
        m.setLore(lore);
        this.item.setItemMeta(m);
        return this;
    }

    public ItemBuilder enchant(Enchantment ench, int lvl) {
        this.item.addUnsafeEnchantment(ench, lvl);
        return this;
    }

    public ItemBuilder addFlags(ItemFlag... flag) {
        ItemMeta m = this.item.getItemMeta();
        m.addItemFlags(flag);
        this.item.setItemMeta(m);
        return this;
    }

    public ItemBuilder setLeatherColor(Color color) {
        LeatherArmorMeta m = (LeatherArmorMeta) this.item.getItemMeta();
        m.setColor(color);
        this.item.setItemMeta(m);
        return this;
    }

    public ItemBuilder setSkullOwner(String owner) {
        SkullMeta m = (SkullMeta) this.item.getItemMeta();
        m.setOwner(owner);
        this.item.setItemMeta(m);
        return this;
    }

    public ItemBuilder enchant() {
        enchant(Enchantment.KNOCKBACK, 1);
        addFlags(ItemFlag.HIDE_ENCHANTS);
        return this;
    }

    public ItemBuilder setPotionType(PotionEffectType type) {
        PotionMeta m = (PotionMeta) this.item.getItemMeta();
        m.setMainEffect(type);
        this.item.setItemMeta(m);
        return this;
    }

    public ItemBuilder setBookAuthor(String author) {
        BookMeta m = (BookMeta) this.item.getItemMeta();
        m.setAuthor(author);
        this.item.setItemMeta(m);
        return this;
    }

    public ItemBuilder setBookContent(String... pages) {
        BookMeta m = (BookMeta) this.item.getItemMeta();
        m.setPages(pages);
        this.item.setItemMeta(m);
        return this;
    }

    public ItemBuilder setBookTitle(String title) {
        BookMeta m = (BookMeta) this.item.getItemMeta();
        m.setTitle(title);
        this.item.setItemMeta(m);
        return this;
    }

    public ItemBuilder setBookMeta(String title, String author, String... pages) {
        BookMeta m = (BookMeta) this.item.getItemMeta();
        m.setTitle(title);
        m.setAuthor(author);
        m.setPages(pages);
        this.item.setItemMeta(m);
        return this;
    }

    public ItemBuilder setSkullTexture(String base64) {
        ItemMeta m = this.item.getItemMeta();
        GameProfile profile = new GameProfile(java.util.UUID.randomUUID(), null);
        profile.getProperties().put("textures", new Property("textures", base64));
        Field profileField = null;
        try {
            profileField = m.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(m, profile);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        this.item.setItemMeta(m);
        return this;
    }

    public ItemStack build() {
        return this.item;
    }
}