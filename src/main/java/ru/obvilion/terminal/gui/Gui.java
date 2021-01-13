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

public class Gui extends Application {
    private static Stage stage;
    private static double x;
    private static double y;

    public static boolean maximised = false;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;

        final ClassLoader loader = getClass().getClassLoader();
        final Parent root = FXMLLoader.load(loader.getResource("Main.fxml"));

        stage.initStyle(StageStyle.UNDECORATED);
        stage.getIcons().add(new Image(loader.getResourceAsStream("images/logo.png")));

        stage.setTitle("Obvilion Terminal");
        stage.setScene(new Scene(root));
        stage.show();

        root.setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });

        root.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        });
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

        System.out.println(width);

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
