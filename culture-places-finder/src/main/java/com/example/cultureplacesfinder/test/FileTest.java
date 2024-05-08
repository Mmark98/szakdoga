package com.example.cultureplacesfinder.test;

import java.io.File;

public class FileTest {
    public static void main(String[] args) {
        File file = new File("rdf/heritage.ttl");
        if (file.exists()) {
            System.out.println("A fájl létezik. "+file.exists());
        } else {
            System.out.println("A fájl nem létezik.");
        }
    }
}
