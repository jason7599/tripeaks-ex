package com.jason7599.tripeaks.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Fixed definition for how the tableau should be laid out.
 * Including which slot covers what slots, and vice versa.
 */
public final class TableauStructure {

    public static final int ROW_COUNT = 4;
    public static final int[] COL_COUNTS = {
        3,
        6,
        9,
        10
    };

    public static final int TOTAL_COUNT;
    public static final List<Slot> ALL_SLOTS;

    private static final Map<Slot, List<Slot>> COVERED_BY;
    private static final Map<Slot, List<Slot>> COVERS;

    static {
        List<Slot> allSlots = new ArrayList<>();
        Map<Slot, List<Slot>> coveredBy = new HashMap<>();
        Map<Slot, List<Slot>> covers = new HashMap<>();
        int totalCount = 0;

        for (int r = 0; r < ROW_COUNT; r++) {
            for (int c = 0; c < COL_COUNTS[r]; c++) {
                Slot s = slot(r, c);
                allSlots.add(s);
                List<Slot> covering = computeCoverage(s); // slots that cover s
                covers.put(s, covering);
                for (Slot a : covering) {
                    coveredBy.computeIfAbsent(a, k -> new ArrayList<>()).add(s);
                }
            }
            totalCount += COL_COUNTS[r];
        }

        ALL_SLOTS = List.copyOf(allSlots);

        COVERED_BY = Map.copyOf(coveredBy);

        // extra step to make the list of covers unmodifiable too
        Map<Slot, List<Slot>> frozen = new HashMap<>();
        covers.forEach((k, v) -> frozen.put(k, List.copyOf(v)));
        COVERS = Map.copyOf(frozen);

        TOTAL_COUNT = totalCount;
    }

    private TableauStructure() {}

    public static List<Slot> coveredBy(Slot s) {
        validateSlot(s);
        return COVERED_BY.getOrDefault(s, List.of());
    }

    public static List<Slot> covers(Slot s) {
        validateSlot(s);
        return COVERS.getOrDefault(s, List.of());
    }

    /** Horizontal position of a slot, in half-card-width units (0..18).
     * This looks like UI stuff, which it also is, but is tied to the shape of
     * the logical Tableau structure, so it lives here.
     */
    public static int gridX(Slot s) {
        validateSlot(s);
        int c = s.col();
        return switch (s.row()) {
            case 0 -> 6 * c + 3;                          // peak tops: 3, 9, 15
            case 1 -> 6 * (c / 2) + 2 + 2 * (c % 2);      // 2, 4,  8, 10,  14, 16
            case 2 -> 2 * c + 1;                          // 1, 3, 5, ..., 17
            case 3 -> 2 * c;                              // 0, 2, 4, ..., 18
            default -> throw new IllegalArgumentException("Invalid row: " + s.row());
        };
    }

    public static void validateSlot(Slot s) {
        if (s.row() < 0 || s.row() >= ROW_COUNT || s.col() < 0 || s.col() >= COL_COUNTS[s.row()]) {
            throw new IllegalArgumentException("Invalid slot position: (" + s.row() + ", " + s.col() + ")");
        }
    }

    /** Slots that cover s */
    private static List<Slot> computeCoverage(Slot s) {
        int r = s.row();
        int c = s.col();
        return switch (r) {
            case 0 -> List.of(slot(1, c * 2), slot(1, c * 2 + 1));
            case 1 -> List.of(slot(2, c * 3 / 2), slot(2, c * 3 / 2 + 1));
            case 2 -> List.of(slot(3, c), slot(3, c + 1));
            default -> List.of();
        };
    }

    private static Slot slot(int row, int col) { return new Slot(row, col); }
}
