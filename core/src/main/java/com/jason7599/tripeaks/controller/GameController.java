package com.jason7599.tripeaks.controller;

import com.badlogic.gdx.math.MathUtils;
import com.jason7599.tripeaks.game.Game;
import com.jason7599.tripeaks.game.Move;
import com.jason7599.tripeaks.model.Slot;
import com.jason7599.tripeaks.model.TableauStructure;
import com.jason7599.tripeaks.view.game.GameView;

import java.util.Comparator;
import java.util.List;

public class GameController implements GameCallbacks {

    private Game game;
    private final GameView view;
    private final Runnable onExitToMenu;

    private List<Slot> playableSlots = List.of();

    public GameController(Runnable onExitToMenu) {
        this.onExitToMenu = onExitToMenu;
        view = new GameView(this);
        newGame(MathUtils.random.nextInt());
    }

    public GameView getScreen() { return view; }

    private void newGame(int seed) {
        game = new Game(seed);
        view.setGame(game);
        afterChange();
    }

    private void afterChange() {
        if (game.isCleared()) {
            onCleared();
            return;
        }

        view.setUndoable(game.canUndo());

        playableSlots = game.getPlayableSlots()
            .stream()
            .sorted(Comparator.comparingInt(TableauStructure::gridX))
            .toList();
        view.setKeyHints(playableSlots);
    }

    private void onCleared() {
        view.setUndoable(false);
        view.setRestartable(false);
        view.showClearedAnimation(() -> newGame(MathUtils.random.nextInt()));
    }

    @Override
    public void onSlotClicked(Slot s) {
        if (game.isCleared()) return;

        game.tryPlaySlot(s).ifPresent((p) -> {
            view.setPlayerCard(p.card());
            view.showSlot(p.slot(), false);
            for (Slot a : p.exposed()) {
                view.setFaceUp(a, true);
            }
            afterChange();
        });
    }

    @Override
    public void onStockClicked() {
        if (game.isCleared()) return;

        game.tryDraw().ifPresent((d) -> {
            view.setPlayerCard(d.card());
            view.setStockCount(game.getStockCount());
            afterChange();
        });
    }

    @Override
    public void onNumKeyPressed(int number) {
        // actually there's no way in normal circumstances keyHints can grow more than 8
        number = number == 0 ? 9 : number - 1;
        if (number < playableSlots.size() && number >= 0) { // ...
            onSlotClicked(playableSlots.get(number));
        }
    }

    @Override
    public void onUndo() {
        if (game.isCleared()) return;

        if (!game.canUndo()) return;

        Move m = game.undo();
        switch (m) {
            case Move.Play p -> {
                view.showSlot(p.slot(), true);
                view.setPlayerCard(game.getPlayerCard());
                for (Slot s : p.exposed()) {
                    view.setFaceUp(s, false);
                }
            }
            case Move.Draw ignored -> {
                view.setPlayerCard(game.getPlayerCard());
                view.setStockCount(game.getStockCount());
            }
        }

        afterChange();
    }

    @Override
    public void onRestart() {
        if (game.isCleared()) return;

        newGame(game.getSeed()); // with the same seed
    }

    @Override
    public void onExit() { onExitToMenu.run(); }
}
