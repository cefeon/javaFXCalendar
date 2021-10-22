package net.cefeon.javafxcalendar;

import java.time.LocalDate;

public class Task {
    private String name;
    private LocalDate startTime;
    private LocalDate endTime;
    private enum type {
        FINISHED, WHOLEDAY, TODO
    }
    private Category category;
    private String description;
}
