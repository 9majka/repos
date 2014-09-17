package com.mygdx.sample;

import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;

public class DropGestureListener implements GestureListener{
    private final ControllerListener m_Listener;;
    private boolean m_Pan = false;
    private int X = 0;
    public DropGestureListener(final ControllerListener listener) {
        m_Listener = listener;
        
    }
    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        m_Listener.onTap();
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        int step = (int)x/GameConfig.CELLSIZE;
        if(m_Pan == false) {
            m_Pan = true;
            X = step;
        }
        if(X != step) {
            m_Listener.onShiftToDelta(step - X);
            X = step;
        }
        
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        m_Pan = false;
        System.out.println("Stop");
        return false;
    }

    @Override
    public boolean zoom (float originalDistance, float currentDistance){
       return false;
    }

    @Override
    public boolean pinch (Vector2 initialFirstPointer, Vector2 initialSecondPointer, Vector2 firstPointer, Vector2 secondPointer){
       return false;
    }
}