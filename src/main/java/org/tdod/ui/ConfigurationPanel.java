package org.tdod.ui;

import org.apache.commons.io.FileUtils;
import org.tdod.Configuration;
import org.tdod.model.enums.TextSourceEnum;
import org.tdod.model.enums.WpmEnum;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConfigurationPanel extends JPanel {

    private JTextField apiKeyTextField;
    private JTextField apiPromptTextField;
    private JTextField apiUrlTextField;
    private JTextField openAiTextField;
    private JComboBox<String> wpmComboBox;
    private JComboBox<String> textSourceComboBox;
    private JComboBox textFilenameComboBox;
    private JTextField startDelayTextField;
    private JTextField audioFrequencyTextField;

    private JPanel outerPanel = new JPanel();

    public ConfigurationPanel() {
        this.setLayout(new BorderLayout());
        outerPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(2, 5, 0, 0);

        int row = 0;
        createLabel(gbc, "OpenAI API Key:", 0, row);
        apiKeyTextField = createTextField(gbc, 1, row, 16, false);
        outerPanel.add(apiKeyTextField, gbc);
        apiKeyTextField.setText(Configuration.getApiKey());

        row++;
        createLabel(gbc, "OpenAI Text Prompt:", 0, row);
        apiPromptTextField = createTextField(gbc, 1, row, 30, false);
        outerPanel.add(apiPromptTextField, gbc);
        apiPromptTextField.setText(Configuration.getTextPrompt());

        row++;
        createLabel(gbc, "OpenAI URL:", 0, row);
        apiUrlTextField = createTextField(gbc, 1, row, 30, false);
        outerPanel.add(apiUrlTextField, gbc);
        apiUrlTextField.setText(Configuration.getUrl());

        row++;
        createLabel(gbc, "OpenAI Model:", 0, row);
        openAiTextField = createTextField(gbc, 1, row, 16, false);
        outerPanel.add(openAiTextField, gbc);
        openAiTextField.setText(Configuration.getModel());

        row++;
        createLabel(gbc, "WPM:", 0, row);
        wpmComboBox = new JComboBox(WpmEnum.getDisplayNames());
        gbc.gridx = 1;
        gbc.gridy = row;
        outerPanel.add(wpmComboBox, gbc);
        wpmComboBox.setSelectedItem(Configuration.getWpm().getDisplayName());

        row++;
        createLabel(gbc, "Text Source:", 0, row);
        textSourceComboBox = new JComboBox(TextSourceEnum.getConfigNames());
        gbc.gridx = 1;
        gbc.gridy = row;
        outerPanel.add(textSourceComboBox, gbc);
        textSourceComboBox.setSelectedItem(Configuration.getTextSource().getConfigName());

        row++;
        createLabel(gbc, "Text Filename:", 0, row);
        String[] historyFiles = getListOfHistoryFiles().toArray(new String[0]);
        textFilenameComboBox = new JComboBox(historyFiles);
        textFilenameComboBox.setSelectedItem(Configuration.getFilename());
        gbc.gridx = 1;
        gbc.gridy = row;
        outerPanel.add(textFilenameComboBox, gbc);

        row++;
        createLabel(gbc, "Start Delay:", 0, row);
        startDelayTextField = createTextField(gbc, 1, row, 3, false);
        outerPanel.add(startDelayTextField, gbc);
        startDelayTextField.setText(String.valueOf(Configuration.getStartDelay()));

        row++;
        createLabel(gbc, "Audio Frequency:", 0, row);
        audioFrequencyTextField = createTextField(gbc, 1, row, 4, true);
        outerPanel.add(audioFrequencyTextField, gbc);
        audioFrequencyTextField.setText(String.valueOf(Configuration.getFrequency()));

        this.add(outerPanel, BorderLayout.WEST);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        JButton button = new JButton("Save");
        button.setSize(50, 50);
        button.addActionListener(e -> saveConfiguration());
        buttonPanel.add(button);
        this.add(buttonPanel, BorderLayout.SOUTH);

    }

    private void createLabel(GridBagConstraints gbc, String label, int x, int y) {
        JLabel jlabel = new JLabel(label, SwingConstants.LEFT);
        gbc.gridx = x;
        gbc.gridy = y;
        outerPanel.add(jlabel, gbc);
    }

    public List<String> getListOfHistoryFiles() {
        String dir = "./" + Configuration.HISTORY_DIR + "/";
        File directory = new File(dir);
        if (!directory.exists() || !directory.isDirectory()) {
            throw new IllegalArgumentException("Invalid directory path");
        }

        File[] files = directory.listFiles();
        return Arrays.stream(files)
                .filter(file -> file.isFile())
                .map(File::getName)
                .sorted()
                .collect(Collectors.toList());
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

    private void saveConfiguration() {
        Configuration.setApiKey(apiKeyTextField.getText());
        Configuration.setTextPrompt(apiPromptTextField.getText());
        Configuration.setUrl(apiUrlTextField.getText());
        Configuration.setModel(openAiTextField.getText());
        Configuration.setWpm(WpmEnum.getWpm(Integer.valueOf(wpmComboBox.getSelectedItem().toString())));
        Configuration.setTextSource(TextSourceEnum.valueOf(((String)textSourceComboBox.getSelectedItem()).toUpperCase()));
        Configuration.setStartDelay(Integer.parseInt(startDelayTextField.getText()));
        Configuration.setFrequency(Integer.parseInt(audioFrequencyTextField.getText()));
        Configuration.setFilename((String) textFilenameComboBox.getSelectedItem());

        StringBuffer sb = new StringBuffer();
        sb.append(Configuration.API_AI_KEY + "=" + Configuration.getApiKey()).append("\n");
        sb.append(Configuration.API_AI_TEXTPROMPT + "=" + Configuration.getTextPrompt()).append("\n");
        sb.append(Configuration.API_AI_URL + "=" + Configuration.getUrl()).append("\n");
        sb.append(Configuration.API_AI_MODEL + "=" + Configuration.getModel()).append("\n");
        sb.append(Configuration.APP_WPM + "=" + Configuration.getWpm().getDisplayName()).append("\n");
        sb.append(Configuration.APP_TEXTSOURCE + "=" + Configuration.getTextSource().name().toLowerCase()).append("\n");
        sb.append(Configuration.APP_STARTDELAY + "=" + Configuration.getStartDelay()).append("\n");
        sb.append(Configuration.AUDIO_FREQUENCY + "=" + Configuration.getFrequency()).append("\n");
        sb.append(Configuration.APP_FILENAME + "=" + Configuration.getFilename()).append("\n");
        try {
            FileUtils.writeStringToFile(new File(Configuration.CONFIG_FILE),
                    sb.toString(),
                    Charset.forName("UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
