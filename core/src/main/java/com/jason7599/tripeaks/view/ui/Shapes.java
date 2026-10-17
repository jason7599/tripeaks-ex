
package com.jason7599.tripeaks.view.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.utils.Disposable;

import java.util.HashMap;
import java.util.Map;

public final class Shapes implements Disposable {

    private static final int SAMPLES = 4;

    private final Map<String, NinePatch> patches = new HashMap<>();
    private final Map<String, Texture> textures = new HashMap<>();

    public void rect(
        Batch batch,
        float x, float y, float w, float h,
        Color color
    ) {
        roundedRect(batch, x, y, w, h, 0, color);
    }

    public void roundedRect(
        Batch batch,
        float x, float y, float w, float h,
        int radius,
        Color color
    ) {
        draw(batch, x, y, w, h, radius, 0, color);
    }

    public void roundedBorder(
        Batch batch,
        float x, float y, float w, float h,
        int radius,
        int thickness,
        Color color
    ) {
        if (thickness <= 0) return;
        draw(batch, x, y, w, h, radius, thickness, color);
    }


    private void draw(
        Batch batch,
        float x, float y, float w, float h,
        int radius,
        int thickness,
        Color color
    ) {
        radius = Math.max(0, radius);
        thickness = Math.max(0, thickness);

        if (w <= 0 || h <= 0) return;
        if (w < radius * 2 || h < radius * 2) return;

        NinePatch patch = getPatch(radius, thickness);

        Color previous = batch.getColor();
        float r = previous.r;
        float g = previous.g;
        float b = previous.b;
        float a = previous.a;

        batch.setColor(
            r * color.r,
            g * color.g,
            b * color.b,
            a * color.a
        );

        patch.draw(batch, x, y, w, h);

        batch.setColor(r, g, b, a);
    }


    private NinePatch getPatch(int radius, int thickness) {
        String key = radius + ":" + thickness;

        NinePatch cached = patches.get(key);
        if (cached != null) return cached;

        int size = radius * 2 + 1;
        Pixmap pixmap = new Pixmap(
            size, size, Pixmap.Format.RGBA8888
        );

        float half = size / 2f;

        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                float coverage = coverage(
                    x, y, half, radius, thickness
                );

                pixmap.drawPixel(
                    x, y,
                    Color.rgba8888(1, 1, 1, coverage)
                );
            }
        }

        Texture texture = new Texture(pixmap);
        texture.setFilter(
            Texture.TextureFilter.Linear,
            Texture.TextureFilter.Linear
        );

        pixmap.dispose();

        NinePatch patch = new NinePatch(
            texture, radius, radius, radius, radius
        );

        textures.put(key, texture);
        patches.put(key, patch);

        return patch;
    }

    private float coverage(
        int px, int py,
        float half,
        int radius,
        int thickness
    ) {
        float total = 0;

        for (int sy = 0; sy < SAMPLES; sy++) {
            for (int sx = 0; sx < SAMPLES; sx++) {
                float x = px + (sx + 0.5f) / SAMPLES - half;
                float y = py + (sy + 0.5f) / SAMPLES - half;

                boolean outer = inside(x, y, half, radius);
                boolean inner = thickness > 0
                    && inside(
                    x, y,
                    half - thickness,
                    Math.max(0, radius - thickness)
                );

                if (outer && !inner) total++;
            }
        }

        return total / (SAMPLES * SAMPLES);
    }

    private boolean inside(
        float x, float y,
        float half,
        float radius
    ) {
        if (half <= 0) return false;

        float corner = Math.min(radius, half);
        float inset = half - corner;

        float dx = Math.max(Math.abs(x) - inset, 0);
        float dy = Math.max(Math.abs(y) - inset, 0);

        return dx * dx + dy * dy <= corner * corner;
    }


    @Override
    public void dispose() {
        for (Texture texture : textures.values()) {
            texture.dispose();
        }

        textures.clear();
        patches.clear();
    }
}
