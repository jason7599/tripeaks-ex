package com.jason7599.tripeaks.view.stock;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.jason7599.tripeaks.view.card.CardView;

import java.util.ArrayList;
import java.util.List;

public class StockView extends Group {
    final List<CardView> cards; // index 0 = leftmost & top
    Label countLabel;
    Container<Label> countBadge;

    public StockView(int stockSize, Runnable onClicked) {
        cards = new ArrayList<>(stockSize);
        for (int i = 0; i < stockSize; i++) {
            cards.add(new CardView());
        }

        StockLayout.build(this);

        addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                onClicked.run();
            }
        });
    }

    public void setCount(int count) {
        for (int i = 0; i < cards.size(); i++) {
            cards.get(i).setVisible(i < count);
        }
        countLabel.setText(count);
        countBadge.setVisible(count > 0);
    }
}
