package com.bigbank.dragonsOfMugloar;

import com.bigbank.dragonsOfMugloar.application.GameEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Main Application class that runs the Spring Boot project and also implements the
 * CommandLineRunner interface in order to execute its run method. The run method
 * takes User Input for how many times the game should be played, and calls playGame method
 * inside GameEngine to run the game the same number of times.
 * This run method also receives the finalScore and displays the final score for all attempts
 * and the success rate.
 *
 *
 * @author Manish Gupta
 * @version $Id: DragonsOfMugloarApplication.java 1.0
 * @since 2020-11-28
 */
@SpringBootApplication
@Profile("!test")
public class DragonsOfMugloarApplication implements CommandLineRunner {

    private GameEngine gameEngine;

    @Autowired
    public void setGameEngine(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
    }

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(DragonsOfMugloarApplication.class, args);
    }

    @Override
    public void run(String... args) {
        System.out.println("_____________________________________________________");
        System.out.println("_____________________________________________________");
        System.out.println("Press number of times you want to play the game.");
        System.out.println("for example, Enter 10 and presss 'enter' to play the game 10 times");
        System.out.println("______________________________________________________");
        System.out.println("______________________________________________________");
        Scanner in = new Scanner(System.in);
        String input = in.nextLine();

        int numberOfGames = Integer.parseInt(input);
        List<Integer> finalScores = new ArrayList<>();
        int i = numberOfGames;
        while(i > 0) {
            int score = gameEngine.playGame();
            finalScores.add(score);
            i--;
        }
        long winCount = finalScores.stream().filter(score -> score > 1000).count();
        System.out.println("______________________________________________________");
        System.out.println("______________________________________________________");
        System.out.println("You won " + winCount + " out of " + numberOfGames );
        System.out.println("Your scores are: " );
        for (int score : finalScores)
            System.out.print(score + "  ");
        System.out.println("\n______________________________________________________");
        System.out.println("______________________________________________________");

    }
}
