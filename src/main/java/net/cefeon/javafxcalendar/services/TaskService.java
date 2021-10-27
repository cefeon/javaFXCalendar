package net.cefeon.javafxcalendar.services;

import net.cefeon.javafxcalendar.entities.Task;
import net.cefeon.javafxcalendar.repositories.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> findAll() {
        return this.taskRepository.findAll();
    }

    public void saveAll(List<Task> tasks){
        this.taskRepository.saveAll(tasks);
    }
}
