package com.jason7599.tripeaks.view;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.jason7599.tripeaks.assets.Assets;

import java.util.ArrayList;
import java.util.List;

public class StockView extends Group {

    private static final float OFFSET = 2; // horizontal spacing between cards

    private final List<CardView> cards; // index 0 = leftmost & top
    private final Label countLabel;

    public StockView(int stockSize, Runnable onClicked) {
        cards = new ArrayList<>(stockSize);
        for (int i = stockSize - 1; i >= 0; i--) { // add right-to-left
            CardView cv = new CardView(); // face down by default
            cv.setPosition(i * OFFSET, 0);
            cards.addFirst(cv);
            addActor(cv);
        }

        final float width = CardView.WIDTH + (stockSize - 1) * OFFSET;
        setSize(width, CardView.HEIGHT);

        countLabel = new Label("", Assets.skin(), "stock-count");
        addActor(countLabel);

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
        countLabel.setVisible(count > 0);
        countLabel.setText(String.valueOf(count));
        countLabel.pack(); // size = text + padding
        countLabel.setPosition(
            (CardView.WIDTH - countLabel.getWidth()) / 2,
            (CardView.HEIGHT - countLabel.getHeight()) / 2
        );
    }
}
