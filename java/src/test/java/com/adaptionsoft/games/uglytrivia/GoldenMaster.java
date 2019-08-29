package com.adaptionsoft.games.uglytrivia;

import com.adaptionsoft.games.trivia.runner.GameRunner;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GoldenMaster {
    private static final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private static final PrintStream originalOut = System.out;


    public static String getResult(int i) {
        String result = null;
        try {
            result = new String(Files.readAllBytes(Paths.get("src","test","resources",String.format("output%d.txt", i))));
        } catch (IOException e) {
            result = generateExpectedResult(i);
        }
        return result;
    }

    private static String generateExpectedResult(int i){
        String result = null;
        System.setOut(new PrintStream(outContent));

        GameRunner.main(new String[]{Integer.toString(i)});

        try {
            Files.write(Paths.get("src","test","resources",String.format("output%d.txt",i)),outContent.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            result = outContent.toString();
            outContent.reset();
            System.setOut(originalOut);
            try {
                outContent.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
