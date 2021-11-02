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
    private Integer taskId;

    @Setter
    @Getter
    private String name;

    public Task(String name){
        this.name = name;
    }

    @Getter
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private String type;

    @Getter
    private String category;

    @Getter
    private String description;

    public String getHourAndDayString(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return this.startTime.format(formatter)+ " - " + this.endTime.format(formatter);
    }
}
