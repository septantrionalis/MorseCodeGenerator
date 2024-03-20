package org.tdod.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class MainPanel extends JPanel {

    public MainPanel() {
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
        JButton button = new JButton("Start");
        button.setSize(50, 50);
        buttonPanel.add(button);
        this.add(buttonPanel, BorderLayout.SOUTH);
    }

}
