package org.tdod.ui;

import org.tdod.Configuration;
import org.tdod.model.enums.TextSourceEnum;
import org.tdod.model.enums.WpmEnum;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class ConfigurationPanel extends JPanel {

    private JTextField apiKeyTextField;
    private JTextField apiPromptTextField;
    private JTextField apiUrlTextField;
    private JTextField openAiTextField;
    private JComboBox<String> wpmComboBox;
    private JComboBox<String> textSourceComboBox;
    private JTextField filenameTextField;
    private JTextField startDelayTextField;
    private JTextField audioFrequencyTextField;

    public ConfigurationPanel() {
        this.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(2, 5, 0, 0);

        int row = 0;
        createLabel(gbc, "OpenAI API Key:", 0, row);
        apiKeyTextField = createTextField(gbc, 1, row, 16, false);
        this.add(apiKeyTextField, gbc);
        apiKeyTextField.setText(Configuration.getApiKey());

        row++;
        createLabel(gbc, "OpenAI Text Prompt:", 0, row);
        apiPromptTextField = createTextField(gbc, 1, row, 16, false);
        this.add(apiPromptTextField, gbc);
        apiPromptTextField.setText(Configuration.getTextPrompt());

        row++;
        createLabel(gbc, "OpenAI URL:", 0, row);
        apiUrlTextField = createTextField(gbc, 1, row, 16, false);
        this.add(apiUrlTextField, gbc);

        row++;
        createLabel(gbc, "OpenAI Model:", 0, row);
        openAiTextField = createTextField(gbc, 1, row, 16, false);
        this.add(openAiTextField, gbc);

        row++;
        createLabel(gbc, "WPM:", 0, row);
        wpmComboBox = new JComboBox(WpmEnum.getDisplayNames());
        gbc.gridx = 1;
        gbc.gridy = row;
        this.add(wpmComboBox, gbc);

        row++;
        createLabel(gbc, "Text Source:", 0, row);
        textSourceComboBox = new JComboBox(TextSourceEnum.getDisplayNames());
        gbc.gridx = 1;
        gbc.gridy = row;
        this.add(textSourceComboBox, gbc);

        row++;
        createLabel(gbc, "Text Filename:", 0, row);
        filenameTextField = createTextField(gbc, 1, row, 16, false);
        this.add(filenameTextField, gbc);

        row++;
        createLabel(gbc, "Start Delay:", 0, row);
        startDelayTextField = createTextField(gbc, 1, row, 3, false);
        this.add(startDelayTextField, gbc);

        row++;
        createLabel(gbc, "Audio Frequency:", 0, row);
        audioFrequencyTextField = createTextField(gbc, 1, row, 4, true);
        this.add(audioFrequencyTextField, gbc);
    }

    private void createLabel(GridBagConstraints gbc, String label, int x, int y) {
        JLabel jlabel = new JLabel(label, SwingConstants.LEFT);
        gbc.gridx = x;
        gbc.gridy = y;
        this.add(jlabel, gbc);
    }

    private JTextField createTextField(GridBagConstraints gbc, int x, int y, int size, boolean isLast) {
        JTextField textfield = new JTextField(size);
        gbc.gridx = x;
        gbc.gridy = y;
        if (isLast) {
            gbc.weightx = 1;
            gbc.weighty = 1;
        }
        return textfield;
    }
}
