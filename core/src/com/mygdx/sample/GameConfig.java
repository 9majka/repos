package com.mygdx.sample;

public class GameConfig {
    private int mBlockWidth = 10;
    private int mBlockHeight = 20;
    private int mCellUnitSize = 40;
    private int mScreenWidth = 0;
    private int mScreenHeight = 0;
    private int mAccelerationSpeed = 15;
    
    public void setScreeSize(int width, int height) {
        mScreenWidth = width;
        mScreenHeight = height;
    }
    
    public int getAccelerationSpeed() {
        return mAccelerationSpeed;
    }
    
    public int getScreenWidth() {
        return mScreenWidth;
    }
    
    public int getScreenHeight() {
        return mScreenHeight;
    }

    public int getFieldBlockWidth() {
        return mBlockWidth;
    }
    
    public int getFieldBlockHeight() {
        return mBlockHeight;
    }
    
    public int getBlockUnitSize() {
        return mCellUnitSize;
    }
    
    public int getFieldUnitWidth() {
        return mBlockWidth * mCellUnitSize;
    }
    
    public int getFieldUnitHeight() {
        return mBlockHeight * mCellUnitSize;
    }
    
    public int getAccelerationCondition() {
        return mBlockHeight / 6;
    }
}