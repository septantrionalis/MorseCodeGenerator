package org.tdod;

public enum TextSource {

    OPEN_AI("open_ai"),
    MOCK("mock"),
    PARIS("paris"),
    FILE("file");
    
    private String textSource; 
    
    private TextSource(String textSource) {
        this.textSource = textSource;
    }

    public String getTextSource() {
        return textSource;
    }
    
    
}
