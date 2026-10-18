package com.jason7599.tripeaks.view.ui;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class Animation {

    private final Actor actor;

    private Runnable onFinish;
    private boolean isPlaying;

    public Animation(Actor actor) {
        this.actor = actor;
    }

    public void play(Action action, Runnable onFinish) {
        cancel();

        this.onFinish = onFinish;
        isPlaying = true;

        actor.addAction(Actions.sequence(
            action,
            Actions.run(this::finish)
        ));
    }

    public void cancel() {
        finish();
    }

    private void finish() {
        if (!isPlaying) return;

        isPlaying = false;

        actor.clearActions();

        Runnable cb = onFinish;
        onFinish = null;
        if (cb != null) {
            cb.run();
        }
    }
}
