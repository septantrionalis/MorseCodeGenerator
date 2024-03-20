package org.tdod;

import com.formdev.flatlaf.FlatLightLaf;
import org.tdod.ui.ConfigurationPanel;
import org.tdod.ui.MainPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class MorseCodePlayerUi {

    private MorseCodePlayerUi() {
    }

    private void run() {
        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Main", new MainPanel());
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

        tabbedPane.addTab("Configuration", new ConfigurationPanel());
        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);

        JFrame frame = new JFrame("My First GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(640,480);
        frame.getContentPane().add(tabbedPane);

        frame.setVisible(true);
    }

    public static void main(String args[]){
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
