package org.tdod.ui;

import org.apache.commons.lang3.StringUtils;
import org.tdod.Configuration;
import org.tdod.MorseCodeMap;
import org.tdod.MorseCodePlayer;
import org.tdod.api.AudioPlayer;
import org.tdod.api.Output;
import org.tdod.api.TextGenerator;
import org.tdod.api.impl.DefaultAudioPlayer;
import org.tdod.api.impl.OpenAiTextGenerator;
import org.tdod.api.impl.TextAreaOutput;
import org.tdod.model.enums.PlayerRunStateEnum;
import org.tdod.utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public class MainPanel extends JPanel {

    private AudioPlayer audioPlayer = new DefaultAudioPlayer();

    private static final HashMap<Character, String> morseCodeMap = MorseCodeMap.getMap();
    private TextGenerator openAiApi = new OpenAiTextGenerator();

    private MorseCodePlayer player;

    private JButton mainButton;
    private PlayerRunStateEnum state = PlayerRunStateEnum.INITIALIZE;

    private JTextArea textArea = new JTextArea();

    private String generatedText = "";

    public MainPanel(MorseCodePlayer player) {
        this.setLayout(new BorderLayout());

        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBackground(this.getBackground());
        Configuration.setOutput(new TextAreaOutput(textArea));
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

        println("Hello...");
    }

    private void mainButtonHandler() {
        try {
            switch (state) {
                case INITIALIZE:
                    generatedText = generateText();
                    break;
                case RUN:
                    play(generatedText);
                    break;
            }
            // player.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String generateText() {
        println("Generating random text...");

        String generatedText = "";
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
                        println("Cannot find the file " + filename);
                        throw new Exception(e);
                    }
                    break;
                default:
                    throw new RuntimeException("Invalid " + Configuration.APP_TEXTSOURCE +": " + Configuration.getTextSource());
            }

            println("Raw Generated text:");
            println(generatedText);

            generatedText = generatedText.replaceAll("\\\\n", " ");
            generatedText = generatedText.toUpperCase();

            generatedText = Utils.getCleanInput(generatedText);
            println("Cleansed text: ");
            println(generatedText);
            println("");
            println("Text statistics:");
            printStats(generatedText);
            mainButton.setText("Run");
            state = PlayerRunStateEnum.RUN;
            println("Press \"Run\" to play the morse code.");
        } catch (Exception e) {
            println("Error generating text: " + e.getMessage());
            generatedText = "";
            mainButton.setText("Run");
            state = PlayerRunStateEnum.INITIALIZE;
            println("Press \"Generate Text\" to try again.");
            e.printStackTrace();
        }

        return generatedText;
    }

    private void printStats(String input) {
        ArrayList<Character> validList = new ArrayList<Character>(morseCodeMap.keySet());

        for (Character c:validList) {
            Long count = input.chars().filter(num -> num == c).count();
            println(c + ":" + count);
        }
    }

    private void println(String text) {
        Configuration.getOutput().println(text);
    }

    private void play(String generatedText) {
        if (StringUtils.isEmpty(generatedText)) {
            return;
        }

        try {
            println("Running...");
        //    audioPlayer.play(generatedText);
        } catch (Exception e) {
            println("Error playing audio: " + e.getMessage());
            e.printStackTrace();
        }

        mainButton.setText("Generate Text");
        state = PlayerRunStateEnum.INITIALIZE;
    }
}
