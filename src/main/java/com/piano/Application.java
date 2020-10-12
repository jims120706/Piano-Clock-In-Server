package com.piano;

import io.micronaut.http.context.event.HttpRequestReceivedEvent;
import io.micronaut.runtime.Micronaut;
import io.micronaut.runtime.event.annotation.EventListener;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;

@Singleton
public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        Micronaut.run(Application.class);
    }

    @EventListener
    void logRequest(HttpRequestReceivedEvent event) {
        if(event.getSource().getPath().startsWith("/health")) {
            return;
        }
        logger.info(event.toString());
    }

}