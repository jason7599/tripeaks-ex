package com.jason7599.tripeaks.game;

import com.jason7599.tripeaks.model.Card;
import com.jason7599.tripeaks.model.Slot;

import java.util.List;

public sealed interface Move {

    /** Card `card` at slot `slot` was played, leaving `exposed` slots newly exposed. */
    record Play(Slot slot, Card card, List<Slot> exposed) implements Move {}

    /** Card `card` was taken from the stockpile */
    record Draw(Card card) implements Move {}
}
