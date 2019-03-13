package com.antoine.Main;

import com.antoine.vue.frame.Frame;

import javax.swing.*;

public class Main {

    public static void main(String[] args){
        SwingUtilities.invokeLater(()->{
            new Frame();
         });
    }
}
