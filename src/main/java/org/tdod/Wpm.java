package org.tdod;

public enum Wpm {

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
    WPM_25(36),
    WPM_26(35),
    WPM_27(34),
    WPM_28(33),
    WPM_29(32),
    WPM_30(31),
    WPM_0(0);
    
    private int dot;
   
    private Wpm(int dot) {
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

    
}
