package com.bigbank.dragonsOfMugloar;

import com.bigbank.dragonsOfMugloar.application.GameEngine;
import org.hamcrest.core.Every;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Test class using junit and hamcrest to run the playGame method in GameEngine class.
 *This class uses TestRunnerApplication spring context to load up beans but not to run
 * CommandLineRunner run method, so that it doesn't wait for the user input and executes
 * independently.
 *
 * @author Manish Gupta
 * @version $Id: GameTest.java 1.0
 * @since 2020-11-11
 */
@SpringBootTest(classes = {TestRunnerApplication.class})
@ActiveProfiles("test")
public class GameTest {

    private final GameEngine gameEngine;

    @Autowired
    public GameTest(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
    }

    /**
     * This method uses a variable numberOfRuns to decide how many times to run the game.
     * It then runs the game that many times, and stores finalScore for all the iterations
     * of the game.
     * The test is to verify if all the scores are over 1000 or not.
     *
     */
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
