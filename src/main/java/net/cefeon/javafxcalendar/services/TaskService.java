package net.cefeon.javafxcalendar.services;

import net.cefeon.javafxcalendar.entities.Task;
import net.cefeon.javafxcalendar.repositories.TaskRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> list() {
        return this.taskRepository.findAll();
    }

    public void saveAll(List<Task> tasks) {
        this.taskRepository.saveAll(tasks);
    }

    public void add(Task task) {
        this.taskRepository.save(task);
    }

    public List<Task> listForDateTime(LocalDateTime localDateTime) {
        List<Task> list = taskRepository.findAll();
        return list.stream().filter(x ->
                    x.getStartTime().getYear() == localDateTime.getYear() &&
                    x.getStartTime().getDayOfYear() == localDateTime.getDayOfYear())
                .collect(Collectors.toList());
    }
}
