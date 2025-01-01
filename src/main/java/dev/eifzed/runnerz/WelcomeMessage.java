package dev.eifzed.runnerz;

import org.springframework.stereotype.Component;

@Component
public class WelcomeMessage {

    public String getWelcomeMessage() {
        return "hello and welcome back";
    }
}
