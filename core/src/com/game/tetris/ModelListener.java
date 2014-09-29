package com.game.tetris;

public interface ModelListener {
    public void onGameOver();
    public void onLevelChange(int level);
    public void onLinesDeleted(int count);
}