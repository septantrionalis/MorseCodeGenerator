package org.tdod.ui;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.tdod.Configuration;
import org.tdod.model.enums.TextSourceEnum;
import org.tdod.model.enums.WpmEnum;
import org.tdod.utils.Utils;

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
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ConfigurationPanel extends JPanel {

    private static final long serialVersionUID = -1553295350137292758L;

    private JTextField apiKeyTextField;
    private JTextField apiPromptTextField;
    private JTextField apiUrlTextField;
    private JTextField openAiModelTextField;
    private JComboBox<String> wpmComboBox;
    private JComboBox<String> textSourceComboBox;
    private JComboBox<String> textFilenameComboBox;
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
        openAiModelTextField = createTextField(gbc, 1, row, 16, false);
        outerPanel.add(openAiModelTextField, gbc);
        openAiModelTextField.setText(Configuration.getModel());

        row++;
        createLabel(gbc, "WPM:", 0, row);
        wpmComboBox = new JComboBox<String>(WpmEnum.getDisplayNames());
        gbc.gridx = 1;
        gbc.gridy = row;
        outerPanel.add(wpmComboBox, gbc);
        wpmComboBox.setSelectedItem(Configuration.getWpm().getDisplayName());

        row++;
        createLabel(gbc, "Text Source:", 0, row);
        textSourceComboBox = new JComboBox<String>(TextSourceEnum.getConfigNames());
        gbc.gridx = 1;
        gbc.gridy = row;
        outerPanel.add(textSourceComboBox, gbc);
        textSourceComboBox.setSelectedItem(Configuration.getTextSource().getConfigName());

        row++;
        createLabel(gbc, "Text Filename:", 0, row);
        String[] historyFiles = getListOfHistoryFiles().toArray(new String[0]);
        textFilenameComboBox = new JComboBox<String>(historyFiles);
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
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> saveConfiguration());
        buttonPanel.add(saveButton);
        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> resetConfiguration());
        buttonPanel.add(resetButton);
        this.add(buttonPanel, BorderLayout.SOUTH);

        this.addComponentListener (new ComponentAdapter() {
            public void componentShown ( ComponentEvent e ) {
                String selectedValue = textFilenameComboBox.getSelectedItem().toString();
                outerPanel.remove(textFilenameComboBox);
                String[] historyFiles = getListOfHistoryFiles().toArray(new String[0]);
                textFilenameComboBox = new JComboBox<String>(historyFiles);
                try {
                    textFilenameComboBox.setSelectedItem(selectedValue);
                } catch (Exception ex) {
                    textFilenameComboBox.setSelectedIndex(0);
                }
                gbc.gridx = 1;
                gbc.gridy = 6;
                gbc.weightx = 0;
                gbc.weighty = 0;
                outerPanel.add(textFilenameComboBox, gbc);
            }

            public void componentHidden ( ComponentEvent e ) {
            }
        });
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
        if (StringUtils.isBlank(apiKeyTextField.getText())) {
            apiKeyTextField.setText(Configuration.getApiKey());
        }
        Configuration.setApiKey(apiKeyTextField.getText());

        if (StringUtils.isBlank(apiPromptTextField.getText())) {
            apiPromptTextField.setText(Configuration.getTextPrompt());
        }
        Configuration.setTextPrompt(apiPromptTextField.getText());

        if (StringUtils.isBlank(apiUrlTextField.getText())) {
            apiUrlTextField.setText(Configuration.getUrl());
        }
        Configuration.setUrl(apiUrlTextField.getText());

        if (StringUtils.isBlank(openAiModelTextField.getText())) {
            openAiModelTextField.setText(Configuration.getModel());
        }
        Configuration.setModel(openAiModelTextField.getText());

        Configuration.setWpm(WpmEnum.getWpm(Integer.valueOf(wpmComboBox.getSelectedItem().toString())));
        Configuration.setTextSource(TextSourceEnum.valueOf(((String)textSourceComboBox.getSelectedItem()).toUpperCase()));

        if (StringUtils.isBlank(startDelayTextField.getText())) {
            startDelayTextField.setText("" + Configuration.getStartDelay());
        }
        if (!Utils.isInteger(startDelayTextField.getText())) {
            startDelayTextField.setText("" + Configuration.getStartDelay());
        }
        Configuration.setStartDelay(Integer.parseInt(startDelayTextField.getText()));
        if (StringUtils.isBlank(audioFrequencyTextField.getText())) {
            audioFrequencyTextField.setText("" + Configuration.getFrequency());
        }
        if (!Utils.isInteger(audioFrequencyTextField.getText())) {
            audioFrequencyTextField.setText("" + Configuration.getFrequency());
        }
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

    private void resetConfiguration() {
        apiKeyTextField.setText(Configuration.getApiKey());
        apiPromptTextField.setText(Configuration.getTextPrompt());
        apiUrlTextField.setText(Configuration.getUrl());
        openAiModelTextField.setText(Configuration.getModel());
        wpmComboBox.setSelectedItem(Configuration.getWpm().getDisplayName());
        textSourceComboBox.setSelectedItem(Configuration.getTextSource().getConfigName());
        textFilenameComboBox.setSelectedItem(Configuration.getFilename());
        startDelayTextField.setText(String.valueOf(Configuration.getStartDelay()));
        audioFrequencyTextField.setText(String.valueOf(Configuration.getFrequency()));
    }
}
