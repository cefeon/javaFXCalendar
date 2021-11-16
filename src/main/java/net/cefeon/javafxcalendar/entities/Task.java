package net.cefeon.javafxcalendar.entities;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Setter
    private Integer taskId;

    @Setter
    @Getter
    private String name;

    public Task(String name){
        this.name = name;
    }

    @Getter
    @Setter
    private LocalDateTime startTime;

    @Getter
    @Setter
    private LocalDateTime endTime;

    private String type;

    @Getter
    @Setter
    private String category;

    @Getter
    @Setter
    private String description;

    public String getHourAndDayString(){
        return dateToTime(this.startTime) + " - " + dateToTime(this.endTime);
    }

    public String getStartTimeText(){
        return dateToTime(this.startTime);
    }

    public String getEndTimeText(){
        return dateToTime(this.endTime);
    }

    private String dateToTime(LocalDateTime localDateTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return localDateTime.format(formatter);
    }
}
