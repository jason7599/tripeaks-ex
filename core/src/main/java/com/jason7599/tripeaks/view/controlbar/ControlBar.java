package com.jason7599.tripeaks.view.controlbar;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.jason7599.tripeaks.controller.GameCallbacks;
import com.jason7599.tripeaks.view.ui.Buttons;

public class ControlBar extends Table {

    private final TextButton restartButton;
    private final TextButton undoButton;

    public ControlBar(GameCallbacks callbacks) {
        restartButton = Buttons.create("Restart", false, callbacks::onRestart);
        undoButton = Buttons.create("Undo", true, callbacks::onUndo);

        ControlBarUI.build(this, restartButton, undoButton);
    }

    public void setUndoable(boolean undoable) {
        undoButton.setDisabled(!undoable);
    }

    public void setRestartable(boolean restartable) {
        restartButton.setDisabled(!restartable);
    }
}
