package org.tdod.api.impl;

import org.tdod.api.Output;

public class ConsoleOutput implements Output {

    @Override
    public void println(String message) {
        System.out.println(message);
    }

    @Override
    public void print(String message) {
        System.out.print(message);
    }

}
