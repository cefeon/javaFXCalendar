package net.cefeon.javafxcalendar;

import javafx.application.Application;
import net.cefeon.javafxcalendar.controllers.GraphicUserInterfaceController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.time.LocalDate;

public class GraphicUserInterface extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/GraphicUserInterface.fxml"));
        Parent root = loader.load();
        GraphicUserInterfaceController graphicUserInterfaceController = loader.getController();
        graphicUserInterfaceController.initializeCalendar();
        graphicUserInterfaceController.setCalendarText();
        Scene scene = new Scene(root);
        scene.getStylesheets().add("https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;700");
        stage.setScene(scene);
        setStageParameters(stage);
        setStageDraggable(stage, root);
        stage.show();
    }

    private void setStageDraggable(Stage stage, Node root) {
        root.setOnMousePressed(pressEvent -> root.setOnMouseDragged(dragEvent -> {
            stage.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
            stage.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
        }));
    }

    private void setStageParameters(Stage stage) {
        stage.setTitle("JavaFX Test");
        stage.setResizable(false);
        Image image = new Image("/images/cefeon.png");
        stage.getIcons().add(image);
        stage.initStyle(StageStyle.TRANSPARENT);
    }
}
