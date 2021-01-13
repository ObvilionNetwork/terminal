package ru.obvilion.terminal.utils;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ResizeHelper {
    public static boolean isResize = false;

    public static void addResizeListener(Stage stage) {
        final Scene scene = stage.getScene();
        final ResizeListener resizeListener = new ResizeListener(stage);

        scene.addEventHandler(MouseEvent.MOUSE_MOVED, resizeListener);
        scene.addEventHandler(MouseEvent.MOUSE_PRESSED, resizeListener);
        scene.addEventHandler(MouseEvent.MOUSE_RELEASED, resizeListener);
        scene.addEventHandler(MouseEvent.MOUSE_DRAGGED, resizeListener);
        scene.addEventHandler(MouseEvent.MOUSE_EXITED, resizeListener);
        scene.addEventHandler(MouseEvent.MOUSE_EXITED_TARGET, resizeListener);

        final ObservableList<Node> children = scene.getRoot().getChildrenUnmodifiable();
        for (Node child : children) {
            addListenerDeeply(child, resizeListener);
        }
    }

    public static void addListenerDeeply(Node node, EventHandler<MouseEvent> listener) {
        node.addEventHandler(MouseEvent.MOUSE_MOVED, listener);
        node.addEventHandler(MouseEvent.MOUSE_PRESSED, listener);
        node.addEventHandler(MouseEvent.MOUSE_RELEASED, listener);
        node.addEventHandler(MouseEvent.MOUSE_DRAGGED, listener);
        node.addEventHandler(MouseEvent.MOUSE_EXITED, listener);
        node.addEventHandler(MouseEvent.MOUSE_EXITED_TARGET, listener);

        if (node instanceof Parent) {
            final Parent parent = (Parent) node;
            final ObservableList<Node> children = parent.getChildrenUnmodifiable();

            for (Node child : children) {
                addListenerDeeply(child, listener);
            }
        }
    }

    private static class ResizeListener implements EventHandler<MouseEvent> {
        private final Stage stage;
        private final int border = 4;

        private Cursor cursorEvent = Cursor.DEFAULT;
        private double startX = 0;
        private double startY = 0;

        public ResizeListener(Stage stage) {
            this.stage = stage;
        }

        @Override
        public void handle(MouseEvent mouseEvent) {
            final EventType<? extends MouseEvent> mouseEventType = mouseEvent.getEventType();
            final Scene scene = stage.getScene();

            final double mouseEventX = mouseEvent.getSceneX(),
                mouseEventY = mouseEvent.getSceneY(),
                sceneWidth = scene.getWidth(),
                sceneHeight = scene.getHeight();

            if (MouseEvent.MOUSE_MOVED.equals(mouseEventType)) {
                isResize = true;

                if (mouseEventX < border && mouseEventY < border) {
                    cursorEvent = Cursor.NW_RESIZE;
                }
                else if (mouseEventX < border && mouseEventY > sceneHeight - border) {
                    cursorEvent = Cursor.SW_RESIZE;
                }
                else if (mouseEventX > sceneWidth - border && mouseEventY < border) {
                    cursorEvent = Cursor.NE_RESIZE;
                }
                else if (mouseEventX > sceneWidth - border && mouseEventY > sceneHeight - border) {
                    cursorEvent = Cursor.SE_RESIZE;
                }
                else if (mouseEventX < border) {
                    cursorEvent = Cursor.W_RESIZE;
                }
                else if (mouseEventX > sceneWidth - border) {
                    cursorEvent = Cursor.E_RESIZE;
                }
                else if (mouseEventY < border) {
                    cursorEvent = Cursor.N_RESIZE;
                }
                else if (mouseEventY > sceneHeight - border) {
                    cursorEvent = Cursor.S_RESIZE;
                }
                else {
                    isResize = false;
                    cursorEvent = Cursor.DEFAULT;
                }

                scene.setCursor(cursorEvent);
            } else {
                if (MouseEvent.MOUSE_EXITED.equals(mouseEventType) || MouseEvent.MOUSE_EXITED_TARGET.equals(mouseEventType)){
                    scene.setCursor(Cursor.DEFAULT);
                }
                else if (MouseEvent.MOUSE_PRESSED.equals(mouseEventType)) {
                    startX = stage.getWidth() - mouseEventX;
                    startY = stage.getHeight() - mouseEventY;
                }
                else if (MouseEvent.MOUSE_DRAGGED.equals(mouseEventType)) {
                    if (!Cursor.DEFAULT.equals(cursorEvent)) {
                        if (!Cursor.W_RESIZE.equals(cursorEvent) && !Cursor.E_RESIZE.equals(cursorEvent)) {
                            double minHeight = stage.getMinHeight() > (border * 2) ? stage.getMinHeight() : (border * 2);
                            if (Cursor.NW_RESIZE.equals(cursorEvent) || Cursor.N_RESIZE.equals(cursorEvent) || Cursor.NE_RESIZE.equals(cursorEvent)) {
                                if (stage.getHeight() > minHeight || mouseEventY < 0) {
                                    stage.setHeight(stage.getY() - mouseEvent.getScreenY() + stage.getHeight());
                                    stage.setY(mouseEvent.getScreenY());
                                }
                            } else {
                                if (stage.getHeight() > minHeight || mouseEventY + startY - stage.getHeight() > 0) {
                                    stage.setHeight(mouseEventY + startY);
                                }
                            }
                        }

                        if (!Cursor.N_RESIZE.equals(cursorEvent) && !Cursor.S_RESIZE.equals(cursorEvent)) {
                            double minWidth = stage.getMinWidth() > (border * 2) ? stage.getMinWidth() : (border * 2);
                            if (Cursor.NW_RESIZE.equals(cursorEvent) || Cursor.W_RESIZE.equals(cursorEvent) || Cursor.SW_RESIZE.equals(cursorEvent)) {
                                if (stage.getWidth() > minWidth || mouseEventX < 0) {
                                    stage.setWidth(stage.getX() - mouseEvent.getScreenX() + stage.getWidth());
                                    stage.setX(mouseEvent.getScreenX());
                                }
                            } else {
                                if (stage.getWidth() > minWidth || mouseEventX + startX - stage.getWidth() > 0) {
                                    stage.setWidth(mouseEventX + startX);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
