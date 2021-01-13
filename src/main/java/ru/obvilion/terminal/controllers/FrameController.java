package ru.obvilion.terminal.controllers;

import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import ru.obvilion.terminal.gui.Gui;
import ru.obvilion.terminal.utils.MoveHelper;

import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class FrameController implements Initializable {
    public Circle CLOSE_BUTTON;
    public Circle MAXIMISE_BUTTON;
    public Circle HIDE_BUTTON;
    public Pane TOP_BAR;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Stage stage = Gui.getStage();

        CLOSE_BUTTON.setOnMouseClicked(e -> System.exit(0));
        MAXIMISE_BUTTON.setOnMouseClicked(e -> Gui.maximise());
        HIDE_BUTTON.setOnMouseClicked(e -> Gui.getStage().setIconified(true));

        MoveHelper.addMoveListener(TOP_BAR);
    }
}
