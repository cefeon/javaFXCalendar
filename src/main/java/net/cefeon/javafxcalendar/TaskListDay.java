package net.cefeon.javafxcalendar;


import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
public class TaskListDay {
    private Text dayName;
    private Text dayDate;

    @Getter
    private HBox hBox;

    public void setTextAndStyle(LocalDateTime localDateTime){
        dayName.setText("TODAY ");
        dayName.getStyleClass().add("taskListDay");
        dayName.getStyleClass().add("greenFill");
        dayName.setTextAlignment(TextAlignment.LEFT);

        dayDate.getStyleClass().add("taskListDay");
        dayDate.getStyleClass().add("blueFill");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        dayDate.setText(localDateTime.toLocalDate().format(formatter));
        dayDate.setTextAlignment(TextAlignment.RIGHT);

        hBox.setPrefHeight(10);
        hBox.getChildren().add(dayName);
        hBox.getChildren().add(dayDate);
    }
}
