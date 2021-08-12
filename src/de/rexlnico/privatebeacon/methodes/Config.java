package de.rexlnico.privatebeacon.methodes;

import de.rexlnico.privatebeacon.manager.BeaconEffects;
import net.minecraft.server.v1_16_R3.Tag;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class Config {

    private static File file;
    private static YamlConfiguration cfg;
    private static HashMap<ConfigValues, Object> values;

    public static void load() throws IOException {
        values = new HashMap<>();
        file = new File("plugins/PrivateBeacon/EffectConfig.yml");
        cfg = YamlConfiguration.loadConfiguration(file);
        for (ConfigValues configValues : ConfigValuesEffects.values()) {
            if (!file.exists() || !cfg.contains(configValues.getPath())) {
                cfg.set(configValues.getPath(), configValues.getDefaultValue());
            }
        }
        cfg.save(file);
        for (ConfigValues configValues : ConfigValuesEffects.values()) {
            if (cfg.contains(configValues.getPath())) {
                values.put(configValues, cfg.get(configValues.getPath()));
            }
        }
        file = new File("plugins/PrivateBeacon/Config.yml");
        cfg = YamlConfiguration.loadConfiguration(file);
        for (ConfigValues configValues : ConfigValuesOther.values()) {
            if (!file.exists() || !cfg.contains(configValues.getPath())) {
                cfg.set(configValues.getPath(), configValues.getDefaultValue());
            }
        }
        cfg.save(file);
        for (ConfigValues configValues : ConfigValuesOther.values()) {
            if (cfg.contains(configValues.getPath())) {
                values.put(configValues, cfg.get(configValues.getPath()));
            }
        }
//        file = new File("plugins/PrivateBeacon/Messages.yml");
//        cfg = YamlConfiguration.loadConfiguration(file);
//        for (ConfigValues configValues : ConfigValuesMessages.values()) {
//            if (!file.exists() || !cfg.contains(configValues.getPath())) {
//                cfg.set(configValues.getPath(), configValues.getDefaultValue());
//            }
//        }
//        cfg.save(file);
//        for (ConfigValues configValues : ConfigValuesMessages.values()) {
//            if (cfg.contains(configValues.getPath())) {
//                values.put(configValues, cfg.get(configValues.getPath()));
//            }
//        }
    }

    public static Object getValue(ConfigValues configValue) {
        return values.get(configValue);
    }

    public static String getValueAsString(ConfigValues configValue) {
        return (String) values.get(configValue);
    }

    public static String getValueAsStringAndReplace(ConfigValues configValue, String... replaces) {
        String back = (String) values.get(configValue);
        int i = 0;
        while (back.contains("%s") && replaces.length > i) {
            back = back.replaceFirst("%s", replaces[i]);
            i++;
        }
        return back;
    }

    public static Integer getValueAsInteger(ConfigValues configValue) {
        return (Integer) values.get(configValue);
    }

    public static boolean getValueAsBoolean(ConfigValues configValue) {
        return (boolean) values.get(configValue);
    }

}
