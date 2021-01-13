package ru.obvilion.terminal.utils;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.Transition;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.util.Duration;

public class StyleUtil {
    public static void createFadeAnimation(Node node, int fadeDuration, float to) {
        FadeTransition ft = new FadeTransition(Duration.millis(fadeDuration), node);

        ft.setFromValue(node.getOpacity());
        ft.setToValue(to);
        ft.play();
    }

    public static void createFadeAnimation(Stage stage, int fadeDuration, float to) {
        final Animation animation = new Transition() {
            {
                setCycleDuration(Duration.millis(fadeDuration));
            }

            final float ansOpasity = (float) stage.getOpacity();
            protected void interpolate(double f) {
                float op = (float) (to * f + ansOpasity * (1 - f));

                stage.setOpacity(op);
            }
        };

        animation.play();
    }
}
