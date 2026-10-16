package com.jason7599.tripeaks.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static com.jason7599.tripeaks.model.Card.Rank.*;
import static com.jason7599.tripeaks.model.Card.Suit.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DeckTest {

    private static Card c(Card.Suit s, Card.Rank r) { return new Card(s, r); }

    /**
     * Golden test: seed 42 must ALWAYS deal exactly this order.
     * This is more a test of MyRandom.
     * If this fails, the shuffle or RNG changed, which would silently
     * change every saved daily game. Don't "fix" this test by updating
     * the expected list unless you truly mean to break all old seeds.
     */
    @Test
    void seed42IsStable() {
        List<Card> expected = List.of(
            c(SPADES, FIVE),    c(CLUBS, THREE),    c(HEARTS, SIX),     c(SPADES, THREE),
            c(CLUBS, SEVEN),    c(SPADES, NINE),    c(CLUBS, TWO),      c(HEARTS, FIVE),
            c(SPADES, QUEEN),   c(DIAMONDS, SEVEN), c(CLUBS, KING),     c(HEARTS, TEN),
            c(DIAMONDS, THREE), c(DIAMONDS, TWO),   c(HEARTS, SEVEN),   c(HEARTS, QUEEN),
            c(DIAMONDS, JACK),  c(CLUBS, EIGHT),    c(CLUBS, FOUR),     c(SPADES, ACE),
            c(CLUBS, FIVE),     c(DIAMONDS, FOUR),  c(DIAMONDS, FIVE),  c(SPADES, TWO),
            c(HEARTS, ACE),     c(DIAMONDS, EIGHT), c(HEARTS, TWO),     c(HEARTS, FOUR),
            c(SPADES, SEVEN),   c(CLUBS, NINE),     c(DIAMONDS, KING),  c(SPADES, JACK),
            c(DIAMONDS, SIX),   c(HEARTS, EIGHT),   c(CLUBS, JACK),     c(DIAMONDS, QUEEN),
            c(DIAMONDS, NINE),  c(HEARTS, KING),    c(CLUBS, SIX),      c(SPADES, FOUR),
            c(DIAMONDS, ACE),   c(SPADES, SIX),     c(HEARTS, THREE),   c(CLUBS, QUEEN),
            c(SPADES, TEN),     c(CLUBS, ACE),      c(DIAMONDS, TEN),   c(SPADES, KING),
            c(HEARTS, NINE),    c(CLUBS, TEN),      c(SPADES, EIGHT),   c(HEARTS, JACK)
        );
        assertEquals(expected, new Deck(42).cards());
    }
}
