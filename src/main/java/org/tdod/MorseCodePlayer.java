package org.tdod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


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
        
        System.out.println("Generating random text...");
        String generatedText;
        switch (Configuration.getTextSource()) {
        case OPEN_AI: 
            generatedText = openAiApi.generateRandomText(); 
            break;
        case MOCK:
            generatedText = "Today's weather forecast is calling for mostly sunny skies with a few scattered clouds in the morning, followed by increasing cloud cover in the afternoon. Temperatures are expected to reach a high of 80 degrees Fahrenheit with a light breeze coming from the southwest.\\n\\nOvernight, we can expect partly cloudy skies with temperatures dropping down to around 65 degrees. There is a slight chance of isolated showers in the late evening, so it's a good idea to keep an umbrella handy just in case.\\n\\nLooking ahead to tomorrow, we can anticipate a mix of sun and clouds with temperatures reaching a high of 75 degrees. There may be a chance of scattered showers in the afternoon, with the potential for some thunderstorms to develop in the evening.\\n\\nAs we head into the weekend, the weather is expected to remain unsettled with a mix of sun, clouds, and scattered showers throughout both Saturday and Sunday. Temperatures are forecasted to be in the mid-70s during the day and drop down to the low 60s in the evenings.\\n\\nOverall, it looks like we can expect a typical summer weather pattern with warm temperatures, occasional showers, and some thunderstorm activity. It's always a good idea to stay updated on the weather forecast and be prepared for any changes that may occur. Stay safe and enjoy the outdoors!";
            break;
        case PARIS:
            generatedText = "PARIS ";
            break;
        default:
            throw new RuntimeException("Invalid app.textsource " + Configuration.getTextSource());
        }       

        System.out.println("Generated text:");
        System.out.println(generatedText);
        
        generatedText = generatedText.replaceAll("\\\\n", " ");
        generatedText = generatedText.toUpperCase();
        
        generatedText = getCleanInput(generatedText);
        System.out.println("Cleansed text: ");
        System.out.println(generatedText);
        
        Scanner scanner = new Scanner(System.in);
        System.out.print("<Press return to start>");
        scanner.nextLine();
        scanner.close();
        
        audioPlayer.play(generatedText);
    }
    
    public static void main(String[] args) throws Exception {
        Configuration.loadProperties();
        new MorseCodePlayer().run();
    }


}
