package org.tdod.api;

public interface AudioPlayer extends Runnable {

    void setMessage(String input) throws Exception;
    void terminate();

    
}
