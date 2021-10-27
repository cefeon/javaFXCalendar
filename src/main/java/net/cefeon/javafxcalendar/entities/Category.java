package net.cefeon.javafxcalendar.entities;

import javax.persistence.*;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer categoryId;

    private String name;
    private String color;

    @ManyToOne
    @JoinColumn(name="taskId", nullable=false)
    private Task task;

    public Integer getId() {
        return categoryId;
    }
}
