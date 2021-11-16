package net.cefeon.javafxcalendar.controllers;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.cefeon.javafxcalendar.GraphicUserInterface;
import net.cefeon.javafxcalendar.TaskListItem;
import net.cefeon.javafxcalendar.entities.Task;
import net.cefeon.javafxcalendar.services.TaskService;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
@FxmlView("../../../../view/TaskDetails.fxml")
public class TaskDetailsController {
    private Stage stage;

    @FXML
    private AnchorPane taskDetailsWindow;

    @FXML
    private TextField taskName;

    @FXML
    private TextField startTime;

    @FXML
    private TextField endTime;

    @FXML
    private TextArea description;

    private final GraphicUserInterfaceController graphicUserInterfaceController;

    private final TaskService taskService;

    private final TaskListController taskListController;

    public TaskDetailsController(GraphicUserInterfaceController graphicUserInterfaceController, TaskService taskService, TaskListController taskListController) {
        this.graphicUserInterfaceController = graphicUserInterfaceController;
        this.taskService = taskService;
        this.taskListController = taskListController;
    }

    @FXML
    public void initialize() {
        this.stage = new Stage();
        setStageParameters(stage);
        this.stage.setScene(new Scene(taskDetailsWindow));
        closeIfNotFocused();
    }

    public void show(Task task) {
        setInitialData(task);
    }

    private void setInitialData(Task task){
        this.taskName.setText(task.getName());
        this.startTime.setText(task.getStartTimeText());
        this.endTime.setText(task.getEndTimeText());
        this.stage.show();
        taskName.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (Boolean.FALSE.equals(newVal)){
                task.setName(this.taskName.getText());
                taskService.addOrUpdate(task);
                taskListController.refresh();
            }
        });
        startTime.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (Boolean.FALSE.equals(newVal)){
                taskService.addOrUpdate(task);
                taskListController.refresh();
            }
        });

    }

    private void setStageParameters(Stage stage) {
        stage.setResizable(false);
        stage.initStyle(StageStyle.TRANSPARENT);
        stickToPaneTopRight(graphicUserInterfaceController.mainWindow);
    }

    private void stickToPaneTopRight(Pane pane){
        stage.setX(pane.getScene().getWindow().getX() + graphicUserInterfaceController.mainWindow.getWidth());
        stage.setY(pane.getScene().getWindow().getY());
    }

    private void closeIfNotFocused() {
        stage.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (Boolean.FALSE.equals(newValue)) {
                stage.close();
            }
        });
    }
}
