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
    @Column(name = "taskId", nullable = false)
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return this.startTime.format(formatter)+ " - " + this.endTime.format(formatter);
    }

    public String getStartTimeText(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return this.startTime.format(formatter);
    }

    public String getEndTimeText(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return this.endTime.format(formatter);
    }
}
