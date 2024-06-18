package com.gpt_hub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class GptHubApplication {

    public static void main(String[] args) {
        SpringApplication.run(GptHubApplication.class, args);
    }

}
