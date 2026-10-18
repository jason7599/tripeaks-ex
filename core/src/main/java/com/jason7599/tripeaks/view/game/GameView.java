package com.jason7599.tripeaks.view.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.jason7599.tripeaks.controller.GameCallbacks;
import com.jason7599.tripeaks.controller.GameInputListener;
import com.jason7599.tripeaks.game.Game;
import com.jason7599.tripeaks.game.Move;
import com.jason7599.tripeaks.model.Card;
import com.jason7599.tripeaks.model.Slot;
import com.jason7599.tripeaks.view.card.CardView;
import com.jason7599.tripeaks.view.controlbar.ControlBar;
import com.jason7599.tripeaks.view.stock.StockView;
import com.jason7599.tripeaks.view.tableau.TableauView;

import java.util.List;

public class GameView extends ScreenAdapter {

    final Stage stage;

    final TableauView tableauView;
    final CardView playerCard;
    final StockView stockView;
    final ControlBar controlBar;

    private final GameAnimations anims;

    public GameView(GameCallbacks callbacks) {
        tableauView = new TableauView(callbacks::onSlotClicked);

        playerCard = new CardView();
        playerCard.setFaceUp(true);

        stockView = new StockView(
            Game.INITIAL_STOCK_SIZE,
            callbacks::onStockClicked
        );

        controlBar = new ControlBar(callbacks);

        stage = GameLayout.build(this, callbacks);

        stage.getRoot().addCaptureListener(
            new GameInputListener(callbacks)
        );

        anims = new GameAnimations(this);
    }

    public void setGame(Game g) {
        tableauView.setTableau(g.getTableau());

        playerCard.setCard(g.getPlayerCard());

        stockView.setCount(g.getStockCount());

        controlBar.setRestartable(true);
        controlBar.setUndoable(g.canUndo());
    }

    public void setPlayerCard(Card c) { playerCard.setCard(c); }

    public void showSlot(Slot s, boolean b) { tableauView.showSlot(s, b); }

    public void setFaceUp(Slot s, boolean b) { tableauView.setFaceUp(s, b); }

    public void setStockCount(int count) { stockView.setCount(count); }

    public void setRestartable(boolean b) { controlBar.setRestartable(b); }

    public void setUndoable(boolean b) { controlBar.setUndoable(b); }

    public void setKeyHints(List<Slot> keyHints) { tableauView.setKeyHints(keyHints); }

    public void showClearedMessage(Runnable onFinish) {
        anims.showClearedMessage(onFinish);
    }

    public void onCardPlayed(Move.Play play) {
        anims.onCardPlayed(play);
    }

    public void onCardDrawn(Move.Draw draw, int stockCount) {
        anims.onCardDrawn(draw, stockCount);
    }

    public void cancelCardAnims() {
        anims.cancelCardAnims();
    }

    @Override
    public void show() { Gdx.input.setInputProcessor(stage); }  // stage receives clicks

    @Override
    public void render(float delta) {
        GameLayout.clear();
        stage.act(delta);
        stage.draw();
    }

    @Override public void resize(int w, int h) { stage.getViewport().update(w, h, true); }

    @Override public void dispose() { stage.dispose(); }
}
