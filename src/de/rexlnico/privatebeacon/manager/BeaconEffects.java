package de.rexlnico.privatebeacon.manager;

import de.rexlnico.privatebeacon.methodes.Config;
import de.rexlnico.privatebeacon.methodes.ConfigValuesEffects;
import de.rexlnico.privatebeacon.methodes.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class BeaconEffects {

//    SPEED("Speed", 1, 0, 1, new ItemBuilder(Material.SUGAR).build(), PotionEffectType.SPEED),
//    HAST("Hast", 1, 0, 1, new ItemBuilder(Material.GOLDEN_PICKAXE).build(), PotionEffectType.FAST_DIGGING),
//    RESISTANCE("Resistance", 2, 0, 1, new ItemBuilder(Material.IRON_CHESTPLATE).build(), PotionEffectType.DAMAGE_RESISTANCE),
//    JUMP_BOOST("Jump boost", 2, 0, 1, new ItemBuilder(Material.IRON_BOOTS).build(), PotionEffectType.JUMP),
//    STRENGTH("Strength", 3, 0, 1, new ItemBuilder(Material.IRON_SWORD).build(), PotionEffectType.INCREASE_DAMAGE),
//    REGENERATION("Regeneration", 4, 0, 1, new ItemBuilder(Material.GOLDEN_APPLE).build(), PotionEffectType.REGENERATION),
//    NIGHT_VISION("Night vision", 2, 0, 1, new ItemBuilder(Material.GLOWSTONE_DUST).build(), PotionEffectType.NIGHT_VISION),
//    WATER_BREATHING("Water breathing", 4, 0, 1, new ItemBuilder(Material.TURTLE_HELMET).build(), PotionEffectType.WATER_BREATHING),
//    INVISIBILITY("Invisibility", 4, 0, 1, new ItemBuilder(Material.GLASS).build(), PotionEffectType.INVISIBILITY);

    public static final List<BeaconEffects> effects = new ArrayList<>();

    public static void load() {
        if (Config.getValueAsBoolean(ConfigValuesEffects.BadLuckEffectActive)) {
            effects.add(new BeaconEffects("§7Bad Luck", Config.getValueAsInteger(ConfigValuesEffects.BadLuckEffectBeaconTier),
                    Config.getValueAsInteger(ConfigValuesEffects.BadLuckEffectLv1), Config.getValueAsInteger(ConfigValuesEffects.BadLuckEffectLv2),
                    new ItemBuilder(Material.DEAD_BRAIN_CORAL).build(), PotionEffectType.UNLUCK));
        }
        if (Config.getValueAsBoolean(ConfigValuesEffects.BadOmenEffectActive)) {
            effects.add(new BeaconEffects("§7Bad Omen", Config.getValueAsInteger(ConfigValuesEffects.BadOmenEffectBeaconTier),
                    Config.getValueAsInteger(ConfigValuesEffects.BadOmenEffectLv1), Config.getValueAsInteger(ConfigValuesEffects.BadOmenEffectLv1),
                    new ItemBuilder(Material.IRON_BARS).build(), PotionEffectType.BAD_OMEN));
        }
        if (Config.getValueAsBoolean(ConfigValuesEffects.BlindnessEffectActive)) {
            effects.add(new BeaconEffects("§7Blindness", Config.getValueAsInteger(ConfigValuesEffects.BlindnessEffectBeaconTier),
                    Config.getValueAsInteger(ConfigValuesEffects.BlindnessEffectLv1), Config.getValueAsInteger(ConfigValuesEffects.BlindnessEffectLv2),
                    new ItemBuilder(Material.BLACK_WOOL).build(), PotionEffectType.BLINDNESS));
        }
        if (Config.getValueAsBoolean(ConfigValuesEffects.DolphinsGraceEffectActive)) {
            effects.add(new BeaconEffects("§7Dolphins Grace", Config.getValueAsInteger(ConfigValuesEffects.DolphinsGraceEffectBeaconTier),
                    Config.getValueAsInteger(ConfigValuesEffects.DolphinsGraceEffectLv1), Config.getValueAsInteger(ConfigValuesEffects.DolphinsGraceEffectLv2),
                    new ItemBuilder(Material.OAK_BOAT).build(), PotionEffectType.DOLPHINS_GRACE));
        }
        if (Config.getValueAsBoolean(ConfigValuesEffects.FireResistanceEffectActive)) {
            effects.add(new BeaconEffects("§7Fire Resistance", Config.getValueAsInteger(ConfigValuesEffects.FireResistanceEffectBeaconTier),
                    Config.getValueAsInteger(ConfigValuesEffects.FireResistanceEffectLv1), Config.getValueAsInteger(ConfigValuesEffects.FireResistanceEffectLv2),
                    new ItemBuilder(Material.FLINT_AND_STEEL).build(), PotionEffectType.FIRE_RESISTANCE));
        }
        if (Config.getValueAsBoolean(ConfigValuesEffects.GlowingEffectActive)) {
            effects.add(new BeaconEffects("§7Glowing", Config.getValueAsInteger(ConfigValuesEffects.GlowingEffectBeaconTier),
                    Config.getValueAsInteger(ConfigValuesEffects.GlowingEffectLv1), Config.getValueAsInteger(ConfigValuesEffects.GlowingEffectLv2),
                    new ItemBuilder(Material.GLOWSTONE_DUST).build(), PotionEffectType.GLOWING));
        }
        if (Config.getValueAsBoolean(ConfigValuesEffects.HasteEffectActive)) {
            effects.add(new BeaconEffects("§7Haste", Config.getValueAsInteger(ConfigValuesEffects.HasteEffectBeaconTier),
                    Config.getValueAsInteger(ConfigValuesEffects.HasteEffectLv1), Config.getValueAsInteger(ConfigValuesEffects.HasteEffectLv2),
                    new ItemBuilder(Material.GOLDEN_PICKAXE).build(), PotionEffectType.FAST_DIGGING));
        }
        if (Config.getValueAsBoolean(ConfigValuesEffects.HealthBoostEffectActive)) {
            effects.add(new BeaconEffects("§7Health Boost", Config.getValueAsInteger(ConfigValuesEffects.HealthBoostEffectBeaconTier),
                    Config.getValueAsInteger(ConfigValuesEffects.HealthBoostEffectLv1), Config.getValueAsInteger(ConfigValuesEffects.HealthBoostEffectLv2),
                    new ItemBuilder(Material.REDSTONE).build(), PotionEffectType.HEALTH_BOOST));
        }
        if (Config.getValueAsBoolean(ConfigValuesEffects.HeroOfTheVillageEffectActive)) {
            effects.add(new BeaconEffects("§7Hero Of The Village", Config.getValueAsInteger(ConfigValuesEffects.HeroOfTheVillageEffectBeaconTier),
                    Config.getValueAsInteger(ConfigValuesEffects.HeroOfTheVillageEffectLv1), Config.getValueAsInteger(ConfigValuesEffects.HeroOfTheVillageEffectLv2),
                    new ItemBuilder(Material.TOTEM_OF_UNDYING).build(), PotionEffectType.HERO_OF_THE_VILLAGE));
        }
        if (Config.getValueAsBoolean(ConfigValuesEffects.HungerEffectActive)) {
            effects.add(new BeaconEffects("§7Hunger", Config.getValueAsInteger(ConfigValuesEffects.HungerEffectBeaconTier),
                    Config.getValueAsInteger(ConfigValuesEffects.HungerEffectLv1), Config.getValueAsInteger(ConfigValuesEffects.HungerEffectLv2),
                    new ItemBuilder(Material.ROTTEN_FLESH).build(), PotionEffectType.HUNGER));
        }
        if (Config.getValueAsBoolean(ConfigValuesEffects.InvisibilityEffectActive)) {
            effects.add(new BeaconEffects("§7Invisibility", Config.getValueAsInteger(ConfigValuesEffects.InvisibilityEffectBeaconTier),
                    Config.getValueAsInteger(ConfigValuesEffects.InvisibilityEffectLv1), Config.getValueAsInteger(ConfigValuesEffects.InvisibilityEffectLv2),
                    new ItemBuilder(Material.ELYTRA).build(), PotionEffectType.INVISIBILITY));
        }
        if (Config.getValueAsBoolean(ConfigValuesEffects.JumpEffectActive)) {
            effects.add(new BeaconEffects("§7Jump Boost", Config.getValueAsInteger(ConfigValuesEffects.JumpEffectBeaconTier),
                    Config.getValueAsInteger(ConfigValuesEffects.JumpEffectLv1), Config.getValueAsInteger(ConfigValuesEffects.JumpEffectLv2),
                    new ItemBuilder(Material.RABBIT_FOOT).build(), PotionEffectType.JUMP));
        }
        if (Config.getValueAsBoolean(ConfigValuesEffects.LevitationEffectActive)) {
            effects.add(new BeaconEffects("§7Levitation", Config.getValueAsInteger(ConfigValuesEffects.LevitationEffectBeaconTier),
                    Config.getValueAsInteger(ConfigValuesEffects.LevitationEffectLv1), Config.getValueAsInteger(ConfigValuesEffects.LevitationEffectLv2),
                    new ItemBuilder(Material.SHULKER_BOX).build(), PotionEffectType.LEVITATION));
        }
        if (Config.getValueAsBoolean(ConfigValuesEffects.LuckEffectActive)) {
            effects.add(new BeaconEffects("§7Luck", Config.getValueAsInteger(ConfigValuesEffects.LuckEffectBeaconTier),
                    Config.getValueAsInteger(ConfigValuesEffects.LuckEffectLv1), Config.getValueAsInteger(ConfigValuesEffects.LuckEffectLv2),
                    new ItemBuilder(Material.DIAMOND).build(), PotionEffectType.LUCK));
        }
        if (Config.getValueAsBoolean(ConfigValuesEffects.NauseaEffectActive)) {
            effects.add(new BeaconEffects("§7Nausea", Config.getValueAsInteger(ConfigValuesEffects.NauseaEffectBeaconTier),
                    Config.getValueAsInteger(ConfigValuesEffects.NauseaEffectLv1), Config.getValueAsInteger(ConfigValuesEffects.NauseaEffectLv2),
                    new ItemBuilder(Material.GUNPOWDER).build(), PotionEffectType.CONFUSION));
        }
        if (Config.getValueAsBoolean(ConfigValuesEffects.NightVisionEffectActive)) {
            effects.add(new BeaconEffects("§7Night Vision", Config.getValueAsInteger(ConfigValuesEffects.NightVisionEffectBeaconTier),
                    Config.getValueAsInteger(ConfigValuesEffects.NightVisionEffectLv1), Config.getValueAsInteger(ConfigValuesEffects.NightVisionEffectLv2),
                    new ItemBuilder(Material.ENDER_EYE).build(), PotionEffectType.NIGHT_VISION));
        }
        if (Config.getValueAsBoolean(ConfigValuesEffects.PoisonEffectActive)) {
            effects.add(new BeaconEffects("§7Poison", Config.getValueAsInteger(ConfigValuesEffects.PoisonEffectBeaconTier),
                    Config.getValueAsInteger(ConfigValuesEffects.PoisonEffectLv1), Config.getValueAsInteger(ConfigValuesEffects.PoisonEffectLv2),
                    new ItemBuilder(Material.SPIDER_EYE).build(), PotionEffectType.POISON));
        }
        if (Config.getValueAsBoolean(ConfigValuesEffects.RegenerationEffectActive)) {
            effects.add(new BeaconEffects("§7Regeneration", Config.getValueAsInteger(ConfigValuesEffects.RegenerationEffectBeaconTier),
                    Config.getValueAsInteger(ConfigValuesEffects.RegenerationEffectLv1), Config.getValueAsInteger(ConfigValuesEffects.RegenerationEffectLv2),
                    new ItemBuilder(Material.GOLDEN_APPLE).build(), PotionEffectType.REGENERATION));
        }
        if (Config.getValueAsBoolean(ConfigValuesEffects.ResistanceEffectActive)) {
            effects.add(new BeaconEffects("§7Resistance", Config.getValueAsInteger(ConfigValuesEffects.ResistanceEffectBeaconTier),
                    Config.getValueAsInteger(ConfigValuesEffects.ResistanceEffectLv1), Config.getValueAsInteger(ConfigValuesEffects.ResistanceEffectLv2),
                    new ItemBuilder(Material.IRON_CHESTPLATE).build(), PotionEffectType.DAMAGE_RESISTANCE));
        }
        if (Config.getValueAsBoolean(ConfigValuesEffects.SaturationEffectActive)) {
            effects.add(new BeaconEffects("§7Saturation", Config.getValueAsInteger(ConfigValuesEffects.SaturationEffectBeaconTier),
                    Config.getValueAsInteger(ConfigValuesEffects.SaturationEffectLv1), Config.getValueAsInteger(ConfigValuesEffects.SaturationEffectLv2),
                    new ItemBuilder(Material.GOLDEN_CARROT).build(), PotionEffectType.SATURATION));
        }
        if (Config.getValueAsBoolean(ConfigValuesEffects.SlowDiggingEffectActive)) {
            effects.add(new BeaconEffects("§7Mining Fatigue", Config.getValueAsInteger(ConfigValuesEffects.SlowDiggingEffectBeaconTier),
                    Config.getValueAsInteger(ConfigValuesEffects.SlowDiggingEffectLv1), Config.getValueAsInteger(ConfigValuesEffects.SlowDiggingEffectLv2),
                    new ItemBuilder(Material.WOODEN_PICKAXE).build(), PotionEffectType.SLOW_DIGGING));
        }
        if (Config.getValueAsBoolean(ConfigValuesEffects.SlowEffectActive)) {
            effects.add(new BeaconEffects("§7Slowness", Config.getValueAsInteger(ConfigValuesEffects.SlowEffectBeaconTier),
                    Config.getValueAsInteger(ConfigValuesEffects.SlowEffectLv1), Config.getValueAsInteger(ConfigValuesEffects.SlowEffectLv2),
                    new ItemBuilder(Material.COBWEB).build(), PotionEffectType.SLOW));
        }
        if (Config.getValueAsBoolean(ConfigValuesEffects.SlowFallingEffectActive)) {
            effects.add(new BeaconEffects("§7Slow Fall", Config.getValueAsInteger(ConfigValuesEffects.SlowFallingEffectBeaconTier),
                    Config.getValueAsInteger(ConfigValuesEffects.SlowFallingEffectLv1), Config.getValueAsInteger(ConfigValuesEffects.SlowFallingEffectLv2),
                    new ItemBuilder(Material.FEATHER).build(), PotionEffectType.SLOW_FALLING));
        }
        if (Config.getValueAsBoolean(ConfigValuesEffects.SpeedEffectActive)) {
            effects.add(new BeaconEffects("§7Speed", Config.getValueAsInteger(ConfigValuesEffects.SpeedEffectBeaconTier),
                    Config.getValueAsInteger(ConfigValuesEffects.SpeedEffectLv1), Config.getValueAsInteger(ConfigValuesEffects.SpeedEffectLv2),
                    new ItemBuilder(Material.SUGAR).build(), PotionEffectType.SPEED));
        }
        if (Config.getValueAsBoolean(ConfigValuesEffects.StrengthEffectActive)) {
            effects.add(new BeaconEffects("§7Strength", Config.getValueAsInteger(ConfigValuesEffects.StrengthEffectBeaconTier),
                    Config.getValueAsInteger(ConfigValuesEffects.StrengthEffectLv1), Config.getValueAsInteger(ConfigValuesEffects.StrengthEffectLv2),
                    new ItemBuilder(Material.NETHERITE_SWORD).build(), PotionEffectType.INCREASE_DAMAGE));
        }
        if (Config.getValueAsBoolean(ConfigValuesEffects.WaterBreathingEffectActive)) {
            effects.add(new BeaconEffects("§7Water Breathing", Config.getValueAsInteger(ConfigValuesEffects.WaterBreathingEffectBeaconTier),
                    Config.getValueAsInteger(ConfigValuesEffects.WaterBreathingEffectLv1), Config.getValueAsInteger(ConfigValuesEffects.WaterBreathingEffectLv2),
                    new ItemBuilder(Material.TURTLE_HELMET).build(), PotionEffectType.WATER_BREATHING));
        }
        if (Config.getValueAsBoolean(ConfigValuesEffects.WeaknessEffectActive)) {
            effects.add(new BeaconEffects("§7Weakness", Config.getValueAsInteger(ConfigValuesEffects.WeaknessEffectBeaconTier),
                    Config.getValueAsInteger(ConfigValuesEffects.WeaknessEffectLv1), Config.getValueAsInteger(ConfigValuesEffects.WeaknessEffectLv2),
                    new ItemBuilder(Material.WOODEN_SWORD).build(), PotionEffectType.WEAKNESS));
        }
        if (Config.getValueAsBoolean(ConfigValuesEffects.WitherEffectActive)) {
            effects.add(new BeaconEffects("§7Wither", Config.getValueAsInteger(ConfigValuesEffects.WitherEffectBeaconTier),
                    Config.getValueAsInteger(ConfigValuesEffects.WitherEffectLv1), Config.getValueAsInteger(ConfigValuesEffects.WitherEffectLv2),
                    new ItemBuilder(Material.WITHER_SKELETON_SKULL).build(), PotionEffectType.WITHER));
        }
    }

    private String name;
    private int tier;
    private int lv1;
    private int lv2;
    private ItemStack icon;
    private PotionEffectType potionEffect;

    BeaconEffects(String name, int tier, int lv1, int lv2, ItemStack icon, PotionEffectType potionEffect) {
        this.name = name;
        this.tier = tier;
        this.lv1 = lv1;
        this.lv2 = lv2;
        this.icon = icon;
        this.potionEffect = potionEffect;
    }

    public String getName() {
        return name;
    }

    public int getTier() {
        return tier;
    }

    public int getLv1() {
        return lv1;
    }

    public int getLv2() {
        return lv2;
    }

    public ItemStack getIcon() {
        return new ItemBuilder(icon).setName(getName()).build();
    }

    public PotionEffectType getPotionEffect() {
        return potionEffect;
    }

    public static BeaconEffects getEffect(String name) {
        for (BeaconEffects effects : effects) {
            if (effects.name.equals(name)) {
                return effects;
            }
        }
        return null;
    }


}
