package com.jason7599.tripeaks.view.controlbar;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.jason7599.tripeaks.controller.GameCallbacks;
import com.jason7599.tripeaks.view.ui.Buttons;

public class ControlBar extends Table {

    private final TextButton undoButton;

    public ControlBar(GameCallbacks callbacks) {
        undoButton = Buttons.create("Undo", true, callbacks::onUndo);
        TextButton restartButton = Buttons.create("Restart", false, callbacks::onRestart);

        ControlBarUI.build(this, undoButton, restartButton);
    }

    public void setUndoable(boolean undoable) {
        undoButton.setDisabled(!undoable);
    }
}
