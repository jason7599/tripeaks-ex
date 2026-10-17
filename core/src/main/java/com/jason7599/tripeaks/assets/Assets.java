package com.jason7599.tripeaks.assets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.jason7599.tripeaks.ui.Theme;

/** App-wide resources */
public final class Assets {

    private static CardSprites cards;
    private static Skin skin;

    private Assets() {}

    public static void load() {
        if (skin != null) return;

        cards = new CardSprites();
        skin = Theme.create();
    }

    public static CardSprites cards() {
        return cards;
    }

    public static Skin skin() {
        return skin;
    }

    public static BitmapFont font() {
        return skin.getFont("ui-font");
    }

    public static void dispose() {
        if (skin != null) skin.dispose();
        if (cards != null) cards.dispose();

        skin = null;
        cards = null;
    }
}
