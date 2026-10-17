package com.jason7599.tripeaks;

import com.badlogic.gdx.Screen;
import com.jason7599.tripeaks.assets.Assets;
import com.jason7599.tripeaks.controller.GameController;
import com.jason7599.tripeaks.view.MainMenuScreen;

public class TripeaksApp extends com.badlogic.gdx.Game {

    @Override
    public void create() {
        Assets.load();
        showMainMenu();
    }

    public void showMainMenu() {
        switchScreen(new MainMenuScreen(this::startGame));
    }

    private void switchScreen(Screen screen) {
        Screen prev = getScreen();
        setScreen(screen);
        if (prev != null) prev.dispose();
    }

    public void startGame() {
        GameController gc = new GameController(this::showMainMenu);
        setScreen(gc.getScreen());
    }

    @Override
    public void dispose() {
        if (getScreen() != null) getScreen().dispose();
        Assets.dispose();
        super.dispose();
    }
}
