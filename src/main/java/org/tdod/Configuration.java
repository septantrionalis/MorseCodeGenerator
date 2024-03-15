package org.tdod;

public class Configuration {

    private static String apiKey = "TODO";
    private static Wpm wpm = Wpm.WPM_23;    
    
    private Configuration() {
        
    }

    public static String getApiKey() {
        return apiKey;
    }

    public static void setApiKey(String apiKey) {
        Configuration.apiKey = apiKey;
    }

    public static Wpm getWpm() {
        return wpm;
    }

    public static void setWpm(Wpm wpm) {
        Configuration.wpm = wpm;
    }

     
    
    
}
