package com.game.tetris;


public interface ControllerListener {
    public void onShiftToDeltaX(int delta);
    public void onAccelarate();
    public void onRotate(); 
}