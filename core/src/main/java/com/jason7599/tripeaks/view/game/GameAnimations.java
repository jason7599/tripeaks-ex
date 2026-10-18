package com.jason7599.tripeaks.view.game;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.jason7599.tripeaks.assets.Assets;
import com.jason7599.tripeaks.game.Move;
import com.jason7599.tripeaks.model.Slot;
import com.jason7599.tripeaks.view.card.CardUI;
import com.jason7599.tripeaks.view.card.CardView;
import com.jason7599.tripeaks.view.ui.Animation;
import com.jason7599.tripeaks.view.ui.Palette;

final class GameAnimations {

    private final GameView view;

    private Animation playedCardAnim;

    GameAnimations(GameView view) {
        this.view = view;
    }

    private static final float CLEARED_MESSAGE_DURATION = 3f;

    void showClearedMessage(Runnable onFinish) {
        Label label = new Label(
            "CLEARED!",
            new Label.LabelStyle(Assets.font(), Palette.GOLD)
        );

        label.pack();
        label.setOrigin(label.getWidth() / 2f, label.getHeight() / 2f);
        label.setPosition(
            (view.stage.getWidth() - label.getWidth()) / 2f,
            (view.stage.getHeight() - label.getHeight()) / 2f
        );
        label.setScale(0.5f);

        new Animation(view.stage, label).play(
            onFinish,
            Actions.parallel(
                Actions.scaleTo(2f, 2f, 3f, Interpolation.sineOut),
                Actions.sequence(
                    Actions.delay(CLEARED_MESSAGE_DURATION / 2),
                    Actions.fadeOut(CLEARED_MESSAGE_DURATION / 2)
                )
            )
        );
    }

    private static final float CARD_MOVE_DURATION = 0.3f;

    void onCardPlayed(Move.Play play) {
        if (playedCardAnim != null) {
            playedCardAnim.cancel();
            playedCardAnim = null;
        }

        CardView src = view.tableauView.getCardView(play.slot());

        Vector2 from = src.localToStageCoordinates(new Vector2());
        Vector2 to = view.playerCard.localToStageCoordinates(new Vector2());

        Image img = new Image(Assets.cards().face(play.card()));
        img.setSize(CardUI.WIDTH, CardUI.HEIGHT);
        img.setPosition(from.x, from.y);
        img.setTouchable(Touchable.disabled);

        view.tableauView.showSlot(play.slot(), false);
        for (Slot s : play.exposed()) {
            view.tableauView.setFaceUp(s, true);
        }

        playedCardAnim = new Animation(view.stage, img);
        playedCardAnim.play(
            () -> view.setPlayerCard(play.card()),
            Actions.moveTo(
                to.x, to.y,
                CARD_MOVE_DURATION,
                Interpolation.swingIn
            )
        );
    }
}
