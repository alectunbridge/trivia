package com.adaptionsoft.games.uglytrivia;

import com.adaptionsoft.games.trivia.runner.GameRunner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;

class GameRunnerTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() throws IOException {
        System.setOut(originalOut);
        outContent.close();
    }

    @Test
    void goldenMaster() {
        for (int i = 0; i < 10000; i++) {
            GameRunner.main(new String[]{Integer.toString(i)});
            assertThat(outContent.toString()).isEqualTo(GoldenMaster.getResult(i));
            outContent.reset();
        }
    }
}