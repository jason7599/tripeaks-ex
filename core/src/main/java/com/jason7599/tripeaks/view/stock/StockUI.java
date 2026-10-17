
package com.jason7599.tripeaks.view.stock;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;
import com.badlogic.gdx.utils.Align;
import com.jason7599.tripeaks.assets.Assets;
import com.jason7599.tripeaks.view.card.CardUI;
import com.jason7599.tripeaks.view.card.CardView;
import com.jason7599.tripeaks.view.ui.Palette;

final class StockUI {

    private static final float CARD_OFFSET = 2;

    private static final float COUNT_LABEL_WIDTH = 54;
    private static final float COUNT_LABEL_HEIGHT = 38;
    private static final int COUNT_LABEL_RADIUS = 12;
    private static final int COUNT_BORDER = 2;

    private StockUI() {}

    static void build(StockView v) {
        int size = v.cards.size();

        // Add cards right-to-left, index 0 on top.
        for (int i = size - 1; i >= 0; i--) {
            CardView card = v.cards.get(i);
            card.setPosition(i * CARD_OFFSET, 0);
            v.addActor(card);
        }

        float width = CardUI.WIDTH
            + Math.max(0, size - 1) * CARD_OFFSET;

        v.setSize(width, CardUI.HEIGHT);

        // Create count label.
        v.countLabel = new Label(
            "",
            new Label.LabelStyle(Assets.font(), Palette.CREAM)
        );
        v.countLabel.setAlignment(Align.center);
        v.countLabel.setTouchable(Touchable.disabled);

        // Create its background container.
        v.countBadge = createCountBadge(v.countLabel);
        v.addActor(v.countBadge);
    }

    private static Container<Label> createCountBadge(Label label) {
        Container<Label> count = new Container<>(label);

        count.setSize(COUNT_LABEL_WIDTH, COUNT_LABEL_HEIGHT);
        count.setPosition(
            (CardUI.WIDTH - COUNT_LABEL_WIDTH) / 2f,
            (CardUI.HEIGHT - COUNT_LABEL_HEIGHT) / 2f
        );

        count.pad(6);

        count.setBackground(new BaseDrawable() {
            @Override
            public void draw(
                Batch batch,
                float x, float y,
                float width, float height
            ) {
                Assets.shapes().roundedRect(
                    batch, x, y, width, height,
                    COUNT_LABEL_RADIUS, Palette.TEAL
                );

                Assets.shapes().roundedBorder(
                    batch, x, y, width, height,
                    COUNT_LABEL_RADIUS, COUNT_BORDER, Palette.GOLD
                );
            }
        });

        count.setTouchable(Touchable.disabled);
        return count;
    }
}
