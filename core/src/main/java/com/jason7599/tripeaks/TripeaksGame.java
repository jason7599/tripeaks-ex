package com.jason7599.tripeaks;

import com.jason7599.tripeaks.assets.Assets;
import com.jason7599.tripeaks.controller.GameController;

public class TripeaksGame extends com.badlogic.gdx.Game {

    @Override
    public void create() {
        Assets.load();

        GameController gc = new GameController();
        setScreen(gc.getScreen());
    }

    @Override
    public void dispose() {
        if (getScreen() != null) getScreen().dispose();
        Assets.dispose();
    }
}
