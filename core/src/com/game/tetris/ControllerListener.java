package com.game.tetris;


public interface ControllerListener {
    public void onShiftToDeltaX(int delta);
    public void onAccelarateStart();
    public void onAccelarateFinish();
    public void onRotate(); 
}