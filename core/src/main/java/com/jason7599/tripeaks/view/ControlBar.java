package com.jason7599.tripeaks.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.jason7599.tripeaks.assets.Assets;
import com.jason7599.tripeaks.controller.GameCallbacks;

public class ControlBar extends Table {

    private static final float BUTTON_WIDTH = 140;
    private static final float BUTTON_HEIGHT = 44;
    private static final float SPACING = 88;

    private final TextButton undoButton;

    public ControlBar(GameCallbacks callbacks) {
        Skin skin = Assets.skin();

        setBackground(skin.getDrawable("panel-bg"));

        pad(12, 16, 12, 16);

        defaults()
            .size(BUTTON_WIDTH, BUTTON_HEIGHT)
            .space(SPACING);

        add(undoButton = button("Undo", "primary", callbacks::onUndo));
        add(button("Restart", "secondary", callbacks::onRestart));

        pack();
    }

    private static TextButton button(
        String text,
        String style,
        Runnable action
    ) {
        TextButton b = new TextButton(text, Assets.skin(), style);

        b.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (!b.isDisabled()) {
                    action.run();
                }
            }
        });

        b.addListener(new InputListener() {
            @Override
            public void enter(
                InputEvent event,
                float x,
                float y,
                int pointer,
                Actor from
            ) {
                if (pointer == -1 && !b.isDisabled()) {
                    Gdx.graphics.setSystemCursor(
                        Cursor.SystemCursor.Hand
                    );
                }
            }

            @Override
            public void exit(
                InputEvent event,
                float x,
                float y,
                int pointer,
                Actor to
            ) {
                if (pointer == -1) {
                    Gdx.graphics.setSystemCursor(
                        Cursor.SystemCursor.Arrow
                    );
                }
            }
        });

        return b;
    }

    public void setUndoable(boolean undoable) {
        undoButton.setDisabled(!undoable);
    }
}
