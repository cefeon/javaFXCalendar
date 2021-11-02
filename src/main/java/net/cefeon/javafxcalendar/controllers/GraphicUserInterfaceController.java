package net.cefeon.javafxcalendar.controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import net.cefeon.javafxcalendar.entities.Task;
import net.cefeon.javafxcalendar.services.TaskService;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.awt.Point;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@FxmlView("../../../../view/GraphicUserInterface.fxml")
public class GraphicUserInterfaceController {
    @FXML
    private Text calendarMonth;

    @FXML
    private Text calendarYear;

    @FXML
    private AnchorPane leftContainer;

    @FXML
    private GridPane calendarGridPane;

    @FXML
    private Button rightArrowButton;

    @FXML
    private Button leftArrowButton;

    private LocalDateTime selectedDate;

    @Autowired
    private TaskListController taskListController;

    @Autowired
    private TaskService taskService;

    public void setCalendarMonth(String text) {
        this.calendarMonth.setText(text);
    }

    public void setCalendarYear(String text) {
        this.calendarYear.setText(text);
    }

    public Map<Point, Node> getGridNodes(GridPane gridPane) {
        Map<Point, Node> nodes = new HashMap<>();
        gridPane.getChildren().forEach(node ->
                nodes.put(new Point(GridPane.getRowIndex(node), GridPane.getColumnIndex(node)), node));
        return nodes;
    }

    public void refreshCalendar() {
        setCalendarText();
        setCalendarColor();
        setCalendarYearAndMonth(this.selectedDate);
    }

    private void clearStyle(Node calendarTextNode) {
        calendarTextNode.getStyleClass().remove("lightFill");
        calendarTextNode.getStyleClass().remove("darkFill");
    }

    public void setCalendarText() {
        int firstDayOfWeek = this.selectedDate.getDayOfWeek().getValue();
        getCalendarFields().forEach((coords, field) -> {
            int calculatedDay = (int) (coords.getY() + (coords.getX() - 1) * 7) - firstDayOfWeek;
            if (calculatedDay > 0 && calculatedDay <= this.selectedDate.toLocalDate().lengthOfMonth()) {
                field.setText(String.valueOf(calculatedDay));
                addSetSelectedDayOnClick(field);
            } else if (calculatedDay <= 0) {
                field.setText(String.valueOf(this.selectedDate.plusMonths(-1).toLocalDate().lengthOfMonth() + calculatedDay));
                field.setOnMouseClicked(event -> {
                });
            } else {
                field.setText(String.valueOf(calculatedDay - this.selectedDate.toLocalDate().lengthOfMonth()));
                field.setOnMouseClicked(event -> {
                });
            }
        });
    }

    public void setCalendarColor() {
        getCalendarFields().forEach((coords, field) -> {
            clearStyle(field);
            if (isCalendarNodeCurrentMonth(coords)) {
                field.getStyleClass().add("lightFill");
            } else {
                field.getStyleClass().add("darkFill");
            }
        });
    }

    private boolean isCalendarNodeCurrentMonth(Point coords) {
        int firstDayOfWeek = this.selectedDate.getDayOfWeek().getValue();
        int calculatedDay = (int) (coords.getY() + (coords.getX() - 1) * 7) - firstDayOfWeek;
        return calculatedDay > 0 && calculatedDay <= this.selectedDate.toLocalDate().lengthOfMonth();
    }

    private String dateToMonthName(LocalDateTime localDate) {
        return localDate.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
    }

    public void setCalendarYearAndMonth(LocalDateTime localDate) {
        setCalendarMonth(dateToMonthName(localDate));
        setCalendarYear(String.valueOf(localDate.getYear()));
    }

    private Map<Point, Text> getCalendarFields() {
        Map<Point, Text> fromTextNodes = getGridNodes(this.calendarGridPane).entrySet().stream()
                .filter(x -> x.getValue() instanceof Text)
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> (Text) entry.getValue()));

        Map<Point, Text> fromVboxNodes = getGridNodes(this.calendarGridPane).entrySet().stream()
                .filter(x -> x.getValue() instanceof VBox)
                .collect(Collectors.toMap(Map.Entry::getKey, x -> (Text) ((VBox) x.getValue()).getChildren().get(0)));

        return Stream.of(fromTextNodes, fromVboxNodes)
                .flatMap(map -> map.entrySet().stream())
                .filter(x -> x.getKey().getX() >= 1 && x.getKey().getY() >= 1)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    }

    public void addSetSelectedDayOnClick(Text text) {
        text.setOnMouseClicked(event -> {
            getCalendarFields().forEach((coords, field1) -> field1.getStyleClass().remove("selectedField"));
            this.selectedDate = selectedDate.withDayOfMonth(Integer.parseInt(text.getText()));
            taskListController.refreshTaskList(this.selectedDate);
/*            taskListController.getController().show();*/
            text.getStyleClass().add("selectedField");
        });
    }

    public void addAddMonthOnClick(Button button) {
        button.setOnAction(event -> {
            this.selectedDate = this.selectedDate.plusMonths(1);
            refreshCalendar();
        });
    }

    public void addSubstractMonthOnClick(Button button) {
        button.setOnAction(event -> {
            this.selectedDate = this.selectedDate.plusMonths(-1);
            refreshCalendar();
        });
    }

    @FXML
    public void initialize() {
        this.selectedDate = LocalDateTime.now();
        addAddMonthOnClick(this.rightArrowButton);
        addSubstractMonthOnClick(this.leftArrowButton);
        setCalendarYearAndMonth(this.selectedDate);
        refreshCalendar();
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
}