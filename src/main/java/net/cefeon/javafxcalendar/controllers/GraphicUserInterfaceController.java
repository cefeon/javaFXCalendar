package net.cefeon.javafxcalendar.controllers;

import javafx.fxml.FXML;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
@FxmlView("../../../../view/GraphicUserInterface.fxml")
public class GraphicUserInterfaceController {

    @Autowired
    private CalendarController calendarController;

    @Autowired
    private TaskListController taskListController;


    @FXML
    public void initialize() {


    }
}