package com.mygdx.sample;

import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;

public class EventController implements GestureListener{
    private final ControllerListener mListener;
    private final GameConfig mConfig;
    private boolean mPanX = false;
    private boolean mPanY = false;
    private int mStartPanX = 0;
    private int mStartPanY = 0;
    
    public EventController(final ControllerListener listener, final GameConfig config) {
        mListener = listener;
        mConfig = config;
    }
    
    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        mListener.onRotate();
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
//        System.out.print("X = ");
//        System.out.println(velocityX);
//        System.out.print("Y = ");
//        System.out.println(velocityY);
        System.out.print("Flying");
        return false;
    }
    
    private void proccessAcceleration(float y) {
        int stepy = (int)y / mConfig.getBlockUnitSize();
        if(mPanY == false) {
            mPanY = true;
            mStartPanY = stepy;
        }
        if(stepy - mStartPanY > mConfig.getAccelerationCondition()) {
            mListener.onAccelarate();
            mStartPanY = 0;
            mPanY = false;
        }
    }
    
    private void proccessShift(float x) {
        int stepx = (int)x / mConfig.getBlockUnitSize();
        if(mPanX == false) {
            mPanX = true;
            mStartPanX = stepx;
        }
        if(mStartPanX != stepx) {
            mListener.onShiftToDeltaX(stepx - mStartPanX);
            mStartPanX = stepx;
        }
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        System.out.print("Pan");
        proccessAcceleration(y);
        proccessShift(x);
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        mPanX = false;
        mPanY = false;
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