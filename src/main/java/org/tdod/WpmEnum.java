package org.tdod;

public enum WpmEnum {

    WPM_5(192),
    WPM_6(160),
    WPM_7(137),
    WPM_8(120),
    WPM_9(106),
    WPM_10(96),
    WPM_11(89),
    WPM_12(79),
    WPM_13(73),
    WPM_14(67),
    WPM_15(63),
    WPM_16(59),
    WPM_17(54),
    WPM_18(53),    
    WPM_19(49),
    WPM_20(46),
    WPM_21(43),
    WPM_22(42),
    WPM_23(40),
    WPM_24(48),
    WPM_25(36),
    WPM_26(35),
    WPM_27(34),
    WPM_28(33),
    WPM_29(32),
    WPM_30(31),
    WPM_0(0);
    
    private int dot;
   
    private WpmEnum(int dot) {
        this.dot = dot;
    }

    public int getDot() {
        return dot;
    }

    public int getDash() {
        return dot * 3;
    }
    
    public int getCharPause() {
        return dot * 3;
    }
    
    public int getWordPause() {
        return dot * 7;
    }

    public static WpmEnum getWpm(Integer wpmInt) {
        switch (wpmInt) {
        case 5: return WPM_5;
        case 6: return WPM_6;
        case 7: return WPM_7;
        case 8: return WPM_8;
        case 9: return WPM_9;
        case 10: return WPM_10;
        case 11: return WPM_11;
        case 12: return WPM_12;
        case 13: return WPM_13;
        case 14: return WPM_14;
        case 15: return WPM_15;
        case 16: return WPM_16;
        case 17: return WPM_17;
        case 18: return WPM_18;
        case 19: return WPM_19;
        case 20: return WPM_20;
        case 21: return WPM_21;
        case 22: return WPM_22;
        case 23: return WPM_23;
        case 24: return WPM_24;
        case 25: return WPM_25;
        case 26: return WPM_26;
        case 27: return WPM_27;
        case 28: return WPM_28;
        case 29: return WPM_29;
        case 30: return WPM_30;
        default:
            System.out.println("WPM " + wpmInt + " not supported.");
            throw new RuntimeException("WPM " + wpmInt + " not supported.");
        }
    }
    
}
