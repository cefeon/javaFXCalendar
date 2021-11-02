package net.cefeon.javafxcalendar.controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@FxmlView("../../../../view/Calendar.fxml")
public class CalendarController {
    private LocalDateTime selectedDate;

    @FXML
    private Text calendarMonth;

    @FXML
    private Text calendarYear;

    @FXML
    private GridPane calendarGridPane;

    @FXML
    private Button rightArrowButton;

    @FXML
    private Button leftArrowButton;

    @Autowired
    private TaskListController taskListController;

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

    public void addSetSelectedDayOnClick(Text text) {
        text.setOnMouseClicked(event -> {
            this.getCalendarFields().forEach((coords, field1) -> field1.getStyleClass().remove("selectedField"));
            this.selectedDate = selectedDate.withDayOfMonth(Integer.parseInt(text.getText()));
            taskListController.displayTaskList(this.selectedDate);
            text.getStyleClass().add("selectedField");
        });
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

    public Map<Point, Text> getCalendarFields() {
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

    public void addAddMonthOnClick(javafx.scene.control.Button button) {
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
    }
}
