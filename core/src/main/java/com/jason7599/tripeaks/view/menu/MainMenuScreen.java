package com.jason7599.tripeaks.view.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class MainMenuScreen extends ScreenAdapter {

    private final Stage stage;

    public MainMenuScreen(Runnable onPlay) {
        stage = MainMenuUI.build(this, onPlay);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        MainMenuUI.clear();
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
