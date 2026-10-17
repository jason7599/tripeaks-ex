package com.jason7599.tripeaks.view.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.jason7599.tripeaks.assets.Assets;

public final class Buttons {

    private static final int RADIUS = 10;

    private Buttons() {}

    public static TextButton create(
        String text,
        boolean primary,
        Runnable action
    ) {
        TextButton.TextButtonStyle style =
            new TextButton.TextButtonStyle();

        style.font = Assets.font();
        style.fontColor = primary ? Palette.INK : Palette.CREAM;
        style.disabledFontColor = Palette.MUTED;

        style.up = background(primary ? Palette.GOLD : Palette.TEAL);
        style.over = background(primary ? Palette.GOLD_LIGHT : Palette.TEAL_LIGHT);
        style.down = background(primary ? Palette.GOLD_DARK : Palette.TEAL_DEEP);
        style.disabled = background(Palette.DISABLED);

        TextButton button = new TextButton(text, style);

        // Click
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (!button.isDisabled()) {
                    action.run();
                }
            }
        });

        // Cursor
        button.addListener(new InputListener() {
            @Override
            public void enter(
                InputEvent event, float x, float y,
                int pointer, Actor from
            ) {
                if (pointer == -1 && !button.isDisabled()) {
                    Gdx.graphics.setSystemCursor(
                        Cursor.SystemCursor.Hand
                    );
                }
            }

            @Override
            public void exit(
                InputEvent event, float x, float y,
                int pointer, Actor to
            ) {
                if (pointer == -1) {
                    Gdx.graphics.setSystemCursor(
                        Cursor.SystemCursor.Arrow
                    );
                }
            }
        });

        return button;
    }

    private static Drawable background(Color color) {
        return new BaseDrawable() {
            @Override
            public void draw(
                Batch batch,
                float x, float y,
                float width, float height
            ) {
                Assets.shapes().roundedRect(
                    batch, x, y, width, height, RADIUS, color
                );
            }
        };
    }
}
