package org.tdod;

import com.formdev.flatlaf.FlatLightLaf;
import org.tdod.api.impl.ConsoleOutput;
import org.tdod.api.impl.TextAreaOutput;
import org.tdod.ui.ConfigurationPanel;
import org.tdod.ui.MainPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.net.ConnectException;

public class MorseCodePlayerUi {

    private MorseCodePlayer player = new MorseCodePlayer();

    private MorseCodePlayerUi() {
    }

    private void run() {
        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Main", new MainPanel(player));
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

        tabbedPane.addTab("Configuration", new ConfigurationPanel());
        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);

        JFrame frame = new JFrame("Morse Code Generator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(640,480);
        frame.getContentPane().add(tabbedPane);

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem menuItem1 = new JMenuItem("Exit");
        fileMenu.add(menuItem1);
        menuBar.add(fileMenu);
        frame.setJMenuBar(menuBar);

        menuItem1.addActionListener(e -> System.exit(0));

        frame.setVisible(true);

    }

    public static void main(String args[]){
        Configuration.setOutput(new ConsoleOutput());

        try {
            // UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            Configuration.loadProperties();
            UIManager.setLookAndFeel( new FlatLightLaf() );
        } catch (Exception e) {
            e.printStackTrace();
        }
        new MorseCodePlayerUi().run();
    }

}
