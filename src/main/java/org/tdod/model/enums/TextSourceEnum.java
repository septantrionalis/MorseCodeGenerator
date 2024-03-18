package org.tdod.model.enums;

public enum TextSourceEnum {

    OPEN_AI("open_ai"),
    MOCK("mock"),
    PARIS("paris"),
    FILE("file");
    
    private String textSource; 
    
    private TextSourceEnum(String textSource) {
        this.textSource = textSource;
    }

    public String getTextSource() {
        return textSource;
    }
    
    
}
