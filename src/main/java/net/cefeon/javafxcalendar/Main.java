package net.cefeon.javafxcalendar;

import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        Application.launch(GraphicUserInterface.class, args);
    }
}
