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
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;

@Component
@FxmlView("../../../../view/TaskList.fxml")
public class TaskListController {

    @Autowired
    private FxWeaver fxWeaver;

    @Getter
    @FXML
    ListView<Node> todayTaskList = new ListView<>();

    @Getter
    @FXML
    ListView<Node> tomorrowTaskList = new ListView<>();

    @FXML
    private VBox taskBox;

    @Autowired
    private TaskService taskService;

    public void displayTaskList(LocalDateTime date){
        ObservableList<Node> todayItems = FXCollections.observableArrayList (addDayLabel(date,"TODAY "), addDailyGrid(date));
        todayTaskList.setItems(todayItems);
        todayTaskList.setPrefHeight(170);
        todayTaskList.setId("todayTaskList");
        taskBox.getChildren().remove(todayTaskList);
        taskBox.getChildren().add(todayTaskList);
        ObservableList<Node> tomorrowItems = FXCollections.observableArrayList (addDayLabel(date.plusDays(1)," "), addDailyGrid(date.plusDays(1)));
        tomorrowTaskList.setItems(tomorrowItems);
        tomorrowTaskList.setPrefHeight(170);
        tomorrowTaskList.setId("tomorrowTaskList");
        taskBox.getChildren().remove(tomorrowTaskList);
        taskBox.getChildren().add(tomorrowTaskList);
    }

    private GridPane addDailyGrid(LocalDateTime date){
        List<Task> list = taskService.listForDateTime(date);
        GridPane gridPane = new GridPane();
        gridPane.getColumnConstraints().add(new ColumnConstraints(20));
        gridPane.getColumnConstraints().add(new ColumnConstraints(180));
        gridPane.getColumnConstraints().add(new ColumnConstraints(100));

        IntStream.range(0, list.size()).forEach(i->{
            Task x = list.get(i);
            addTaskGridElement(gridPane, x.getName(), x.getStartTimeText(), x.getEndTimeText(), x.getCategory(), i);
            gridPane.getRowConstraints().add(new RowConstraints(25));
        });
        return gridPane;
    }

    private HBox addDayLabel(LocalDateTime localDateTime, String text){
        TaskListDay taskListDay = new TaskListDay(new Text(text), new Text(), new HBox());
        taskListDay.setTextAndStyle(localDateTime);
        return taskListDay.getHBox();
    }

    private void addTaskGridElement(GridPane gridPane, String taskListItemName, String taskListStartTime, String taskListEndTime, String taskCategory, int row){
        TaskListItem taskListItem = new TaskListItem(new Text(), new Text(), new Rectangle(), "", "");
        addShowDetailsWindow(taskListItem);
        taskListItem.setTextAndStyle(taskListItemName, taskListStartTime, taskListEndTime, taskCategory);
        gridPane.add(taskListItem.getIcon(), 0, row);
        gridPane.add(taskListItem.getItemName(), 1,row);
        gridPane.add(taskListItem.getHours(), 2,row);
    }

    private void addExampleTask(){
        taskService.add(
                Task.builder()
                        .name("Zrobić śniadanie")
                        .startTime(LocalDateTime.now())
                        .endTime(LocalDateTime.now().plusHours(1))
                        .type("TODO")
                        .description("Posmarować jakieś kanapeczki masłem i położyć szynkę")
                        .category("blue")
                        .build()
        );
    }

    private void addShowDetailsWindow(TaskListItem taskListItem){
        taskListItem.getItemName().setOnMouseClicked(event->{
            fxWeaver.loadController(TaskDetailsController.class).show(taskListItem);
        });
    }

    @FXML
    public void initialize() {
        addExampleTask();
    }
}
