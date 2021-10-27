package net.cefeon.javafxcalendar.repositories;

import net.cefeon.javafxcalendar.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
}