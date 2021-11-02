package net.cefeon.javafxcalendar;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.cefeon.javafxcalendar.controllers.GraphicUserInterfaceController;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

public class GraphicUserInterface extends Application {

    private ConfigurableApplicationContext applicationContext;

    @Override
    public void init() {
        String[] args = getParameters().getRaw().toArray(new String[0]);
        this.applicationContext = new SpringApplicationBuilder().sources(Main.class).run(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FxWeaver fxWeaver = applicationContext.getBean(FxWeaver.class);
        Parent root = fxWeaver.loadView(GraphicUserInterfaceController.class);
        Scene scene = new Scene(root);
        scene.getStylesheets().add("https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;700");
        stage.setScene(scene);
        setStageParameters(stage);
        setStageDraggable(stage, root);
        stage.show();
    }

    @Override
    public void stop() {
        this.applicationContext.close();
        Platform.exit();
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
