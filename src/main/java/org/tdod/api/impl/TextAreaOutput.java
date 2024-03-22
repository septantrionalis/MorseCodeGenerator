package org.tdod.api.impl;

import org.tdod.api.Output;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.*;

public class TextAreaOutput implements Output {

    private JTextArea textArea;
    private ScheduledExecutorService executor = Executors.newScheduledThreadPool ( 1 );
    
    public TextAreaOutput(JTextArea textArea) {
        this.textArea = textArea;
        
        Runnable r = new Runnable () {
            @Override
            public void run () {
                try {  
                    // textArea.update(textArea.getGraphics());
                    textArea.setCaretPosition(textArea.getDocument().getLength());                

                    if ( Boolean.FALSE ) { 
                        executor.shutdown (); 
                    }

                } catch ( Exception e ) {
                    System.out.println ( "Oops, uncaught Exception surfaced at Runnable in ScheduledExecutorService." );
                }
            }
        };
        executor.scheduleAtFixedRate ( r , 0L , 1L , TimeUnit.SECONDS );

    }

    @Override
    public void println(String message) {
        textArea.append(message + "\n");
    }

    @Override
    public void print(String message) {
        System.out.print(message);
        textArea.append(message);
    }
    
}
