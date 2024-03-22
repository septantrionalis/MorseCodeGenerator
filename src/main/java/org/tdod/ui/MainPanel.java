package org.tdod.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.apache.commons.lang3.StringUtils;
import org.tdod.Configuration;
import org.tdod.MorseCodePlayer;
import org.tdod.MorseCodePlayerConsole;
import org.tdod.api.AudioPlayer;
import org.tdod.api.impl.DefaultAudioPlayer;
import org.tdod.api.impl.TextAreaOutput;
import org.tdod.model.enums.PlayerRunStateEnum;

public class MainPanel extends MorseCodePlayer {

    private static final long serialVersionUID = -7553255361043368892L;

    private AudioPlayer audioPlayer = new DefaultAudioPlayer();
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
}
