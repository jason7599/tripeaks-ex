package com.jason7599.tripeaks.assets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.jason7599.tripeaks.view.ui.Shapes;

/** App-wide resources */
public final class Assets {

    private static CardSprites cards;
    private static BitmapFont font;
    private static Shapes shapes;

    private Assets() {}

    public static void load() {
        if (font != null) return;

        cards = new CardSprites();
        shapes = new Shapes();

        font = new BitmapFont();
        font.getRegion().getTexture().setFilter(
            Texture.TextureFilter.Linear,
            Texture.TextureFilter.Linear
        );
        font.getData().setScale(2);
    }

    public static CardSprites cards() {
        return cards;
    }
    public static BitmapFont font() { return font; }
    public static Shapes shapes() { return shapes; }

    public static void dispose() {
        if (shapes != null) shapes.dispose();
        if (font != null) font.dispose();
        if (cards != null) cards.dispose();

        shapes = null;
        font = null;
        cards = null;
    }
}
