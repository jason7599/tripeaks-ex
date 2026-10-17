package com.jason7599.tripeaks.view.tableau;

import com.jason7599.tripeaks.model.Slot;
import com.jason7599.tripeaks.model.TableauStructure;
import com.jason7599.tripeaks.view.card.CardUI;
import com.jason7599.tripeaks.view.card.CardView;

final class TableauUI {

    private static final float COLUMN_STEP = CardUI.WIDTH / 2f;
    private static final float ROW_STEP = CardUI.HEIGHT / 2f;

    private static final int COLUMN_SPANS = 18;
    private static final int ROW_SPANS = 3;

    static final float WIDTH = COLUMN_SPANS * COLUMN_STEP + CardUI.WIDTH;
    static final float HEIGHT = ROW_SPANS * ROW_STEP + CardUI.HEIGHT;

    private TableauUI() {}

    // ── Initialization ─────────────────────────

    static void build(TableauView v) {
        v.setSize(WIDTH, HEIGHT);
    }

    // ── Card layout ────────────────────────────

    static void addCard(
        TableauView view,
        CardView card,
        Slot slot
    ) {
        float x = TableauStructure.gridX(slot) * COLUMN_STEP;

        float y = HEIGHT
            - CardUI.HEIGHT
            - slot.row() * ROW_STEP;

        card.setPosition(x, y);
        view.addActor(card);
    }
}
