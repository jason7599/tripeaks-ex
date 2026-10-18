package com.jason7599.tripeaks.view.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.jason7599.tripeaks.assets.Assets;

public final class Animations {

    private Animations() {}

    public static void fadingMessage(
        Stage stage,
        String message,
        Color color,
        float duration,
        Runnable onComplete
    ) {
        Label label = new Label(message, new Label.LabelStyle(
            Assets.font(), color
        ));
        label.pack();

        label.setOrigin(
            label.getWidth() / 2f,
            label.getHeight() / 2f
        );

        label.setPosition(
            (stage.getWidth() - label.getWidth()) / 2f,
            (stage.getHeight() - label.getHeight()) / 2f
        );

        label.setScale(0.5f);
        stage.addActor(label);

        label.addAction(Actions.sequence(
            Actions.parallel(
                Actions.scaleTo(2f, 2f, duration, Interpolation.sineOut),
                Actions.sequence(
                    Actions.delay(duration / 2),
                    Actions.fadeOut(duration / 2)
                )
            ),
            Actions.run(() -> {
                label.remove();
                Gdx.app.postRunnable(onComplete);
            })
        ));
    }
}
