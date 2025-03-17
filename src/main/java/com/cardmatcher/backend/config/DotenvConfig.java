package com.cardmatcher.backend.config;

import org.springframework.context.annotation.Configuration;
import jakarta.annotation.PostConstruct;
import io.github.cdimascio.dotenv.Dotenv;

@Configuration
public class DotenvConfig {

    private final Dotenv dotenv;

    public DotenvConfig() {
        this.dotenv = Dotenv.configure().load();
    }

    @PostConstruct
    public void postConstruct() {
        System.setProperty("DB_URL", dotenv.get("DB_URL"));
        System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
        System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
        
        System.out.println("DB_URL: " + dotenv.get("DB_URL"));
    }
}
