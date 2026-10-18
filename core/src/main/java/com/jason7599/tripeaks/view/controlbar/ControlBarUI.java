package com.jason7599.tripeaks.view.controlbar;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;
import com.jason7599.tripeaks.assets.Assets;
import com.jason7599.tripeaks.view.ui.Palette;

final class ControlBarUI {

    private static final float BUTTON_WIDTH = 140;
    private static final float BUTTON_HEIGHT = 44;
    private static final float SPACING = 88;

    private static final float PAD_VERTICAL = 12;
    private static final float PAD_HORIZONTAL = 16;

    private static final int RADIUS = 16;

    private ControlBarUI() {}

    static void build(
        ControlBar bar,
        TextButton restart,
        TextButton undo
    ) {
        bar.setBackground(new BaseDrawable() {
            @Override
            public void draw(
                Batch batch,
                float x, float y,
                float width, float height
            ) {
                Assets.shapes().roundedRect(
                    batch,
                    x, y, width, height,
                    RADIUS,
                    Palette.TEAL_DARK
                );
            }
        });

        bar.pad(
            PAD_VERTICAL,
            PAD_HORIZONTAL,
            PAD_VERTICAL,
            PAD_HORIZONTAL
        );

        bar.defaults()
            .size(BUTTON_WIDTH, BUTTON_HEIGHT)
            .space(SPACING);

        bar.add(restart);
        bar.add(undo);

        bar.pack();
    }
}
