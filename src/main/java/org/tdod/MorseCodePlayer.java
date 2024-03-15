package org.tdod;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;


public class MorseCodePlayer {

    private static final HashMap<Character, String> morseCodeMap = MorseCodeMap.getMap();
   
    public OpenAiApi openAiApi = new OpenAiApi();
    public AudioPlayer audioPlayer = new AudioPlayer();
    

    private String getCleanInput(String input) {
        ArrayList<Character> validList = new ArrayList<Character>(morseCodeMap.keySet());
        StringBuffer lineBuffer = new StringBuffer();

        for (int i = 0; i < input.length(); i++) {
            String str = "" + input.charAt(i);
            for (char s:validList) {
                if (str.toLowerCase().equals(String.valueOf(s).toLowerCase())) {
                    lineBuffer.append(str);
                }
            }
        }

        return lineBuffer.toString();
    }
    
    private void run() throws Exception {
        InputStream inputStream = MorseCodePlayer.class.getClassLoader().getResourceAsStream("config.properties");
        Properties prop = new Properties();
        prop.load(inputStream);
        String apiKey = prop.getProperty("api.ai.key");
        System.out.println("Found api key: " + apiKey);
        Configuration.setApiKey(apiKey);
        
        System.out.println("Generating random text...");
        // String input = openAiApi.generateRandomText();
        String input = "PARIS ";
        input = input.toUpperCase();

        System.out.println("Generated text:");
        System.out.println(input);
        
        input = getCleanInput(input);
        System.out.println("CLEANSED: " + input);

        audioPlayer.play(input);
    }
    
    public static void main(String[] args) throws Exception {
        new MorseCodePlayer().run();
    }


}
