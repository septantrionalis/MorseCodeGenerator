package org.tdod;

import java.util.Scanner;

import org.tdod.api.AudioPlayer;
import org.tdod.api.impl.ConsoleOutput;
import org.tdod.api.impl.DefaultAudioPlayer;


public class MorseCodePlayerConsole extends MorseCodePlayer {


    private static final long serialVersionUID = 1L;
    public AudioPlayer audioPlayer = new DefaultAudioPlayer();

    public void run() throws Exception {
        println("Generating random text...");

        String generatedText = generateText();
        
        Scanner scanner = new Scanner(System.in);
        print("<Press return to start>");
        scanner.nextLine();
        scanner.close();

        println("Starting in " + Configuration.getStartDelay() + " seconds...");
        
        Thread.sleep(Configuration.getStartDelay() * 1000);
        
        audioPlayer.setMessage(generatedText);
        audioPlayer.run();
    }
    
    public static void main(String[] args) throws Exception {
        Configuration.setOutput(new ConsoleOutput());
        Configuration.loadProperties();

        new MorseCodePlayerConsole().run();
    }

}
