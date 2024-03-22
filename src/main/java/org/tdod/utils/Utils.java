package org.tdod.utils;

import org.tdod.MorseCodeMap;

import java.util.ArrayList;
import java.util.HashMap;

public class Utils {

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


}
