package ru.obvilion.terminal.controllers;

import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import ru.obvilion.terminal.gui.Gui;
import ru.obvilion.terminal.utils.MoveHelper;

import java.net.URL;
import java.util.ResourceBundle;

public class FrameController implements Initializable {
    public Circle CLOSE_BUTTON;
    public Circle MAXIMISE_BUTTON;
    public Circle HIDE_BUTTON;

    public Pane TOP_BAR;
    public AnchorPane root;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        root.setOpaqueInsets(new Insets(15, 15, 15, 15));

        CLOSE_BUTTON.setOnMouseClicked(e -> Runtime.getRuntime().exit(0));
        MAXIMISE_BUTTON.setOnMouseClicked(e -> Gui.maximise());
        HIDE_BUTTON.setOnMouseClicked(e -> Gui.getStage().setIconified(true));

        MoveHelper.addMoveListener(TOP_BAR);
    }
}
