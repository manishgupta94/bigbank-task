package com.bigbank.dragonsOfMugloar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestTemplate;

/**
 * This SpringBoot application is used to execute test method without waiting for user input
 * This provides a different context to run the test method in GameTest class.
 *
 *
 * @author Manish Gupta
 * @version $Id: TestRunnerApplication.java 1.0
 * @since 2020-11-28
 */
@SpringBootApplication
public class TestRunnerApplication {

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(TestRunnerApplication.class, args);
    }

}
