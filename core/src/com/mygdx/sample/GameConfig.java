package com.mygdx.sample;

public class GameConfig {
    private int mScreenWidth = 0;
    private int mScreenHeight = 0;
    
    private float mScreenUnitWidth = 480f;
    private float mScreenUnitHeight = 800f;

    private int mFieldUnitNormalizeHeight = 23;
    private int mBlockHeight = 22;
    private int mBlockWidth = 10;
    private int mFieldUnitNormalizeWidth = 14;
    public float mCellUnitHeight = mScreenUnitHeight/mFieldUnitNormalizeHeight;
    public float mCellUnitWidth = mCellUnitHeight;

    
    private float mFieldUnitWidth = mCellUnitWidth * mBlockWidth;
    private float mFieldUnitHeight = mCellUnitHeight * mBlockHeight;

    private float mPxPerUnitWidth = mScreenWidth/mScreenUnitWidth;
    private float mPxPerUnitHeight = mScreenHeight/mScreenUnitHeight;
    
    private float mPaddingHor = 0f;
    private float mPaddingVert = 0f;
    
    private int mAccelerationSpeed = 15;
    
    private void updateAspectRatio() {
        float aspect = (float)mScreenWidth/mScreenHeight;
        mScreenUnitWidth = mScreenUnitHeight * aspect;

        mCellUnitHeight = mScreenUnitHeight/mFieldUnitNormalizeHeight;
        mCellUnitWidth = mCellUnitHeight;
        
        mFieldUnitWidth = mCellUnitWidth * mBlockWidth;
        mFieldUnitHeight = mCellUnitHeight * mBlockHeight;
        
        mPaddingHor = (mScreenUnitWidth - mFieldUnitWidth - 3*mCellUnitWidth)/3;
        mPaddingVert = (mScreenUnitHeight - mFieldUnitHeight)/2;
        
        if(mPaddingHor < 0) {
            mCellUnitWidth = mScreenUnitWidth/mFieldUnitNormalizeWidth;
            mCellUnitHeight = mCellUnitWidth;
            
            mFieldUnitWidth = mCellUnitWidth * mBlockWidth;
            mFieldUnitHeight = mCellUnitHeight * mBlockHeight;
            
            mPaddingHor = (mScreenUnitWidth - mFieldUnitWidth - 3*mCellUnitWidth)/3;
            mPaddingVert = (mScreenUnitHeight - mFieldUnitHeight)/2;
        }
    }
    
    public void setScreeSize(int width, int height) {
        mScreenWidth = width;
        mScreenHeight = height;

        updateAspectRatio();
        mPxPerUnitWidth = (float)mScreenWidth/mScreenUnitWidth;
        mPxPerUnitHeight = (float)mScreenHeight/mScreenUnitHeight;
    }
    
    public float getHorizontalPadding() {
        return mPaddingHor;
    }
    
    public float getVerticalPadding() {
        return mPaddingVert;
    }
    
    public int getAccelerationSpeed() {
        return mAccelerationSpeed;
    }
    
    public float getFiledUnitWidth() {
        return mFieldUnitWidth;
    }
    
    public float getFiledUnitHeight() {
        return mFieldUnitHeight;
    }
    
    public float getScreenUnitWidth() {
        return mScreenUnitWidth;
    }
    
    public float getScreenUnitHeight() {
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
    
    public float getFieldUnitWidth() {
        return mFieldUnitWidth;
    }
    
    public float getFieldUnitHeight() {
        return mFieldUnitHeight;
    }
    
    public int getAccelerationCondition() {
        return mBlockHeight / 6;
    }
}