package com.jason7599.tripeaks.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;
import com.jason7599.tripeaks.model.Card;
import com.jason7599.tripeaks.model.Deck;

import java.util.*;

public final class CardSprites implements Disposable {

    private static final String DIR = "card-sprites/";

    private final Map<Card, TextureRegion> faces = new HashMap<>(52);
    private final TextureRegion back;
    private final List<Texture> textures = new ArrayList<>(); // remembered so we can dispose them later

    public CardSprites() {
        for (Card c : Deck.ALL) faces.put(c, load(fileName(c)));
        back = load("card_back");
    }

    public TextureRegion face(Card card) { return faces.get(card); }
    public TextureRegion back() { return back; }

    private static String fileName(Card card) {
        String s = 2 <= card.rankValue() && card.rankValue() <= 10
            ? String.valueOf(card.rankValue())
            : card.rank().name().toLowerCase(Locale.ROOT);
        return s + "_of_" + card.suit().name().toLowerCase(Locale.ROOT);
    }

    private TextureRegion load(String name) {
        FileHandle f = Gdx.files.internal(DIR + name + ".png");
        if (!f.exists()) throw new IllegalStateException("Missing sprite: " + f.path());
        Texture t = new Texture(f);
        t.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear); // ≈ setSmooth(true)
        textures.add(t);
        return new TextureRegion(t);
    }

    @Override
    public void dispose() {
        for (Texture t : textures) t.dispose();
    }
}
