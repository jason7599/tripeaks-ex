package com.jason7599.tripeaks.controller;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class GameInputListener extends InputListener {

    private final GameCallbacks callbacks;

    public GameInputListener(GameCallbacks callbacks) {
        this.callbacks = callbacks;
    }

    @Override
    public boolean keyDown(InputEvent event, int keycode) {
        if (Input.Keys.NUM_0 <= keycode && keycode <= Input.Keys.NUM_9) {
            callbacks.onNumKeyPressed(keycode - Input.Keys.NUM_0);
            return true;
        }

        return switch (keycode) {
            case Input.Keys.Z, Input.Keys.BACKSPACE -> {
                callbacks.onUndo();
                yield true;
            }
            case Input.Keys.SPACE -> {
                callbacks.onStockClicked();
                yield true;
            }
            default -> false;
        };

    }
}
