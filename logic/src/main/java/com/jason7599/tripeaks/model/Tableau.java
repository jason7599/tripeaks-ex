package com.jason7599.tripeaks.model;

import java.util.*;

/**
 * Actual game state of the Tableau
 */
public class Tableau {

    private final Map<Slot, Card> cardSlots;

    public Tableau(List<Card> cards) {
        if (cards.size() != TableauStructure.TOTAL_COUNT) {
            throw new IllegalArgumentException("List length doesn't match the layout");
        }

        cardSlots = new HashMap<>(TableauStructure.TOTAL_COUNT);
        for (int i = 0; i < TableauStructure.TOTAL_COUNT; i++) {
            cardSlots.put(TableauStructure.ALL_SLOTS.get(i), cards.get(i));
        }
    }

    /** return null if cleared */
    public Card card(Slot s) {
        return cardSlots.get(s);
    }

    public int remaining() {
        return cardSlots.size();
    }

    /** for undoing a play */
    public void put(Slot s, Card c) {
        TableauStructure.validateSlot(s);
        if (cardSlots.put(s, Objects.requireNonNull(c)) != null) {
            throw new IllegalStateException(
                "put called on occupied slot: " + s
            );
        }
    }

    /** return newly exposed slots */
    public List<Slot> remove(Slot s) {
        if (cardSlots.remove(s) == null) {
            throw new IllegalStateException("Slot already empty: " + s);
        }

        List<Slot> exposed = new ArrayList<>();
        for (Slot b : TableauStructure.coveredBy(s)) {
            if (isExposed(b)) {
                exposed.add(b);
            }
        }

        return exposed;
    }

    public boolean isExposed(Slot s) {
        for (Slot a : TableauStructure.covers(s)) {
            if (card(a) != null) {
                return false;
            }
        }
        return true;
    }

    public List<Slot> exposedSlots() {
        return cardSlots.keySet()
            .stream()
            .filter(this::isExposed)
            .toList();
    }
}
