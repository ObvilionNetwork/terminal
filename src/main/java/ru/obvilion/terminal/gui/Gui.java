package ru.obvilion.terminal.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ru.obvilion.terminal.utils.ResizeHelper;

public class Gui extends Application {
    private static Stage stage;

    public static double x;
    public static double y;
    public static double xClick;
    public static double yClick;

    public static double width;
    public static double height;

    public static boolean maximised = false;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;

        final ClassLoader loader = getClass().getClassLoader();
        final Parent root = FXMLLoader.load(loader.getResource("Frame.fxml"));

        stage.initStyle(StageStyle.UNDECORATED);
        stage.getIcons().add(new Image(loader.getResourceAsStream("images/logo.png")));

        stage.setTitle("Obvilion Terminal");
        stage.setScene(new Scene(root));
        stage.show();

        stage.setMinWidth(200);
        stage.setMinHeight(130);

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
