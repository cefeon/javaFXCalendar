package net.cefeon.javafxcalendar;

import javafx.scene.Group;
import lombok.*;
import net.cefeon.javafxcalendar.entities.Task;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Day extends Group {
    @Setter
    @Getter
    List<Task> taskList;

    @Setter
    @Getter
    LocalDateTime date;
}
