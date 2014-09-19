package com.mygdx.sample;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.sample.object.GameObject;

public class FieldViewer {
    private final GameConfig mConfig;
    private Texture FieldBg;
    
    private GameObject mActiveObj = null;
    private Model mModel = null;
    private int mUnitWidth;
    private int mUnitHeight;
    
    public FieldViewer(final GameConfig config, int unitWidth, int unitHeight) {
        mConfig = config;
        mUnitWidth = unitWidth;
        mUnitHeight = unitHeight;
        FieldBg = new Texture(Gdx.files.internal("field_bg.jpg"));
    }
    
    public void setActiveObject(GameObject obj) {
        mActiveObj = obj;
    }
    
    public void setModel(Model model) {
        mModel = model;
    }
    
    public void draw(SpriteBatch batch) {
        batch.draw(FieldBg, 0, 0, mUnitWidth, mUnitHeight);
        if(mActiveObj != null) {
            drawObject(batch);
        }
        drawModel(batch);
    }
    
    private void drawObject(SpriteBatch batch) {
        float x = 0;
        float y = 0;
        int unitSize = mConfig.getBlockUnitSize();
        Array<GridPoint2> points = mActiveObj.getAbsolutePoints();
        int shiftX = mActiveObj.getShiftX();
        int shiftY = mActiveObj.getShiftY();
        float posY = mActiveObj.getPosY();
        float delta = posY - shiftY * unitSize;
        
        Texture texture = mActiveObj.getTexture();
        
        for(GridPoint2 point: points) {
            x = (point.x + shiftX) * unitSize;
            y = (point.y + shiftY - 1) * unitSize + delta;
            batch.draw(texture, x, y, unitSize, unitSize);
        }
    }
    
    private void drawModel(SpriteBatch batch) {
        boolean field[][] = mModel.getField();
        int unitSize = mConfig.getBlockUnitSize();
        Texture texture = mModel.getTexture();
        for(int j = 1; j < mConfig.getFieldBlockHeight(); j++) {
            for(int i = 0; i < mConfig.getFieldBlockWidth(); i++) {
                if(field[i][j] == true) {
                    batch.draw(texture, i*unitSize, (j-1)*unitSize, unitSize, unitSize);
                }
            }
        }
    }
}