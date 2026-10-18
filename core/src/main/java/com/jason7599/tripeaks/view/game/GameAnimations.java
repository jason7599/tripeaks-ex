package com.jason7599.tripeaks.view.game;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.jason7599.tripeaks.assets.Assets;
import com.jason7599.tripeaks.game.Move;
import com.jason7599.tripeaks.view.card.CardLayout;
import com.jason7599.tripeaks.view.card.CardView;
import com.jason7599.tripeaks.view.ui.Animation;
import com.jason7599.tripeaks.view.ui.Palette;

import java.util.List;

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

        view.stage.addActor(label);

        label.pack();
        label.setOrigin(label.getWidth() / 2f, label.getHeight() / 2f);
        label.setPosition(
            (view.stage.getWidth() - label.getWidth()) / 2f,
            (view.stage.getHeight() - label.getHeight()) / 2f
        );
        label.setScale(0.5f);

        new Animation(label).play(
            () -> {
                label.remove();
                onFinish.run();
            },
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
        img.setSize(CardLayout.WIDTH, CardLayout.HEIGHT);
        img.setPosition(from.x, from.y);
        img.setTouchable(Touchable.disabled);
        view.stage.addActor(img);

        view.tableauView.showSlot(play.slot(), false);
        List<Animation> exposeAnims = play.exposed()
            .stream()
            .map((s) -> flipCard(view.tableauView.getCardView(s), true))
            .toList();

        playedCardAnim = new Animation(img);
        playedCardAnim.play(
            () -> {
                img.remove();
                view.setPlayerCard(play.card());

                for (Animation a : exposeAnims) {
                    a.cancel();
                }
            },
            Actions.moveTo(
                to.x, to.y,
                CARD_MOVE_DURATION,
                Interpolation.swingIn
            )
        );
    }

    private static final float FLIP_DURATION = 0.2f;

    Animation flipCard(CardView cv, boolean faceUp) {
        Animation anim = new Animation(cv);
        anim.play(
            () -> {
                cv.setFaceUp(faceUp);
                cv.setScale(1f);
            },
            Actions.scaleTo(0f, 1f, FLIP_DURATION / 2, Interpolation.sineIn),
            Actions.run(() -> cv.setFaceUp(faceUp)),
            Actions.scaleTo(1f, 1f, FLIP_DURATION / 2, Interpolation.sineOut)
        );
        return anim;
    }
}
