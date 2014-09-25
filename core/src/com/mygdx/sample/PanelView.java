package com.mygdx.sample;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.sample.object.GameObject;
import com.mygdx.sample.object.GameObject.ObjectType;

public class PanelView {
    final GameConfig mCnfg;
    private Texture mPaddingH;
    private Texture mPaddingV;
    private Texture mPanel;
    private Texture mLayer;
    
    private Texture[] mPreviewTextures;
    private int mNextObject = 0;
    
    private BitmapFont mFont;

    public PanelView(final GameConfig config) {
        mCnfg = config;
        
        mPaddingH = new Texture(Gdx.files.internal("padding.png"));
        mPaddingV = new Texture(Gdx.files.internal("paddingVer.png"));
        mPanel = new Texture(Gdx.files.internal("panel.png"));
        mLayer = new Texture(Gdx.files.internal("layer.png"));
        
        mFont = new BitmapFont();
        mFont.setScale(2, 2);
        
        int types = ObjectType.toInt(ObjectType.OT_MAXObject);
        mPreviewTextures = new Texture[types];
        
        for(int i = 0; i < types; i++) {
            mPreviewTextures[i] = new Texture(Gdx.files.internal(GameObject.getPreviewObjectFileRath(i)));
        }
    }
    
    public void setNextObject(int type) {
        mNextObject = type;
    }
    

    
    private void drawPanel(SpriteBatch batch) {
        float x = 2* mCnfg.getHorizontalPadding() + mCnfg.getFieldUnitWidth();
        float y = 0f;
        float w = mCnfg.mPanelUnitWidth;
        float h = mCnfg.getScreenUnitHeight();
        
        batch.draw(mPanel, x, y, w, h);
        
        float padding = 0f;
        x = x + padding;
        y = mCnfg.getScreenUnitHeight() - mCnfg.mPanelUnitWidth - mCnfg.getVerticalPadding() - padding;
        w = w - 2*padding;
        h = w;
        batch.draw(mLayer, x, y, w, h);
        batch.draw(mPreviewTextures[mNextObject], x, y, w, h);
        
        y = y - 200;
        h = h/2;
        batch.draw(mLayer, x, y, w, h);
        mFont.draw(batch, "Level", x, y);
        
        y = y - h - 200;
        batch.draw(mLayer, x, y, w, h);
        mFont.draw(batch, "Score", x, y);
    }
    
    private void drawPaddings(SpriteBatch batch) {
        float x = 0f;
        float y = 0f;

        batch.draw(mPaddingH, x, 0, mCnfg.getHorizontalPadding(), mCnfg.getScreenUnitHeight());
        
        x = mCnfg.getHorizontalPadding() + mCnfg.getFieldUnitWidth();
        batch.draw(mPaddingH, x, 0, mCnfg.getHorizontalPadding(), mCnfg.getScreenUnitHeight());
        
        x = mCnfg.getScreenUnitWidth() - mCnfg.getHorizontalPadding();
        batch.draw(mPaddingH, x, 0, mCnfg.getHorizontalPadding(), mCnfg.getScreenUnitHeight());
        batch.draw(mPaddingV, 0, 0, mCnfg.getScreenUnitWidth(), mCnfg.getVerticalPadding());
        
        y = mCnfg.getScreenUnitHeight() - mCnfg.getVerticalPadding();
        batch.draw(mPaddingV, 0, y, mCnfg.getScreenUnitWidth(), mCnfg.getVerticalPadding());
    }
    
    public void draw(SpriteBatch batch) {
        drawPanel(batch);
        drawPaddings(batch);
    }
}