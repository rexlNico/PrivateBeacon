package de.rexlnico.privatebeacon.methodes;

public enum ConfigValuesMessages implements ConfigValues {

    TEST("", "");

    private String path;
    private Object defaultValue;

    ConfigValuesMessages(String path, Object defaultValue) {
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