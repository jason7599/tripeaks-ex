package com.jason7599.tripeaks.view.card;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.jason7599.tripeaks.model.Card;

public class CardView extends Group {

    /* package private */
    Card card;
    boolean faceUp;
    boolean highlighted;
    Label badge;

    public CardView() {
        CardLayout.build(this);
    }

    public void setCard(Card card) { this.card = card; }

    public Card getCard() { return card; }

    public boolean isFaceUp() { return faceUp; }

    public void setFaceUp(boolean faceUp) { this.faceUp = faceUp; }

    public void setHighlighted(boolean highlighted) { this.highlighted = highlighted; }

    /** Show a badge with this text, or hide it with null. */
    public void setBadge(String text) { CardLayout.setBadge(this, text); }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        applyTransform(batch, computeTransform());
        CardLayout.draw(this, batch, parentAlpha);
        drawChildren(batch, parentAlpha);
        resetTransform(batch);
    }
}
