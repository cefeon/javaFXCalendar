package net.cefeon.javafxcalendar;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import lombok.Getter;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TaskListItem extends Node {

    @Getter
    Text taskListItemName;
    @Getter
    Text taskListHours;
    @Getter
    Rectangle taskListIcon;

    public void setTextAndStyle(String taskListItemName, String taskListHours, String taskCategory){
        this.taskListItemName.setText(taskListItemName);
        this.taskListItemName.prefHeight(20);
        this.taskListItemName.getStyleClass().add("taskListItemName");
        this.taskListItemName.getStyleClass().add("lightFill");

        this.taskListHours.setText(taskListHours);
        this.taskListHours.getStyleClass().add("taskListHours");
        this.taskListHours.getStyleClass().add("darkFill");

        this.taskListIcon.setWidth(8);
        this.taskListIcon.setHeight(8);
        this.taskListIcon.getStyleClass().add("taskListIcon");
        this.taskListIcon.getStyleClass().add(taskCategory+"Fill");
        this.taskListIcon.setArcHeight(5);
        this.taskListIcon.setArcWidth(5);
    }
}
