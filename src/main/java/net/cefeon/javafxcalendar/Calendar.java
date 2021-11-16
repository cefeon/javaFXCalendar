package net.cefeon.javafxcalendar;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class Calendar {
    @Getter
    @Setter
    private LocalDateTime selectedDate;

    public Calendar(){
        this.selectedDate = LocalDateTime.now();
    }
}
