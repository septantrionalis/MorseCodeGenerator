package org.tdod.utils;

import org.tdod.MorseCodeMap;

import java.util.ArrayList;
import java.util.HashMap;

public class Utils {

    private static final HashMap<Character, String> morseCodeMap = MorseCodeMap.getMap();

    private void Utils() {
    }

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        } catch(NullPointerException e) {
            return false;
        }

        return true;
    }

    public static String getCleanInput(String input) {
        ArrayList<Character> validList = new ArrayList<Character>(morseCodeMap.keySet());
        StringBuffer lineBuffer = new StringBuffer();

        for (int i = 0; i < input.length(); i++) {
            String str = "" + input.charAt(i);
            for (char s:validList) {
                if (str.toLowerCase().equals(String.valueOf(s).toLowerCase())) {
                    lineBuffer.append(str);
                }
            }
        }

        return lineBuffer.toString();
    }


}
