package com.bsep.marketingacency.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")		// dozvoljava cross-origin zahteve ka navedenim putanjama
                .allowedOrigins("https://localhost:4200")	// postavice Access-Control-Allow-Origin header u preflight zahtev
                .allowedMethods("*")
                .maxAge(3600);		// definise u sekundama koliko dugo se preflight response cuva u browseru
    }
}
