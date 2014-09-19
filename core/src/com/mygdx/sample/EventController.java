package com.mygdx.sample;

import com.badlogic.gdx.input.GestureDetector.GestureAdapter;

public class EventController extends GestureAdapter{
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
    public boolean tap(float x, float y, int count, int button) {
        mListener.onRotate();
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
    
    private float normalizeX(float absoluteX) {
        float ratio = mConfig.getRatioWidth();
        return absoluteX/ratio;
    }
    
    private float normalizeY(float absoluteY) {
        float ratio = mConfig.getRatioHeight();
        return absoluteY/ratio;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        if(Math.abs(deltaX) > Math.abs(deltaY)) {
            proccessShift(normalizeX(x));
        }
        else {
            proccessAcceleration(normalizeY(y));
        }
        
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        mPanX = false;
        mPanY = false;
        return false;
    }
}