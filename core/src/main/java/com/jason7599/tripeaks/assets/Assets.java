package com.jason7599.tripeaks.assets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.jason7599.tripeaks.ui.Theme;

/** App-wide resources */
public final class Assets {

    private Assets() {}

    private static boolean init;
    private static CardSprites cards;
    private static BitmapFont font;
    private static Skin skin;

    public static void load() {
        if (init) return;

        cards = new CardSprites();

        font = new BitmapFont(); // libGDX builtin default font
        font.getRegion().getTexture().setFilter(
            Texture.TextureFilter.Linear,
            Texture.TextureFilter.Linear
        );
        font.getData().setScale(1.5f);

        skin = Theme.create(font());

        init = true;
    }

    public static CardSprites cards() { return cards; }
    public static BitmapFont font() { return font; }
    public static Skin skin() { return skin; }

    public static synchronized void dispose() {
        if (!init) return;

        if (cards != null) cards.dispose();
        if (font != null) font.dispose();
        if (skin != null) skin.dispose();
        cards = null;
        font = null;
        skin = null;

        init = false;
    }
}
