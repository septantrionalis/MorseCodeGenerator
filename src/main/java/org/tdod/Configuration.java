package org.tdod;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

public class Configuration {

    private static String apiKey = "TODO";
    private static Wpm wpm = Wpm.WPM_23;    
    private static int frequency = 600;
    private static TextSource textSource = TextSource.MOCK;
    
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
            Optional<String> wpmConfig = Optional.ofNullable(prop.getProperty("app.wpm"));
            wpmConfig.ifPresent(value -> System.out.println("Found app.wpm: " + value));
            wpmConfig.ifPresent(value -> setWpm(Wpm.getWpm(Integer.valueOf(value.trim()))));            
        } catch (NumberFormatException e) {
            System.out.println("app.wpm in property file is invalid.");
            throw e;
        }

        try {
            Optional<String> frequencyConfig = Optional.ofNullable(prop.getProperty("audio.frequency"));
            frequencyConfig.ifPresent(value -> System.out.println("Found audio.frequency: " + value));
            frequencyConfig.ifPresent(value -> setFrequency(Integer.valueOf(value.trim())));                        
        } catch (NumberFormatException e) {
            System.out.println("audio.frequency in property file is invalid.");
            throw e;
        }
        
        try {
            Optional<String> frequencyConfig = Optional.ofNullable(prop.getProperty("app.textsource"));
            frequencyConfig.ifPresent(value -> System.out.println("Found app.textsource: " + value));
            frequencyConfig.ifPresent(value -> setTextSource(TextSource.valueOf(value.trim().toUpperCase())));                        
        } catch (NumberFormatException e) {
            System.out.println("audio.frequency in property file is invalid.");
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

    public static int getFrequency() {
        return frequency;
    }

    public static void setFrequency(int frequency) {
        Configuration.frequency = frequency;
    }

    public static TextSource getTextSource() {
        return textSource;
    }

    public static void setTextSource(TextSource textSource) {
        Configuration.textSource = textSource;
    }

     
    
    
}
