package com.bigbank.dragonsofmugloar;

import org.hamcrest.core.Every;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest(classes = {TestRunnerApplication.class})
@ActiveProfiles("test")
public class GameTest {

    private final GameEngine gameEngine;

    @Autowired
    public GameTest(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
    }

    @Test
    public void playGameMultipleTimesAndMakeSureScoreIsOver1000() {
        int numberOfRuns = 10;
        List<Integer> listOfFinalScores = new ArrayList<>();
        for (int i = 0; i < numberOfRuns; i++) {
            int finalScore = gameEngine.playGame();
            listOfFinalScores.add(finalScore);
        }
        for (int finalScore: listOfFinalScores)
            System.out.println(finalScore);
        assertThat("Test list of final Scores has size same as numberOfRuns",
                listOfFinalScores,
                hasSize(numberOfRuns));
        assertThat("Test all the final Scores in numberOfRuns are over 1000",
                listOfFinalScores,
                Every.everyItem(greaterThan(1000)));
    }

}
