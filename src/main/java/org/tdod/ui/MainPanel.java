package org.tdod.ui;

import org.tdod.MorseCodePlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class MainPanel extends JPanel {

    private MorseCodePlayer player;
    private JButton mainButton = new JButton("Start");

    public MainPanel(MorseCodePlayer player) {
        this.setLayout(new BorderLayout());

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBackground(this.getBackground());
        textArea.setText("This is a text area");
        JPanel textAreaPanel = new JPanel();
        textAreaPanel.setLayout(new BorderLayout());
        textAreaPanel.add(textArea);
        textAreaPanel.setBorder(BorderFactory.createEtchedBorder());

        this.add(textAreaPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        mainButton = new JButton("Generate Text");
        mainButton.addActionListener(e -> runPlayer());
        buttonPanel.add(mainButton);
        this.add(buttonPanel, BorderLayout.SOUTH);

        this.player = player;
    }

    private void runPlayer() {
        try {
            mainButton.setText("Run");
            // player.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
