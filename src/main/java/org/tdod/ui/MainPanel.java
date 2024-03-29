package org.tdod.ui;

import org.apache.commons.lang3.StringUtils;
import org.tdod.Configuration;
import org.tdod.MorseCodeMap;
import org.tdod.MorseCodePlayer;
import org.tdod.MorseCodePlayerConsole;
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

public class MainPanel extends MorseCodePlayer {

    private AudioPlayer audioPlayer = new DefaultAudioPlayer();

    private static final HashMap<Character, String> morseCodeMap = MorseCodeMap.getMap();
    private TextGenerator openAiApi = new OpenAiTextGenerator();

    private MorseCodePlayerConsole player;

    private JButton mainButton;
    private PlayerRunStateEnum state = PlayerRunStateEnum.INITIALIZE;

    private JTextArea textArea = new JTextArea();

    private String generatedText = "";

    private Thread thread;
    
    public MainPanel(MorseCodePlayerConsole player) {
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
                    generatedText = initialize();
                    break;
                case RUN:
                    play(generatedText);
                    break;
                case RUNNING:
                    stop();
                    break;
            }
            // player.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String initialize() {
        String generatedText;
        try {
            println("Generating random text...");
            Thread t1 = new Thread(new RunnableImpl());
            t1.start();
            generatedText = generateText();
        
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

    private void play(String generatedText) {
        if (StringUtils.isEmpty(generatedText)) {
            return;
        }

        try {
            println("Running...");
            audioPlayer.setMessage(generatedText);
            thread = new Thread(audioPlayer);
            thread.start();
        } catch (Exception e) {
            println("Error playing audio: " + e.getMessage());
            e.printStackTrace();
        }

        mainButton.setText("Stop");
        state = PlayerRunStateEnum.RUNNING;
    }
    
    private void stop() {
        if (thread != null) {
            audioPlayer.terminate();
        }
        
        mainButton.setText("Generate Text");
        state = PlayerRunStateEnum.INITIALIZE;

        
    }
    
    private class RunnableImpl implements Runnable {
        
        public void run() {
            textArea.update(textArea.getGraphics());
            textArea.setCaretPosition(textArea.getDocument().getLength());                
        }
    }
}
