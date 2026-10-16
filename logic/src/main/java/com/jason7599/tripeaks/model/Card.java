package com.jason7599.tripeaks.model;

public record Card(
    Suit suit,
    Rank rank
) {
    public enum Suit { CLUBS, DIAMONDS, HEARTS, SPADES }
    public enum Rank { ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING }

    // ace = 1, two = 2, king = 13
    public int rankValue() {
        return rank.ordinal() + 1;
    }
}
