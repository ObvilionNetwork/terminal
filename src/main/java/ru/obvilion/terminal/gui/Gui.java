package ru.obvilion.terminal.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ru.obvilion.terminal.utils.ResizeHelper;

public class Gui extends Application {
    private static Stage stage;

    public static boolean maximised = false;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;

        final ClassLoader loader = getClass().getClassLoader();
        final Parent root = FXMLLoader.load(loader.getResource("Frame.fxml"));

        root.getStylesheets().add((loader.getResource("style.css")).toExternalForm());

        stage.initStyle(StageStyle.TRANSPARENT);
        stage.getIcons().add(new Image(loader.getResourceAsStream("images/logo.png")));

        stage.setTitle("Obvilion Terminal");
        stage.setScene(new Scene(root));
        stage.show();

        stage.setMinWidth(200);
        stage.setMinHeight(130);
        stage.getScene().setFill(Color.TRANSPARENT);

        ResizeHelper.addResizeListener(stage);
    }

    public static Stage getStage() {
        return stage;
    }

    public static void maximise() {
        maximised = !maximised;

        final Screen screen = Screen.getPrimary();
        final Rectangle2D bounds = screen.getVisualBounds();

        final double height = bounds.getHeight();
        final double width = bounds.getWidth();
        final double x = width > height ? height * 1.078 : width * 0.8;
        final double y = width > height ? height * 0.61 : width * 0.5;

        if (maximised) {
            stage.setX(bounds.getMinX());
            stage.setY(bounds.getMinY());
            stage.setWidth(bounds.getWidth());
            stage.setHeight(height);
        } else {
            stage.setX((bounds.getWidth() - x) / 2);
            stage.setY((height - y) / 2);
            stage.setWidth(x);
            stage.setHeight(y);
        }
    }

    public static void load() {
        launch();
    }
}
