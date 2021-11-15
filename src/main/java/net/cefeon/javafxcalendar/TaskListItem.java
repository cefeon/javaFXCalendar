package net.cefeon.javafxcalendar;

import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class TaskListItem extends Node {

    @Getter
    Text itemName;
    @Getter
    Text hours;
    @Getter
    Rectangle icon;
    @Getter
    String startTime;
    @Getter
    String endTime;

    public void setTextAndStyle(String taskListItemName, String taskListStartTime, String taskListEndTime, String taskCategory){
        this.itemName.setText(taskListItemName);
        this.itemName.prefHeight(20);
        this.itemName.getStyleClass().add("taskListItemName");
        this.itemName.getStyleClass().add("lightFill");
        this.startTime = taskListStartTime;
        this.endTime = taskListEndTime;
        this.hours.setText(taskListStartTime+" - "+taskListEndTime);
        this.hours.getStyleClass().add("taskListHours");
        this.hours.getStyleClass().add("darkFill");
        this.icon.setWidth(8);
        this.icon.setHeight(8);
        this.icon.getStyleClass().add("taskListIcon");
        this.icon.getStyleClass().add(taskCategory+"Fill");
        this.icon.setArcHeight(5);
        this.icon.setArcWidth(5);
    }
}
