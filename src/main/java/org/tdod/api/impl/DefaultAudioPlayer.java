package org.tdod.api.impl;

import org.tdod.Configuration;
import org.tdod.MorseCodeMap;
import org.tdod.api.AudioPlayer;
import org.tdod.model.enums.TextSourceEnum;
import org.tdod.model.enums.ToneBuffer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;

public class DefaultAudioPlayer implements AudioPlayer {
    
    private static List<ToneBuffer> toneBuffers = new ArrayList<>();
    private static final HashMap<Character, String> morseCodeMap = MorseCodeMap.getMap();

    private String input;
    private boolean terminate = false;
    
    
    public DefaultAudioPlayer() {
        calculateBeep(Configuration.getWpm().getDot());
        calculateBeep(Configuration.getWpm().getDash());
    }

    @Override
    public void setMessage(String input) throws Exception {
        this.input = input;
    }

    @Override
    public void run() {
        terminate = false;
        try {
            AudioFormat format = new AudioFormat(8000, 8, 1, true, false);
            SourceDataLine line = AudioSystem.getSourceDataLine(format);
            line.open(format);
            line.start();

            for (int count = 0; count < 1; count++) {

                long start = System.currentTimeMillis();

                for (char c : input.toCharArray()) {
                    if (terminate) {
                        line.drain();
                        line.stop();
                        line.close();
                        return;
                    }
                    String morseCode = morseCodeMap.get(c);
                    if (morseCode != null) {
                        Configuration.getOutput().print("" + c);
                        for (char symbol : morseCode.toCharArray()) {
                            if (symbol == '.') {
                                byte[] toneBuffer = toneBuffers.get(0).getToneBuffer();
                                line.write(toneBuffer, 0, toneBuffer.length);
                                line.drain();
                                Thread.sleep(Configuration.getWpm().getDot());
                            } else if (symbol == '-') {
                                byte[] toneBuffer = toneBuffers.get(1).getToneBuffer();
                                line.write(toneBuffer, 0, toneBuffer.length);
                                line.drain();
                                Thread.sleep(Configuration.getWpm().getDot());
                            } else if (symbol == '/') {
                                try {
                                    Thread.sleep(Configuration.getWpm().getWordPause());
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        try {
                            Thread.sleep(Configuration.getWpm().getCharPause());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

                long end = System.currentTimeMillis();
                float sec = (end - start) / 1000F;
                System.out.println();
                System.out.println("TIME = " + sec + " seconds.");
                
                if (Configuration.getTextSource().equals(TextSourceEnum.PARIS)) {
                    System.out.println("WPM = " + 60 / sec);                
                }
            }

            line.stop();
            line.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void terminate() {
        terminate = true;
    }

    
    private void calculateBeep(int duration) {
        try {

            byte[] toneBuffer = new byte[duration * 8];
            for (int i = 0; i < toneBuffer.length; i++) {
                double angle = i / (8000.0 / Configuration.getFrequency()) * 2.0 * Math.PI;
                toneBuffer[i] = (byte) (Math.sin(angle) * 127.0);

                // Trying to adjust the tone...
                if (toneBuffer[i] < -100) toneBuffer[i] = (byte)-100;
                if (toneBuffer[i] > 100) toneBuffer[i] = (byte)100;
            }
            ToneBuffer buffer = new ToneBuffer(toneBuffer);
            toneBuffers.add(buffer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
