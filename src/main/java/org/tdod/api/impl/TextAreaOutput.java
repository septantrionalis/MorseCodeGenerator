package org.tdod.api.impl;

import org.tdod.api.Output;

import javax.swing.*;

public class TextAreaOutput implements Output {

    private JTextArea textArea;

    public TextAreaOutput(JTextArea textArea) {
        this.textArea = textArea;
    }

    @Override
    public void println(String message) {
        textArea.append(message + "\n");
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }

    @Override
    public void print(String message) {
        textArea.append(message);
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }
}
