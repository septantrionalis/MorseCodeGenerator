package org.tdod.model.enums;

public class ToneBuffer {

    private byte[] toneBuffer;
    
    public ToneBuffer(byte[] toneBuffer) {
        this.toneBuffer = toneBuffer;        
    }
    
    public byte[] getToneBuffer() {
        return toneBuffer;
    }
    
}
