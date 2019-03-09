package com.antoine.services;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class ImageReaderTest {

    @Test
    public void lireImage() throws IOException {
        ImageReader.lireImage("./ressources/images/tapis1.png");
    }

    @Test
    public void lireImage1() {
    }
}