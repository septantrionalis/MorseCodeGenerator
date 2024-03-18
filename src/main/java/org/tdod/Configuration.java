package org.tdod;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

public class Configuration {

    private static String apiKey = "TODO";
    private static WpmEnum wpm = WpmEnum.WPM_23;
    private static int frequency = 600;
    private static TextSourceEnum textSource = TextSourceEnum.MOCK;
    private static String textPrompt = "a lengthy weather report";
    private static String url = "https://api.openai.com/v1/chat/completions";
    private static String model = "gpt-3.5-turbo";
    private static String filename = "space_weather.txt";
    private static int startDelay = 5;
    
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
            wpmConfig.ifPresent(value -> setWpm(WpmEnum.getWpm(Integer.valueOf(value.trim()))));
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
            frequencyConfig.ifPresent(value -> setTextSource(TextSourceEnum.valueOf(value.trim().toUpperCase())));
        } catch (Exception e) {
            System.out.println("app.textsource in property file is invalid.");
            throw e;
        }
        
        try {
            Optional<String> textPromptConfig = Optional.ofNullable(prop.getProperty("api.ai.textprompt"));
            textPromptConfig.ifPresent(value -> System.out.println("Found api.ai.textprompt: " + value));
            textPromptConfig.ifPresent(value -> setTextPrompt(value));                        
        } catch (Exception e) {
            System.out.println("api.ai.textprompt in property file is invalid.");
            throw e;
        }

        try {
            Optional<String> urlConfig = Optional.ofNullable(prop.getProperty("api.ai.url"));
            urlConfig.ifPresent(value -> System.out.println("Found api.ai.url: " + value));
            urlConfig.ifPresent(value -> setUrl(value));                        
        } catch (Exception e) {
            System.out.println("api.ai.url in property file is invalid.");
            throw e;
        }

        try {
            Optional<String> modelConfig = Optional.ofNullable(prop.getProperty("api.ai.model"));
            modelConfig.ifPresent(value -> System.out.println("Found api.ai.model: " + value));
            modelConfig.ifPresent(value -> setModel(value));                        
        } catch (Exception e) {
            System.out.println("api.ai.mode in property file is invalid.");
            throw e;
        }

        try {
            Optional<String> filenameConfig = Optional.ofNullable(prop.getProperty("app.filename"));
            filenameConfig.ifPresent(value -> System.out.println("Found app.filename: " + value));
            filenameConfig.ifPresent(value -> setFilename(value));                        
        } catch (Exception e) {
            System.out.println("app.filename in property file is invalid.");
            throw e;
        }
     
        try {
            Optional<String> startDelayConfig = Optional.ofNullable(prop.getProperty("app.startdelay"));
            startDelayConfig.ifPresent(value -> System.out.println("Found app.startdelay: " + value));
            startDelayConfig.ifPresent(value -> setStartDelay(Integer.valueOf(value.trim())));            
        } catch (NumberFormatException e) {
            System.out.println("app.startdelay in property file is invalid.");
            throw e;
        }

        
    }
    
    public static String getApiKey() {
        return apiKey;
    }

    public static void setApiKey(String apiKey) {
        Configuration.apiKey = apiKey;
    }

    public static WpmEnum getWpm() {
        return wpm;
    }

    public static void setWpm(WpmEnum wpm) {
        Configuration.wpm = wpm;
    }

    public static int getFrequency() {
        return frequency;
    }

    public static void setFrequency(int frequency) {
        Configuration.frequency = frequency;
    }

    public static TextSourceEnum getTextSource() {
        return textSource;
    }

    public static void setTextSource(TextSourceEnum textSource) {
        Configuration.textSource = textSource;
    }

    public static String getTextPrompt() {
        return textPrompt;
    }

    public static void setTextPrompt(String textPrompt) {
        Configuration.textPrompt = textPrompt;
    }

    public static String getUrl() {
        return url;
    }

    public static void setUrl(String url) {
        Configuration.url = url;
    }

    public static String getModel() {
        return model;
    }

    public static void setModel(String model) {
        Configuration.model = model;
    }

    public static String getFilename() {
        return filename;
    }

    public static void setFilename(String filename) {
        Configuration.filename = filename;
    }

    public static int getStartDelay() {
        return startDelay;
    }

    public static void setStartDelay(int startDelay) {
        Configuration.startDelay = startDelay;
    }
    
    
}
