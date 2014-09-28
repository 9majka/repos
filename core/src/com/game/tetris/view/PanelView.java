package com.game.tetris.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.game.tetris.object.GameObject;
import com.game.tetris.object.GameObject.ObjectType;
import com.game.tetris.screen.GameConfig;

public class PanelView {
    final GameConfig mCnfg;
    private Texture mPaddingH;
    private Texture mPaddingV;
    private Texture mPanel;
    private Texture mLayer;
    private Texture mLevel;
    private Texture mScore;
    
    private Array<Texture> mPreviewTextures;
    private int mNextObject = 0;
    
    private float panelX;
    private float panelY;
    private float panelW;
    private float panelH;
    
    private float previewX;
    private float previewY;
    private float previewW;
    
    private float labelY;
    private float labelH;
    
    private float labelScoreY;

    public PanelView(final GameConfig config) {
        mCnfg = config;
        
        mPaddingH = new Texture(Gdx.files.internal("padding.png"));
        mPaddingV = new Texture(Gdx.files.internal("paddingVer.png"));
        mPanel = new Texture(Gdx.files.internal("panel.png"));
        mLayer = new Texture(Gdx.files.internal("layer.png"));
        mLevel = new Texture(Gdx.files.internal("level.png"));
        mScore = new Texture(Gdx.files.internal("score.png"));
        
        int types = ObjectType.toInt(ObjectType.OT_MAXObject);
        mPreviewTextures = new Array<Texture>();
        
        for(int i = 0; i < types; i++) {
            mPreviewTextures.add(new Texture(Gdx.files.internal(GameObject.getPreviewObjectFileRath(i))));
        }
        
        initPaddings();
    }
    
    private void initPaddings() {
        panelX = 2* mCnfg.getHorizontalPadding() + mCnfg.getFieldUnitWidth();
        panelY = 0f;
        panelW = mCnfg.mPanelUnitWidth;
        panelH = mCnfg.getScreenUnitHeight();
        
        float padding = 0f;
        previewX = panelX + padding;
        previewY = mCnfg.getScreenUnitHeight() - mCnfg.mPanelUnitWidth - mCnfg.getVerticalPadding() - padding;
        previewW = panelW - 2*padding;
        
        labelY = previewY - 200;
        labelH = previewW/2;
        
        labelScoreY = labelY - labelH - 200;
    }
    
    public void setNextObject(int type) {
        mNextObject = type;
    }

    private void drawPanel(SpriteBatch batch) {
        batch.draw(mPanel, panelX, panelY, panelW, panelH);
        batch.draw(mLayer, previewX, previewY, previewW, previewW);
        batch.draw(mPreviewTextures.get(mNextObject), previewX, previewY, previewW, previewW);
        
        batch.draw(mLayer, previewX, labelY, previewW, labelH);
        batch.draw(mLevel, previewX, labelY + labelH, previewW, labelH);
        
        batch.draw(mLayer, previewX, labelScoreY, previewW, labelH);
        batch.draw(mScore, previewX, labelScoreY + labelH, previewW, labelH);
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
    
    public void dispose() {
        mPaddingH.dispose();
        mPaddingV.dispose();
        mPanel.dispose();
        mLayer.dispose();
        mLevel.dispose();
        mScore.dispose();
        for(Texture text: mPreviewTextures) {
            text.dispose();
        }
        
    }
}