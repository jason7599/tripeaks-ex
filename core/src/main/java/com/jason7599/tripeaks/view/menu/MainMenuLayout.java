package com.jason7599.tripeaks.view.menu;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import com.jason7599.tripeaks.assets.Assets;
import com.jason7599.tripeaks.view.ui.Buttons;
import com.jason7599.tripeaks.view.ui.Palette;

final class MainMenuLayout {
    private static final float WIDTH = 1280;
    private static final float HEIGHT = 720;

    private static final float TITLE_SPACING = 50;
    private static final float BUTTON_WIDTH = 260;
    private static final float BUTTON_HEIGHT = 60;

    private static final float TITLE_SCALE = 2.5f;

    private MainMenuLayout() {}

    static Stage build(MainMenuScreen scr, Runnable onPlay) {
        Stage stage = new Stage(new FitViewport(WIDTH, HEIGHT));

        Table layout = new Table();
        layout.setFillParent(true);
        stage.addActor(layout);

        // Title
        Label.LabelStyle titleStyle = new Label.LabelStyle(
            Assets.font(),
            Palette.CREAM
        );

        Label title = new Label("TRIPEAKS-EX", titleStyle);
        title.setAlignment(Align.center);
        title.setFontScale(TITLE_SCALE);

        // Play button
        TextButton play = Buttons.create("PLAY", true, onPlay);

        // Layout
        layout.add(title)
            .padBottom(TITLE_SPACING)
            .row();

        layout.add(play)
            .size(BUTTON_WIDTH, BUTTON_HEIGHT);

        return stage;
    }

    static void clear() {
        ScreenUtils.clear(Palette.TEAL_DARK);
    }
}
