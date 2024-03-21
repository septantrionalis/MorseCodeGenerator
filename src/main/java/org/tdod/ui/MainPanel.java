package org.tdod.ui;

import org.tdod.Configuration;
import org.tdod.MorseCodeMap;
import org.tdod.MorseCodePlayer;
import org.tdod.api.TextGenerator;
import org.tdod.api.impl.OpenAiTextGenerator;
import org.tdod.model.enums.PlayerRunStateEnum;
import org.tdod.utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public class MainPanel extends JPanel {

    private static final HashMap<Character, String> morseCodeMap = MorseCodeMap.getMap();
    private TextGenerator openAiApi = new OpenAiTextGenerator();

    private MorseCodePlayer player;

    private JButton mainButton;
    private PlayerRunStateEnum state = PlayerRunStateEnum.INITIALIZE;

    JTextArea textArea = new JTextArea();
    StringBuffer buffer = new StringBuffer();

    public MainPanel(MorseCodePlayer player) {
        this.setLayout(new BorderLayout());

        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBackground(this.getBackground());
        print("Hello...");
        JScrollPane scroll = new JScrollPane(textArea);
        JPanel textAreaPanel = new JPanel();
        textAreaPanel.setLayout(new BorderLayout());
        textAreaPanel.add(scroll);
        textAreaPanel.setBorder(BorderFactory.createEtchedBorder());

        this.add(textAreaPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        mainButton = new JButton("Generate Text");
        mainButton.addActionListener(e -> mainButtonHandler());
        buttonPanel.add(mainButton);
        this.add(buttonPanel, BorderLayout.SOUTH);

        this.player = player;
    }

    private void mainButtonHandler() {
        try {
            switch (state) {
                case INITIALIZE:
                    generateText();
                    break;
                case RUN:
                    mainButton.setText("Generate Text");
                    state = PlayerRunStateEnum.INITIALIZE;
                    break;
            }
            // player.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void generateText() {
        print("Generating random text...");
        textArea.setText(buffer.toString());

        String generatedText;
        try {
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
                case FILE:
                    String filename = "./" + Configuration.HISTORY_DIR + "/" + Configuration.getFilename();
                    try {
                        generatedText = new String(Files.readAllBytes(Paths.get(filename)));
                    } catch (Exception e) {
                        print("Cannot find the file " + filename);
                        throw new Exception(e);
                    }
                    break;
                default:
                    throw new RuntimeException("Invalid " + Configuration.APP_TEXTSOURCE +": " + Configuration.getTextSource());
            }

            print("Raw Generated text:");
            print(generatedText);

            generatedText = generatedText.replaceAll("\\\\n", " ");
            generatedText = generatedText.toUpperCase();

            generatedText = Utils.getCleanInput(generatedText);
            print("Cleansed text: ");
            print(generatedText);
            print("");
            print("Text statistics:");
            printStats(generatedText);
        } catch (Exception e) {
            print("Error generating text: " + e.getMessage());
            e.printStackTrace();
        }

        mainButton.setText("Run");
        state = PlayerRunStateEnum.RUN;
    }

    private void printStats(String input) {
        ArrayList<Character> validList = new ArrayList<Character>(morseCodeMap.keySet());

        for (Character c:validList) {
            Long count = input.chars().filter(num -> num == c).count();
            print(c + ":" + count);
        }
    }

    private void print(String text) {
        buffer.append(text + "\n");
        textArea.setText(buffer.toString());
    }
}
