package org.tdod;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class Configuration {

    private static String apiKey = "TODO";
    private static Wpm wpm = Wpm.WPM_23;    
    
    private Configuration() {
        
    }

    public static void loadProperties() throws Exception {
        InputStream input = new FileInputStream("config.properties");
        Properties prop = new Properties();
        prop.load(input);        

        String apiKey = prop.getProperty("api.ai.key");
        System.out.println("Found api key: " + apiKey);
        setApiKey(apiKey);
        
        try {
            Integer wpmConfig = Integer.valueOf(prop.getProperty("app.wpm"));
            System.out.println("Found WPM: " + wpmConfig);
        } catch (NumberFormatException e) {
            System.out.println("app.wpm in property file is invalid.");
            throw e;
        }

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
