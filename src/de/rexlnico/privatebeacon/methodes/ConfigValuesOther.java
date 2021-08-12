package de.rexlnico.privatebeacon.methodes;

public enum ConfigValuesOther implements ConfigValues {

    IronIngotRange("range.ironIngot", 0),
    GoldIngotRange("range.goldIngot", 0),
    EmeraldRange("range.emerald", 5),
    DiamondRange("range.diamond", 10),
    NetheriteIngotRange("range.NetheriteIngot", 20),
    DefaultRange("range.default", 40),
    MaxRange("range.max", -1),
    RequiresMaterialForEffectChange("effect.change.material.required", true);

    private String path;
    private Object defaultValue;

    ConfigValuesOther(String path, Object defaultValue) {
        this.path = path;
        this.defaultValue = defaultValue;
    }

    public Object getDefaultValue() {
        return defaultValue;
    }

    public String getPath() {
        return path;
    }
}
