package ru.obvilion.terminal.controllers;

import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

import ru.obvilion.terminal.gui.Gui;
import ru.obvilion.terminal.logic.SSHConnection;
import ru.obvilion.terminal.utils.MoveHelper;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class TerminalController implements Initializable {
    public static final String[] colors = {"\\u001B\\[00;37m", "\\u001B\\[01;30m", "\\u001B\\[01;34m", "\\u001B\\[0m"};

    public Circle CLOSE_BUTTON;
    public Circle MAXIMISE_BUTTON;
    public Circle HIDE_BUTTON;

    public Pane TOP_BAR;
    public Label BAR_TITLE;
    public Label CONSOLE;
    public ScrollPane SCROLL;

    public String command = "";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        CLOSE_BUTTON.setOnMouseClicked(e -> Runtime.getRuntime().exit(0));
        MAXIMISE_BUTTON.setOnMouseClicked(e -> Gui.maximise());
        HIDE_BUTTON.setOnMouseClicked(e -> Gui.getStage().setIconified(true));

        SSHConnection con = new SSHConnection("root", "obvilionnetwork.ru", 2853, new File("C:\\Users\\Fatonn\\obv_root_rsa"));
        try {
            con.setInputNode(SCROLL);
            con.open();
        } catch (Exception e) {
            e.printStackTrace();
        }

        SCROLL.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                con.sendCommand(command);

                Platform.runLater(() -> {
                    try {
                        String str = con.out.toString("UTF-8");
                        for (String color : colors) {
                            str = str.replaceAll(color, "");
                        }

                        CONSOLE.setText(str);
                        SCROLL.setVvalue(1);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                });

                command = "";
                return;
            }

            command += event.getText();
        });

        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Platform.runLater(() -> {
                    try {
                        String str = con.out.toString();
                        for (String color : colors) {
                            str = str.replaceAll(color, "");
                        }

                        CONSOLE.setText(str + command);
                        SCROLL.setVvalue(1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        }).start();

        MoveHelper.addMoveListener(TOP_BAR);
    }
}