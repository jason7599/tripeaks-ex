package com.jason7599.tripeaks.view.game;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.jason7599.tripeaks.assets.Assets;
import com.jason7599.tripeaks.view.ui.Animation;
import com.jason7599.tripeaks.view.ui.Palette;

final class GameAnimations {

    private static final float CLEARED_MESSAGE_DURATION = 3f;

    private GameAnimations() {}

    static void showClearedAnimation(GameView v, Runnable onComplete) {
        Label label = new Label(
            "CLEARED!",
            new Label.LabelStyle(Assets.font(), Palette.GOLD)
        );

        label.pack();
        label.setOrigin(label.getWidth() / 2f, label.getHeight() / 2f);
        label.setPosition(
            (v.stage.getWidth() - label.getWidth()) / 2f,
            (v.stage.getHeight() - label.getHeight()) / 2f
        );
        label.setScale(0.5f);

        new Animation(v.stage, label).play(
            onComplete,
            Actions.parallel(
                Actions.scaleTo(2f, 2f, 3f, Interpolation.sineOut),
                Actions.sequence(
                    Actions.delay(CLEARED_MESSAGE_DURATION / 2),
                    Actions.fadeOut(CLEARED_MESSAGE_DURATION / 2)
                )
            )
        );
    }
}
