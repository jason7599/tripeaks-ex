package com.jason7599.tripeaks.model;

import com.jason7599.tripeaks.util.MyRandom;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    public static final List<Card> ALL;
    static {
        List<Card> l = new ArrayList<>(52);
        for (Card.Suit s : Card.Suit.values()) {
            for (Card.Rank r : Card.Rank.values()) {
                l.add(new Card(s, r));
            }
        }
        ALL = Collections.unmodifiableList(l);
    }

    private final List<Card> cards;

    public Deck(int seed) {
        cards = new ArrayList<>(ALL);
        MyRandom.shuffle(cards, new MyRandom(seed));
    }

    public List<Card> cards() {
        return Collections.unmodifiableList(cards);
    }
}
