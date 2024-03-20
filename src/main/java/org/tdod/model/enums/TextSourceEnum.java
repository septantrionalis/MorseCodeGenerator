package org.tdod.model.enums;

public enum TextSourceEnum {

    OPEN_AI("Open AI"),
    MOCK("Mock"),
    PARIS("PARIS"),
    FILE("File");
    
    private String textSource;

    TextSourceEnum(String textSource) {
        this.textSource = textSource;
    }

    public String getTextSource() {
        return textSource;
    }
    
    public static String[] getDisplayNames() {
        String[] displayNames = new String[TextSourceEnum.values().length];
        for (int i = 0; i < TextSourceEnum.values().length; i++) {
            displayNames[i] = TextSourceEnum.values()[i].getTextSource();
        }
        return displayNames;
    }
}
