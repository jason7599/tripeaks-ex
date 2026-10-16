package com.jason7599.tripeaks.game;

import com.jason7599.tripeaks.model.*;

import java.util.*;

public class Game {
    public static final int INITIAL_STOCK_SIZE = 52 - TableauStructure.TOTAL_COUNT - 1;

    private final int seed;

    private final Tableau tableau;
    private final Deque<Card> stock;    // face-down pile of undealt cards
    private final Deque<Card> waste;    // face-up pile of used cards + player card.
    private final Deque<Move> history;   // for undo

    public Game(int seed) {
        this.seed = seed;

        List<Card> deck = new Deck(seed).cards();
        final int t = TableauStructure.TOTAL_COUNT;

        tableau = new Tableau(deck.subList(0, t));
        stock = new ArrayDeque<>(deck.subList(t, deck.size()));

        waste = new ArrayDeque<>();
        waste.push(stock.pop());

        history = new ArrayDeque<>();
    }

    public int getSeed() { return seed; }
    public Tableau getTableau() { return tableau; }
    public int getStockCount() { return stock.size(); }
    public Card getPlayerCard() { return waste.peek(); }
    public boolean canUndo() { return !history.isEmpty(); }
    public boolean isCleared() { return tableau.remaining() == 0; }

    public static boolean isAdjacent(Card card0, Card card1) {
        int diff = Math.abs(card0.rankValue() - card1.rankValue());
        return diff == 1 || diff == 12;
    }

    public List<Slot> getPlayableSlots() {
        return tableau.exposedSlots()
            .stream()
            .filter(s -> isAdjacent(getPlayerCard(), tableau.card(s)))
            .toList();
    }

    public Optional<Move.Play> tryPlaySlot(Slot s) {
        if (waste.isEmpty()) return Optional.empty();

        Card card = tableau.card(s);
        if (card == null || !tableau.isExposed(s) || !isAdjacent(waste.element(), card))
            return Optional.empty();

        List<Slot> exposed = tableau.remove(s);
        waste.push(card);

        Move.Play play = new Move.Play(s, card, exposed);
        history.push(play);
        return Optional.of(play);
    }

    public Optional<Move.Draw> tryDraw() {
        if (stock.isEmpty()) return Optional.empty();

        Card card = stock.pop();
        waste.push(card);

        Move.Draw draw = new Move.Draw(card);
        history.push(draw);
        return Optional.of(draw);
    }

    public Move undo() {
        if (!canUndo()) throw new IllegalStateException("History stack is empty");

        Move move = history.pop();
        switch (move) {
            case Move.Play p -> {
                tableau.put(p.slot(), p.card());
                waste.pop();
            }
            case Move.Draw ignored -> stock.push(waste.pop());
        }

        return move;
    }
}
