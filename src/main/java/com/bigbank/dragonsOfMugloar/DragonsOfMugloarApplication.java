package com.bigbank.dragonsOfMugloar;

import com.bigbank.dragonsOfMugloar.application.GameEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestTemplate;

import java.util.Scanner;

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
        while (true) {
            System.out.println("_____________________________________________________");
            System.out.println("_____________________________________________________");
            System.out.println("Press Any key and then Enter to play the Game...");
            System.out.println("Press 'N' and then Enter to stop the Game...");
            System.out.println("______________________________________________________");
            System.out.println("______________________________________________________");
            Scanner in = new Scanner(System.in);
            String input = in.nextLine();

            if(input.startsWith("N") || input.startsWith("n"))
                break;

            int finalScore = gameEngine.playGame();
            System.out.println("Game Over\n+++++++++++++++++++++++++++++++++");
            System.out.println("You scored " + finalScore);
            System.out.println("+++++++++++++++++++++++++++++++++");
        }
    }
}
