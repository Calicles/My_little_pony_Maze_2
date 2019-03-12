package com.antoine.Main;

import com.antoine.vue.frame.Frame;
import org.xml.sax.SAXException;

import javax.swing.*;

public class Main {

    public static void main(String[] args){
        SwingUtilities.invokeLater(()-> {
            new Frame();
        });
    }
}
