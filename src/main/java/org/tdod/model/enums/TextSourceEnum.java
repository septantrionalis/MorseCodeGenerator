package org.tdod.model.enums;

public enum TextSourceEnum {

    OPEN_AI("open_ai", "Open AI"),
    MOCK("mock", "Mock"),
    PARIS("paris", "PARIS"),
    FILE("file", "File");

    private String configName;
    private String displayName;

    TextSourceEnum(String configName, String textSource) {
        this.configName = configName;
        this.displayName = textSource;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getConfigName() {
        return configName;
    }

    public static String[] getDisplayNames() {
        String[] displayNames = new String[TextSourceEnum.values().length];
        for (int i = 0; i < TextSourceEnum.values().length; i++) {
            displayNames[i] = TextSourceEnum.values()[i].getDisplayName();
        }
        return displayNames;
    }

    public static String[] getConfigNames() {
        String[] configNames = new String[TextSourceEnum.values().length];
        for (int i = 0; i < TextSourceEnum.values().length; i++) {
            configNames[i] = TextSourceEnum.values()[i].getConfigName();
        }
        return configNames;
    }
}
