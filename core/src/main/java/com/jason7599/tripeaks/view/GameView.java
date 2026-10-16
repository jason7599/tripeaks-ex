package com.jason7599.tripeaks.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.jason7599.tripeaks.controller.GameCallbacks;
import com.jason7599.tripeaks.game.Game;
import com.jason7599.tripeaks.model.Card;
import com.jason7599.tripeaks.model.Slot;

import java.util.List;

public class GameView extends ScreenAdapter {

    private static final float WIDTH = 1280, HEIGHT = 720;
    private static final float PILE_Y = 120, PILE_SPACING = 66;
    private static final Color BG = Color.valueOf("#141D2B");

    private final Stage stage = new Stage(new FitViewport(WIDTH, HEIGHT));
    private final TableauView tableauView;
    private final CardView playerCard;
    private final StockView stockView;
    private final ControlBar controlBar;

    public GameView(GameCallbacks callbacks) {
        tableauView = new TableauView(callbacks::onSlotClicked);
        tableauView.setPosition(
            (WIDTH - TableauView.WIDTH) / 2,
            HEIGHT - 40 - TableauView.HEIGHT
        );

        playerCard = new CardView();
        playerCard.setFaceUp(true);

        final float playerX = WIDTH / 2 - CardView.WIDTH / 2;
        playerCard.setPosition(playerX, PILE_Y);

        stockView = new StockView(Game.INITIAL_STOCK_SIZE, callbacks::onStockClicked);
        stockView.setPosition(playerX + CardView.WIDTH + PILE_SPACING, PILE_Y);

        controlBar = new ControlBar(callbacks);
        controlBar.setPosition(
            (WIDTH - controlBar.getWidth()) / 2,
            20
        );

        stage.addActor(tableauView);
        stage.addActor(playerCard);
        stage.addActor(stockView);
        stage.addActor(controlBar);

        stage.getRoot().addCaptureListener(
            new GameInputListener(callbacks)
        );
    }

    public void setGame(Game g) {
        tableauView.setTableau(g.getTableau());

        Card pc = g.getPlayerCard();
        playerCard.setCard(pc);
        playerCard.setVisible(pc != null);

        stockView.setCount(g.getStockCount());

        controlBar.setUndoable(g.canUndo());
    }

    public void setPlayerCard(Card c) { playerCard.setCard(c); }

    public void showSlot(Slot s, boolean b) { tableauView.showSlot(s, b); }

    public void setFaceUp(Slot s, boolean b) { tableauView.setFaceUp(s, b); }

    public void setStockCount(int count) { stockView.setCount(count); }

    public void setUndoable(boolean b) { controlBar.setUndoable(b); }

    public void setKeyHints(List<Slot> keyHints) { tableauView.setKeyHints(keyHints);}

    @Override public void show() { Gdx.input.setInputProcessor(stage); }  // stage receives clicks

    @Override
    public void render(float delta) {
        ScreenUtils.clear(BG);
        stage.act(delta);
        stage.draw();
    }
    @Override public void resize(int w, int h) { stage.getViewport().update(w, h, true); }

    @Override public void dispose() { stage.dispose(); }
}
