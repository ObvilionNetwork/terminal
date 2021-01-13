package ru.obvilion.terminal.controllers;

import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import ru.obvilion.terminal.gui.Gui;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public Circle CLOSE_BUTTON;
    public Circle MAXIMISE_BUTTON;
    public Circle HIDE_BUTTON;

    public Pane RESIZE_DOWN;
    public Pane RESIZE_UP;
    public Pane RESIZE_RIGHT;
    public Pane RESIZE_LEFT;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        CLOSE_BUTTON.setOnMouseClicked(e -> System.exit(0));
        MAXIMISE_BUTTON.setOnMouseClicked(e -> Gui.maximise());
        HIDE_BUTTON.setOnMouseClicked(e -> Gui.getStage().setIconified(true));
    }
}
