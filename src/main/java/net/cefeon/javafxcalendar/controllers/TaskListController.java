package net.cefeon.javafxcalendar.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import lombok.Getter;
import net.cefeon.javafxcalendar.TaskListDay;
import net.cefeon.javafxcalendar.TaskListItem;
import net.cefeon.javafxcalendar.entities.Task;
import net.cefeon.javafxcalendar.services.TaskService;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@FxmlView("../../../../view/TaskList.fxml")
public class TaskListController {
    @Getter
    @FXML
    ListView<Node> taskList = new ListView<>();

    @FXML
    private VBox taskBox;

    @Autowired
    private TaskService taskService;


    public void refreshTaskList(LocalDateTime selectedDate){
        ObservableList<Node> items = FXCollections.observableArrayList (addDayLabel(selectedDate), addDailyGrid(selectedDate));
        taskList.setItems(items);
        taskList.setPrefHeight(150);
        taskList.setId("taskList");
        taskBox.getChildren().remove(taskList);
        taskBox.getChildren().add(taskList);
        taskBox.getChildren().removeAll();
    }

    private GridPane addDailyGrid(LocalDateTime date){
        List<Task> list = taskService.listForDateTime(date);
        GridPane gridPane = new GridPane();
        gridPane.getColumnConstraints().add(new ColumnConstraints(20));
        gridPane.getColumnConstraints().add(new ColumnConstraints(180));
        gridPane.getColumnConstraints().add(new ColumnConstraints(100));
        for (int i = 0; i<list.size(); i++){
            Task x = list.get(i);
            addTaskGridElement(gridPane, x.getName(), x.getHourAndDayString(), x.getCategory(), i);
            gridPane.getRowConstraints().add(new RowConstraints(25));
        }
        return gridPane;
    }

    private HBox addDayLabel(LocalDateTime localDateTime){
        TaskListDay taskListDay = new TaskListDay(new Text(), new Text(), new HBox());
        taskListDay.setTextAndStyle(localDateTime);
        return taskListDay.getHBox();
    }

    private void addTaskGridElement(GridPane gridPane, String taskListItemName, String taskListHours, String taskCategory, int row){
        TaskListItem taskListItem = new TaskListItem(new Text(), new Text(), new Rectangle());
        taskListItem.setTextAndStyle(taskListItemName, taskListHours, taskCategory);
        gridPane.add(taskListItem.getTaskListIcon(), 0, row);
        gridPane.add(taskListItem.getTaskListItemName(), 1,row);
        gridPane.add(taskListItem.getTaskListHours(), 2,row);
    }

    @FXML
    public void initialize() {
        System.out.println("initialized");
    }
}