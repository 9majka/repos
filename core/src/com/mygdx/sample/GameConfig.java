package com.mygdx.sample;

public class GameConfig {
    private int mScreenUnitWidth = 480;
    private int mScreenUnitHeight = 800;
    
    private int mBlockHeight = 25;
    private int mCellUnitSize = mScreenUnitHeight/mBlockHeight;
    private int mBlockWidth = mScreenUnitWidth/mCellUnitSize;

    private int mScreenWidth = 0;
    private int mScreenHeight = 0;
    private int mAccelerationSpeed = 15;
    
    private float mPxPerUnitWidth = mScreenWidth/mScreenUnitWidth;
    private float mPxPerUnitHeight = mScreenHeight/mScreenUnitHeight;
    
    public void setScreeSize(int width, int height) {
        mScreenWidth = width;
        mScreenHeight = height;
        
        mPxPerUnitWidth = (float)mScreenWidth/mScreenUnitWidth;
        mPxPerUnitHeight = (float)mScreenHeight/mScreenUnitHeight;
    }
    
    public int getAccelerationSpeed() {
        return mAccelerationSpeed;
    }
    
    public int getScreenUnitWidth() {
        return mScreenUnitWidth;
    }
    
    public int getScreenUnitHeight() {
        return mScreenUnitHeight;
    }
    
    public int getScreenWidth() {
        return mScreenWidth;
    }
    
    public int getScreenHeight() {
        return mScreenHeight;
    }
    
    public float getRatioWidth() {
        return mPxPerUnitWidth;
    }
    
    public float getRatioHeight() {
        return mPxPerUnitHeight;
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