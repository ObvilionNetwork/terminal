package ru.obvilion.terminal.utils;

import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import ru.obvilion.terminal.gui.Gui;

public class MoveHelper {
    public static void addMoveListener(Node node) {
        final MoveHelper.MoveListener moveListener = new MoveHelper.MoveListener(node);

        node.addEventHandler(MouseEvent.MOUSE_PRESSED, moveListener);
        node.addEventHandler(MouseEvent.MOUSE_DRAGGED, moveListener);
        node.addEventHandler(MouseEvent.MOUSE_RELEASED, moveListener);
    }

    private static class MoveListener implements EventHandler<MouseEvent> {
        private final Node node;
        private double clickX = 0;
        private double clickY = 0;

        public MoveListener(Node node) {
            this.node = node;
        }

        @Override
        public void handle(MouseEvent event) {
            final EventType<? extends MouseEvent> eventType = event.getEventType();
            final Stage stage = Gui.getStage();
            
            if (ResizeHelper.isResize) return;

            if (MouseEvent.MOUSE_DRAGGED.equals(eventType)) {
                stage.setX(event.getScreenX() - clickX);
                stage.setY(event.getScreenY() - clickY);
            }
            else if (MouseEvent.MOUSE_RELEASED.equals(eventType)) {
                stage.setOpacity(1);
            }
            else if (MouseEvent.MOUSE_PRESSED.equals(eventType)) {
                clickX = event.getSceneX();
                clickY = event.getSceneY();

                stage.setOpacity(0.8);
            }
        }
    }
}
