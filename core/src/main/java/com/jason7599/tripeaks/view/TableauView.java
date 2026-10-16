package com.jason7599.tripeaks.view;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.jason7599.tripeaks.model.Slot;
import com.jason7599.tripeaks.model.Tableau;
import com.jason7599.tripeaks.model.TableauStructure;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class TableauView extends Group {

    private static final float HALF = CardView.WIDTH / 2;
    private static final float ROW_STEP = CardView.HEIGHT * 0.5f;
    public static final float WIDTH = 18 * HALF + CardView.WIDTH;
    public static final float HEIGHT = 3 * ROW_STEP + CardView.HEIGHT;

    private final Map<Slot, CardView> cards = new HashMap<>();

    public TableauView(Consumer<Slot> onSlotClicked) {
        setSize(WIDTH, HEIGHT);

        for (Slot s : TableauStructure.ALL_SLOTS) {   // row 0 first = drawn underneath
            CardView cv = new CardView();
            cv.setPosition(TableauStructure.gridX(s) * HALF,
                HEIGHT - CardView.HEIGHT - s.row() * ROW_STEP);  // y goes UP

            cv.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent e, float x, float y) {
                    onSlotClicked.accept(s);
                }
            });

            cards.put(s, cv);
            addActor(cv);
        }
    }

    public void setTableau(Tableau t) {
        for (Slot s : TableauStructure.ALL_SLOTS) {
            CardView a = cards.get(s);
            a.setCard(t.card(s));
            a.setFaceUp(t.isExposed(s));
            a.setVisible(t.card(s) != null);
        }
    }

    public void showSlot(Slot s, boolean b) { cards.get(s).setVisible(b); }

    public void setFaceUp(Slot s, boolean b) { cards.get(s).setFaceUp(b); }

    public void setKeyHints(List<Slot> keyHints) {
        cards.values().forEach(c -> c.setBadge(null));
        for (int i = 0; i < keyHints.size(); i++) {
            cards.get(keyHints.get(i)).setBadge(String.valueOf((i + 1) % 10));
        }
    }
}
