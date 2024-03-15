package org.tdod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;

public class AudioPlayer {

    Wpm wpm = Configuration.getWpm();
    
    private static final int FREQUENCY = 600;
    
    private static List<ToneBuffer> toneBuffers = new ArrayList<>();
    private static final HashMap<Character, String> morseCodeMap = MorseCodeMap.getMap();

    public AudioPlayer() {
        calculateBeep(wpm.getDot()); 
        calculateBeep(wpm.getDash());

    }
    
    public void calculateBeep(int duration) {
        try {
            
            byte[] toneBuffer = new byte[duration * 8];
            for (int i = 0; i < toneBuffer.length; i++) {
                double angle = i / (8000.0 / FREQUENCY) * 2.0 * Math.PI;
                toneBuffer[i] = (byte) (Math.sin(angle) * 127.0);
            }
            ToneBuffer buffer = new ToneBuffer(toneBuffer);
            toneBuffers.add(buffer);

        } catch (Exception e) {
            e.printStackTrace();
        }        
    }
    
    public void play(String input) throws Exception {
        AudioFormat format = new AudioFormat(8000, 8, 1, true, false);
        SourceDataLine line = AudioSystem.getSourceDataLine(format);
        line.open(format);
        line.start();

        for (int count = 0; count < 1; count++) {

            long start = System.currentTimeMillis();

            for (char c : input.toCharArray()) {
                String morseCode = morseCodeMap.get(c);
                if (morseCode != null) {
                    System.out.print(morseCode + " "); // Print Morse code
                                                       // representation
                    for (char symbol : morseCode.toCharArray()) {
                        if (symbol == '.') {
                            byte[] toneBuffer = toneBuffers.get(0).getToneBuffer();
                            line.write(toneBuffer, 0, toneBuffer.length);
                            line.drain();
                            Thread.sleep(wpm.getDot());
                        } else if (symbol == '-') {
                            byte[] toneBuffer = toneBuffers.get(1).getToneBuffer();
                            line.write(toneBuffer, 0, toneBuffer.length);
                            line.drain();
                            Thread.sleep(wpm.getDot());
                        } else if (symbol == '/') {
                            try {
                                Thread.sleep(wpm.getWordPause()); // Pause
                                                                  // between
                                                                  // words
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    try {
                        Thread.sleep(wpm.getCharPause()); // Pause between
                                                          // characters
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            long end = System.currentTimeMillis();
            float sec = (end - start) / 1000F;
            System.out.println();
            System.out.println("TIME = " + sec);
            System.out.println("WPM = " + 60 / sec);
        }

        line.stop();
        line.close();

    }

    
}
