
package com.jason7599.tripeaks.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

public final class Theme {

    private Theme() {}

    private static final class Palette {
        static final String SURFACE = "#151F32";
        static final String SURFACE_EDGE = "#293C5C";

        static final String BUTTON = "#263550";
        static final String BUTTON_EDGE = "#52698D";

        static final String HOVER = "#3B5281";
        static final String HOVER_EDGE = "#8AA5FF";

        static final String PRESSED = "#19263F";
        static final String PRESSED_EDGE = "#435A80";

        static final String DISABLED = "#1B2537";
        static final String DISABLED_EDGE = "#303D53";

        static final String TEXT = "#EAF0FF";
        static final String TEXT_HOVER = "#FFFFFF";
        static final String TEXT_PRESSED = "#C7D6FF";
        static final String TEXT_DISABLED = "#66758F";

        static final String BADGE = "#323047";
        static final String BADGE_EDGE = "#B99B66";
        static final String BADGE_TEXT = "#F9D796";

        private Palette() {}
    }

    public static Skin create(BitmapFont font) {
        Skin skin = new Skin();

        createControlBar(skin);
        createButtons(skin, font);
        createStockCount(skin, font);
        createBadges(skin, font);

        return skin;
    }

    private static void createControlBar(Skin skin) {
        Drawable bar = panel(
            skin, "bar",
            Palette.SURFACE,
            Palette.SURFACE_EDGE,
            14
        );

        skin.add("bar", bar, Drawable.class);
    }

    private static void createButtons(Skin skin, BitmapFont font) {
        TextButton.TextButtonStyle style =
            new TextButton.TextButtonStyle();

        style.font = font;
        style.fontColor = color(Palette.TEXT);
        style.overFontColor = color(Palette.TEXT_HOVER);
        style.downFontColor = color(Palette.TEXT_PRESSED);
        style.disabledFontColor = color(Palette.TEXT_DISABLED);

        style.up = panel(skin, "btn-up",
            Palette.BUTTON, Palette.BUTTON_EDGE, 12);

        style.over = panel(skin, "btn-over",
            Palette.HOVER, Palette.HOVER_EDGE, 12);

        style.down = panel(skin, "btn-down",
            Palette.PRESSED, Palette.PRESSED_EDGE, 12);

        style.disabled = panel(skin, "btn-disabled",
            Palette.DISABLED, Palette.DISABLED_EDGE, 12);

        style.pressedOffsetY = -1;

        skin.add("game", style);
    }

    private static void createStockCount(Skin skin, BitmapFont font) {
        Drawable background = panel(
            skin, "stock-count-bg",
            "#172136", "#50648A", 10
        );

        // Padding around the label text
        background.setLeftWidth(10);
        background.setRightWidth(10);
        background.setTopHeight(5);
        background.setBottomHeight(5);

        Label.LabelStyle style = new Label.LabelStyle(
            font,
            Color.valueOf("#FFFFFF")
        );

        style.background = background;

        skin.add("stock-count", style);
    }

    private static void createBadges(Skin skin, BitmapFont font) {
        Drawable background = panel(
            skin, "badge",
            Palette.BADGE,
            Palette.BADGE_EDGE,
            7
        );

        background.setMinWidth(26);
        background.setMinHeight(26);

        Label.LabelStyle style = new Label.LabelStyle(
            font, color(Palette.BADGE_TEXT)
        );

        style.background = background;
        skin.add("badge", style);
    }


    private static Color color(String hex) {
        return Color.valueOf(hex);
    }

    private static Drawable panel(
        Skin skin,
        String name,
        String fill,
        String border,
        int radius
    ) {
        // One pixel of stretchable center in each direction.
        int size = radius * 2 + 3;
        int split = radius + 1;

        Pixmap pixmap = roundedRect(
            size, size, radius,
            color(fill), color(border)
        );

        Texture texture;
        try {
            texture = new Texture(pixmap);
        } finally {
            pixmap.dispose();
        }

        texture.setFilter(
            Texture.TextureFilter.Linear,
            Texture.TextureFilter.Linear
        );

        skin.add(name, texture, Texture.class);

        NinePatch patch = new NinePatch(
            new TextureRegion(texture),
            split, split, split, split
        );

        return new NinePatchDrawable(patch);
    }

    private static Pixmap roundedRect(
        int width,
        int height,
        int radius,
        Color fill,
        Color border
    ) {
        Pixmap pixmap = new Pixmap(
            width, height, Pixmap.Format.RGBA8888
        );

        pixmap.setBlending(Pixmap.Blending.None);

        Color pixel = new Color();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                float px = x + 0.5f;
                float py = y + 0.5f;

                float outer = coverage(
                    px, py, 0, width, height, radius
                );

                float inner = coverage(
                    px, py, 1, width, height, radius - 1
                );

                pixel.set(border).lerp(fill, inner);
                pixel.a *= outer;

                pixmap.drawPixel(
                    x, y, Color.rgba8888(pixel)
                );
            }
        }

        return pixmap;
    }

    private static float coverage(
        float px,
        float py,
        float inset,
        int width,
        int height,
        float radius
    ) {
        radius = Math.max(0, radius);

        float cx = MathUtils.clamp(
            px, inset + radius, width - inset - radius
        );

        float cy = MathUtils.clamp(
            py, inset + radius, height - inset - radius
        );

        float dx = px - cx;
        float dy = py - cy;

        float distance = (float) Math.sqrt(dx * dx + dy * dy);

        return MathUtils.clamp(
            radius - distance + 0.5f, 0f, 1f
        );
    }
}
