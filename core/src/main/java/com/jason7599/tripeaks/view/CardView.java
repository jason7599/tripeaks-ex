package com.jason7599.tripeaks.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.jason7599.tripeaks.assets.Assets;
import com.jason7599.tripeaks.model.Card;

public class CardView extends Group {

    public static final float WIDTH = 100, HEIGHT = 140;

    private static final float BADGE_MARGIN = -10;
    private static final float BORDER = 2f;
    private static final Color BORDER_COLOR = Color.valueOf("#708996");

    private final Label badge;
    private final Drawable border;

    private Card card;
    private boolean faceUp;

    public CardView() {
        setSize(WIDTH, HEIGHT);
        setTransform(false);

        border = Assets.skin().getDrawable("rounded-12");

        badge = new Label("", Assets.skin(), "badge");
        badge.setAlignment(Align.center);
        badge.setTouchable(Touchable.disabled);
        badge.setVisible(false);

        addActor(badge);
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public void setFaceUp(boolean faceUp) {
        this.faceUp = faceUp;
    }

    /** Show a badge with this text, or hide it with null. */
    public void setBadge(String text) {
        if (text == null) {
            badge.setVisible(false);
            return;
        }

        badge.setText(text);
        badge.pack();
        badge.setPosition(
            (WIDTH - badge.getWidth()) / 2,
            BADGE_MARGIN
        );
        badge.setVisible(true);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Color c = getColor();
        float alpha = c.a * parentAlpha;

        TextureRegion img = (faceUp && card != null)
            ? Assets.cards().face(card)
            : Assets.cards().back();

        // Draw rounded border behind the card.
        batch.setColor(
            BORDER_COLOR.r * c.r,
            BORDER_COLOR.g * c.g,
            BORDER_COLOR.b * c.b,
            alpha
        );

        border.draw(
            batch,
            getX() - BORDER,
            getY() - BORDER,
            getWidth() + BORDER * 2,
            getHeight() + BORDER * 2
        );

        // Draw card.
        batch.setColor(c.r, c.g, c.b, alpha);

        batch.draw(
            img,
            getX(),
            getY(),
            getWidth(),
            getHeight()
        );

        // Draw badge and other children.
        batch.setColor(Color.WHITE);
        super.draw(batch, parentAlpha);
    }
}
