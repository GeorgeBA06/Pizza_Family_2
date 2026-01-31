package com.example.pizza.exception;

import javax.swing.plaf.synth.SynthTabbedPaneUI;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(String message){
        super(message);
    }
}
