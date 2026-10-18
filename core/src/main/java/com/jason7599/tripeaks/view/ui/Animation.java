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

    public void play(Runnable onFinish, Action... actions) {
        cancel();

        this.onFinish = onFinish;
        isPlaying = true;

        actor.addAction(Actions.sequence(
            Actions.sequence(actions),
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
