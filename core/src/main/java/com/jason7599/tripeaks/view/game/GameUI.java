package com.jason7599.tripeaks.view.game;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.jason7599.tripeaks.controller.GameCallbacks;
import com.jason7599.tripeaks.view.ui.Buttons;
import com.jason7599.tripeaks.view.ui.Palette;

final class GameUI {

    private static final float WIDTH = 1280;
    private static final float HEIGHT = 720;

    private static final float TABLEAU_TOP_MARGIN = 40;

    private static final float PILE_Y = 120;
    private static final float PILE_SPACING = 66;

    private static final float CONTROL_BOTTOM = 20;

    private static final float EXIT_WIDTH = 100;
    private static final float EXIT_HEIGHT = 42;
    private static final float EXIT_MARGIN = 24;

    private GameUI() {}

    static Stage build(GameView v, GameCallbacks cb) {
        Stage stage = new Stage(new FitViewport(WIDTH, HEIGHT));

        // Tableau: top center
        v.tableauView.setPosition(
            (WIDTH - v.tableauView.getWidth()) / 2f,
            HEIGHT - TABLEAU_TOP_MARGIN - v.tableauView.getHeight()
        );
        stage.addActor(v.tableauView);

        // Player card: bottom center
        float playerX = (WIDTH - v.playerCard.getWidth()) / 2f;

        v.playerCard.setPosition(playerX, PILE_Y);
        stage.addActor(v.playerCard);

        // Stock: right of player card
        v.stockView.setPosition(
            playerX + v.playerCard.getWidth() + PILE_SPACING,
            PILE_Y
        );
        stage.addActor(v.stockView);

        // Controls: bottom center
        v.controlBar.setPosition(
            (WIDTH - v.controlBar.getWidth()) / 2f,
            CONTROL_BOTTOM
        );
        stage.addActor(v.controlBar);

        TextButton exit = Buttons.create(
            "Exit",
            false,
            cb::onExit
        );
        // Exit: top right
        exit.setSize(EXIT_WIDTH, EXIT_HEIGHT);
        exit.setPosition(
            WIDTH - EXIT_WIDTH - EXIT_MARGIN,
            HEIGHT - EXIT_HEIGHT - EXIT_MARGIN
        );
        stage.addActor(exit);

        return stage;
    }

    static void clear() {
        ScreenUtils.clear(Palette.TEAL_DARK);
    }
}
