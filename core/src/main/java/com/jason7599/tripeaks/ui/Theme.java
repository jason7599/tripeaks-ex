package com.jason7599.tripeaks.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

public final class Theme {

    private Theme() {}

    public static Skin create() {
        Skin skin = new Skin();

        try {
            BitmapFont font = new BitmapFont();

            font.getRegion().getTexture().setFilter(
                Texture.TextureFilter.Linear,
                Texture.TextureFilter.Linear
            );

            font.getData().setScale(1.5f);

            // Skin owns the font.
            skin.add("ui-font", font, BitmapFont.class);

            // Skin owns the textures and drawables.
            addNinePatch(
                skin, "rounded-12", "ui/rounded-12.png", 16
            );
            addNinePatch(
                skin, "rounded-16", "ui/rounded-16.png", 20
            );

            // Load colors, tinted drawables and styles.
            skin.load(Gdx.files.internal("ui/tripeaks.json"));

            return skin;

        } catch (RuntimeException e) {
            // Clean up any resources created so far.
            skin.dispose();
            throw e;
        }
    }

    private static void addNinePatch(
        Skin skin,
        String name,
        String file,
        int split
    ) {
        Texture texture = new Texture(
            Gdx.files.internal(file)
        );

        texture.setFilter(
            Texture.TextureFilter.Linear,
            Texture.TextureFilter.Linear
        );

        skin.add(
            name + "-texture",
            texture,
            Texture.class
        );

        NinePatch patch = new NinePatch(
            texture, split, split, split, split
        );

        skin.add(
            name,
            new NinePatchDrawable(patch),
            Drawable.class
        );
    }
}
