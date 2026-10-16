package com.jason7599.tripeaks.controller;

import com.jason7599.tripeaks.model.Slot;

public interface GameCallbacks {
    void onSlotClicked(Slot slot);
    void onStockClicked();
    void onNumKeyPressed(int number);  // on digit key pressed
    void onUndo();
    void onRestart();
    void onNewGame();
}
