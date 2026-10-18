package com.jason7599.tripeaks.view.game;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.jason7599.tripeaks.assets.Assets;
import com.jason7599.tripeaks.game.Move;
import com.jason7599.tripeaks.view.card.CardLayout;
import com.jason7599.tripeaks.view.card.CardView;
import com.jason7599.tripeaks.view.ui.Animation;
import com.jason7599.tripeaks.view.ui.Palette;

import java.util.List;

final class GameAnimations {

    private final GameView view;

    // Animation for moving the card from the tableau to the player card position
    // It also parents animations for flipping newly exposed cards
    private Animation playAnim;

    // Animation for moving a card from the stock to the player card position
    private Animation drawAnim;

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
            Actions.parallel(
                Actions.scaleTo(2f, 2f, 3f, Interpolation.sineOut),
                Actions.sequence(
                    Actions.delay(CLEARED_MESSAGE_DURATION / 2),
                    Actions.fadeOut(CLEARED_MESSAGE_DURATION / 2)
                )
            ),
            () -> {
                label.remove();
                onFinish.run();
            }
        );
    }

    private static final float CARD_FLIP_DURATION = 0.2f;
    private static final float CARD_MOVE_DURATION = 0.3f;
    private static final float CARD_DRAW_DURATION = 0.1f;

    void cancelCardAnims() {
        if (playAnim != null) {
            playAnim.cancel();
            playAnim = null;
        }
        if (drawAnim != null) {
            drawAnim.cancel();
            drawAnim = null;
        }
    }

    void onCardPlayed(Move.Play play) {
        cancelCardAnims();

        CardView cv = view.tableauView.getCardView(play.slot());
        Vector2 from = cv.localToStageCoordinates(new Vector2());
        Vector2 to = view.playerCard.localToStageCoordinates(new Vector2());

        // Create an image copy of the card
        Image img = new Image(Assets.cards().face(cv.getCard()));
        img.setSize(CardLayout.WIDTH, CardLayout.HEIGHT);
        img.setPosition(from.x, from.y);
        img.setTouchable(Touchable.disabled);
        view.stage.addActor(img);

        // Hide the original card
        view.tableauView.showSlot(play.slot(), false);

        List<Animation> exposeAnims = play.exposed()
            .stream()
            .map((s) -> {
                CardView flip = view.tableauView.getCardView(s);
                Animation anim = new Animation(flip);
                anim.play(
                    Actions.sequence(
                        Actions.scaleTo(0f, 1f, CARD_FLIP_DURATION / 2, Interpolation.sineIn),
                        Actions.run(() -> flip.setFaceUp(true)),
                        Actions.scaleTo(1f, 1f, CARD_FLIP_DURATION / 2, Interpolation.sineOut)
                    ),
                    () -> {
                        flip.setFaceUp(true);
                        flip.setScale(1f); // so it doesnt get stuck
                    }
                );
                return anim;
            })
            .toList();

        playAnim = new Animation(img);
        playAnim.play(
            Actions.moveTo(
                to.x, to.y,
                CARD_MOVE_DURATION,
                Interpolation.swingIn
            ),
            () -> {
                img.remove();
                view.setPlayerCard(play.card());
                for (Animation a : exposeAnims) {
                    a.cancel();
                }
            }
        );
    }

    void onCardDrawn(Move.Draw draw, int stockCount) {
        cancelCardAnims();

        view.stockView.setCount(stockCount);

        Vector2 from = view.stockView.localToStageCoordinates(new Vector2());
        Vector2 to = view.playerCard.localToStageCoordinates(new Vector2());

        // start face down
        Image img = new Image(Assets.cards().back());
        img.setSize(CardLayout.WIDTH, CardLayout.HEIGHT);
        img.setPosition(from.x, from.y);
        view.stage.addActor(img);

        drawAnim = new Animation(img);
        drawAnim.play(
            Actions.parallel(
                // flip to reveal face halfway
                Actions.sequence(
                    Actions.scaleTo(0f, 1f, CARD_FLIP_DURATION / 2, Interpolation.sineIn),
                    Actions.run(() ->
                        img.setDrawable(
                            new TextureRegionDrawable(Assets.cards().face(draw.card()))
                        )
                    ),
                    Actions.scaleTo(1f, 1f, CARD_FLIP_DURATION / 2, Interpolation.sineOut)
                ),
                Actions.moveTo(
                    to.x, to.y,
                    CARD_DRAW_DURATION,
                    Interpolation.sineIn
                )
            ),
            () -> {
                img.remove();
                view.setPlayerCard(draw.card());
            }
        );
    }
}
