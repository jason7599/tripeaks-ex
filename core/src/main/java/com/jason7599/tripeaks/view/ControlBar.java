package com.jason7599.tripeaks.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.jason7599.tripeaks.assets.Assets;
import com.jason7599.tripeaks.controller.GameCallbacks;

public class ControlBar extends Table {

    private static final float BUTTON_WIDTH = 140, BUTTON_HEIGHT = 44;
    private static final float SPACING = 100;

    private final TextButton undoButton;

    public ControlBar(GameCallbacks callbacks) {
        setBackground(Assets.skin().getDrawable("bar"));
        pad(16, 24, 16, 24);   // top, left, bottom, right
        defaults().size(BUTTON_WIDTH, BUTTON_HEIGHT).space(SPACING);   // applies to every cell

        undoButton = button("Undo", callbacks::onUndo);

        add(button("New Game", callbacks::onNewGame));
        add(undoButton);
        add(button("Restart", callbacks::onRestart));

        pack();   // size the bar to fit its contents
    }

    private static TextButton button(String text, Runnable action) {
        TextButton b = new TextButton(text, Assets.skin(), "game");

        // ChangeListener, not ClickListener: it respects setDisabled()
        b.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent e, Actor actor) { action.run(); }
        });

        // cursor: hand
        b.addListener(new InputListener() {
            @Override
            public void enter(InputEvent e, float x, float y, int pointer, Actor from) {
                if (pointer == -1) Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Hand);
            }
            @Override
            public void exit(InputEvent e, float x, float y, int pointer, Actor to) {
                if (pointer == -1) Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
            }
        });
        return b;
    }

    public void setUndoable(boolean b) { undoButton.setDisabled(!b); }
}
