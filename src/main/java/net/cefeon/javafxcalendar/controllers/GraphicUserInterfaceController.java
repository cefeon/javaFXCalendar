package net.cefeon.javafxcalendar.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;

@Component
@FxmlView("../../../../view/GraphicUserInterface.fxml")
public class GraphicUserInterfaceController {

    @FXML
    Pane mainWindow;

    @Autowired
    private CalendarController calendarController;

    @Autowired
    private TaskListController taskListController;

    @Autowired
    private TaskDetailsController taskDetailsController;

    @FXML
    public void initialize() {
        String month = LocalDateTime.now().getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        String background = "-fx-background-image: url('https://source.unsplash.com/random/400x800?"+month.toLowerCase(Locale.ROOT)+"')";
        mainWindow.setStyle(background);
    }
}