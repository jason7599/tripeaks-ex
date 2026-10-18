package com.jason7599.tripeaks.view.card;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.jason7599.tripeaks.assets.Assets;

public final class CardUI {

    private CardUI() {}

    public static final float WIDTH = 100;
    public static final float HEIGHT = 100 * 1.4f;

    private static final float RADIUS = 12;
    private static final float BORDER = 2;
    private static final float HIGHLIGHT_BORDER = 4;
    private static final Color BORDER_COLOR = Color.valueOf("#708996");
    private static final Color HIGHLIGHT_COLOR = Color.valueOf("#FFD700");

    private static final float BADGE_MARGIN = -10;

    static void build(CardView v) {
        v.setSize(WIDTH, HEIGHT);

        Label badge = v.badge = new Label(
            "",
            new Label.LabelStyle(
                Assets.font(),
                Color.GREEN
            )
        );
        badge.setAlignment(Align.center);
        badge.setTouchable(Touchable.disabled);
        badge.setVisible(false);
        v.addActor(badge);
    }

    static void setBadge(CardView v, String text) {
        Label badge = v.badge;
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

    static void draw(CardView v, Batch batch, float parentAlpha) {
        float x = v.getX();
        float y = v.getY();
        float w = v.getWidth();
        float h = v.getHeight();

        Color tint = v.getColor();
        float alpha = tint.a * parentAlpha;

        float thickness = v.highlighted
            ? HIGHLIGHT_BORDER
            : BORDER;

        Color borderColor = v.highlighted
            ? HIGHLIGHT_COLOR
            : BORDER_COLOR;

        // Draw border behind the card.
        batch.setColor(tint.r, tint.g, tint.b, alpha);

        Assets.shapes().roundedBorder(
            batch,
            x - thickness,
            y - thickness,
            w + thickness * 2,
            h + thickness * 2,
            (int)(RADIUS + thickness),
            (int)thickness,
            borderColor
        );

        // Draw card face or back.
        TextureRegion image = v.faceUp && v.card != null
            ? Assets.cards().face(v.card)
            : Assets.cards().back();

        batch.draw(image, x, y, w, h);
        // Restore batch color before Scene2D draws children.
        batch.setColor(Color.WHITE);
    }
}
