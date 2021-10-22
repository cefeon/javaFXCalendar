package net.cefeon.javafxcalendar.controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


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

    private LocalDate selectedDate;

    public void setCalendarMonth(String text) {
        this.calendarMonth.setText(text);
    }

    public void setCalendarYear(String text) {
        this.calendarYear.setText(text);
    }

    public void addNode(Node n) {
        this.leftContainer.getChildren().add(n);
    }

    public Map<Point, Node> getCalendarNodesMap() {
        Map<Point, Node> calendarNodes = new HashMap<>();
        this.calendarGridPane.getChildren().forEach(node ->
                calendarNodes.put(new Point(GridPane.getRowIndex(node), GridPane.getColumnIndex(node)), node));
        return calendarNodes;
    }

    public void setSelectedDate(LocalDate selectedDate) {
        this.selectedDate = selectedDate;
    }

    public void refreshCalendar() {
        setCalendarText();
        setCalendarYearAndMonth(this.selectedDate);
    }

    private void clearFills(Node calendarTextNode) {
        calendarTextNode.getStyleClass().remove("lightFill");
        calendarTextNode.getStyleClass().remove("darkFill");
    }

    public void setCalendarText() {
        int firstDayOfWeek = this.selectedDate.getDayOfWeek().getValue();
        getCalendarFields().forEach((coords, field) -> {
            clearFills(field);
            int calculatedDay = (int) (coords.getY() + (coords.getX() - 1) * 7) - firstDayOfWeek;
            if (calculatedDay > 0 && calculatedDay <= this.selectedDate.lengthOfMonth()) {
                field.setText(String.valueOf(calculatedDay));
                field.getStyleClass().add("lightFill");
            }
            if (calculatedDay <= 0) {
                field.setText(String.valueOf(this.selectedDate.plusMonths(-1).lengthOfMonth() + calculatedDay));
                field.getStyleClass().add("darkFill");
            }
            if (calculatedDay > this.selectedDate.lengthOfMonth()) {
                field.setText(String.valueOf(calculatedDay - this.selectedDate.lengthOfMonth()));
                field.getStyleClass().add("darkFill");
                field.setOnMouseClicked(event -> {
                    this.selectedDate = this.selectedDate.plusMonths(-1);
                    refreshCalendar();
                });
            }
        });
    }

    private String dateToMonthName(LocalDate localDate) {
        return localDate.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
    }

    public void setCalendarYearAndMonth(LocalDate localDate) {
        setCalendarMonth(dateToMonthName(localDate));
        setCalendarYear(String.valueOf(localDate.getYear()));
    }

    private Map<Point, Text> getCalendarFields() {
        Map<Point, Text> fromTextNodes = getCalendarNodesMap().entrySet().stream()
                .filter(x -> x.getValue() instanceof Text)
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> (Text) entry.getValue()));

        Map<Point, Text> fromVboxNodes = getCalendarNodesMap().entrySet().stream()
                .filter(x -> x.getValue() instanceof VBox)
                .collect(Collectors.toMap(Map.Entry::getKey, x -> (Text) ((VBox) x.getValue()).getChildren().get(0)));

        return Stream.of(fromTextNodes, fromVboxNodes)
                .flatMap(map -> map.entrySet().stream())
                .filter(x -> x.getKey().getX() >= 1 && x.getKey().getY() >= 1)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    }

    public void addSetSelectedDayOnClick() {
        getCalendarFields().forEach((coords, field) -> field.setOnMouseClicked(event -> {
            selectedDate = selectedDate.withDayOfMonth(Integer.parseInt(field.getText()));
            getCalendarFields().forEach((coords1,field1)->field1.getStyleClass().remove("selectedField"));
            field.getStyleClass().add("selectedField");
        }));
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

    public void initializeCalendar() {
        this.selectedDate = LocalDate.now();
        addAddMonthOnClick(this.rightArrowButton);
        addSubstractMonthOnClick(this.leftArrowButton);
        addSetSelectedDayOnClick();
        setCalendarYearAndMonth(selectedDate);
    }
}