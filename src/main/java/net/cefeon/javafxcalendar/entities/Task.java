package net.cefeon.javafxcalendar.entities;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer taskId;

    @Setter
    @Getter
    private String name;

    public Task(String name){
        this.name = name;
    }

    private LocalDate startTime;
    private LocalDate endTime;

    private enum type {
        FINISHED, WHOLEDAY, TODO
    }

    @OneToMany
    private Set<Category> categories;

    private String description;

    public Integer getId() {
        return taskId;
    }

}
