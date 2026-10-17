package com.jason7599.tripeaks.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.jason7599.tripeaks.assets.Assets;

public class MainMenuScreen extends ScreenAdapter {

    private static final float WIDTH = 1280, HEIGHT = 720;

    private static final Color
        BG = Color.valueOf("#141D2B"),
        MUTED = Color.valueOf("#91A3C2");

    private final Stage stage = new Stage(new FitViewport(WIDTH, HEIGHT));

    public MainMenuScreen(Runnable onPlay) {
        Table layout = new Table();
        layout.setFillParent(true);
        stage.addActor(layout);

        Label.LabelStyle titleStyle = new Label.LabelStyle(
            Assets.font(), Color.valueOf("#EAF0FF")
        );

        Label title = new Label("TRIPEAKS-EX", titleStyle);
        title.setAlignment(Align.center);

        TextButton play = new TextButton(
            "PLAY", Assets.skin(), "game"
        );

        play.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                onPlay.run();
            }
        });

        layout.add(title).padBottom(50).row();
        layout.add(play).width(260).height(60);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(BG);
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
