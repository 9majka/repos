package com.mygdx.sample;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.sample.Model.BlockItem;
import com.mygdx.sample.object.GameObject;
import com.mygdx.sample.object.GameObject.ObjectType;

public class FieldViewer {
    private final GameConfig mConfig;
    private Texture FieldBg;
    private Texture mPaddingH;
    private Texture mPaddingV;
    private Texture mPanel;
    private Texture mLayer;
    private Texture mGameOverTexture = null;
    private GameObject mActiveObj = null;
    private Model mModel = null;
    private final int mOffset = 1;
    public float scale = 0f;
    private Texture[] mTextures;
    
    public FieldViewer(final GameConfig config) {
        mConfig = config;
        FieldBg = new Texture(Gdx.files.internal("field_bg.jpg"));
        mPaddingH = new Texture(Gdx.files.internal("padding.png"));
        mPaddingV = new Texture(Gdx.files.internal("paddingVer.png"));
        mPanel = new Texture(Gdx.files.internal("panel.png"));
        mLayer = new Texture(Gdx.files.internal("layer.png"));
        mGameOverTexture = new Texture(Gdx.files.internal("gameover.png"));
        int types = ObjectType.toInt(ObjectType.OT_MAXObject);
        mTextures = new Texture[types];
        for(int i = 0; i < types; i++) {
            mTextures[i] = new Texture(Gdx.files.internal(GameObject.getTextureFileRath(i)));
        }
    }
    
    public void setActiveObject(GameObject obj) {
        mActiveObj = obj;
    }
    
    public void setModel(Model model) {
        mModel = model;
    }
    
    public void draw(SpriteBatch batch) {
        batch.draw( FieldBg, mConfig.getHorizontalPadding()
                  , mConfig.getVerticalPadding()
                  , mConfig.getFieldUnitWidth()
                  , mConfig.getFieldUnitHeight());

        drawModel(batch);
        if(mActiveObj != null) {
            drawObject(batch);
        }

        drawPaddings(batch);
    }
    
    private void drawPaddings(SpriteBatch batch) {
        float x = 0f;
        x = 2* mConfig.getHorizontalPadding() + mConfig.getFieldUnitWidth();
        batch.draw(mPanel, x, 0, mConfig.mPanelUnitWidth, mConfig.getScreenUnitHeight());
        float y = mConfig.getScreenUnitHeight() - mConfig.mPanelUnitWidth - mConfig.getVerticalPadding() - 1;
        batch.draw(mLayer, x + 1, y, mConfig.mPanelUnitWidth - 2, mConfig.mPanelUnitWidth - 2);
        
        
        batch.draw(mPaddingH, 0, 0, mConfig.getHorizontalPadding(), mConfig.getScreenUnitHeight());
        x = mConfig.getHorizontalPadding() + mConfig.getFieldUnitWidth();
        batch.draw(mPaddingH, x, 0, mConfig.getHorizontalPadding(), mConfig.getScreenUnitHeight());
        x = mConfig.getScreenUnitWidth() - mConfig.getHorizontalPadding();
        batch.draw(mPaddingH, x, 0, mConfig.getHorizontalPadding(), mConfig.getScreenUnitHeight());
        
        batch.draw(mPaddingV, 0, 0, mConfig.getScreenUnitWidth(), mConfig.getVerticalPadding());
        y = mConfig.getScreenUnitHeight() - mConfig.getVerticalPadding();
        batch.draw(mPaddingV, 0, y, mConfig.getScreenUnitWidth(), mConfig.getVerticalPadding());
        
        

        
    }
    
    public void drawGameOver(SpriteBatch batch) {
        float paddingLeft = mConfig.getHorizontalPadding();
        float paddingBottom = mConfig.getVerticalPadding();
        batch.draw(FieldBg, paddingLeft, paddingBottom, mConfig.getFieldUnitWidth(), mConfig.getFieldUnitHeight());
        drawModel(batch);
        drawPaddings(batch);
        
        if(scale > 1f) scale = 1f;
        batch.draw(mGameOverTexture, 0, 350, mConfig.getFieldUnitWidth()*scale, 100*scale);
        scale += 0.05;
    }
    
    private void drawObject(SpriteBatch batch) {
        float x = 0;
        float y = 0;
        float paddingLeft = mConfig.getHorizontalPadding();
        float paddingBottom = mConfig.getVerticalPadding();
        float width = mConfig.mCellUnitWidth;
        float height = mConfig.mCellUnitHeight;

        Array<GridPoint2> points = mActiveObj.getAbsolutePoints();
        int shiftX = mActiveObj.getShiftX();
        int shiftY = mActiveObj.getShiftY();
        float posY = mActiveObj.getPosY();
        float delta = posY - shiftY * height;
        
        Texture texture = mActiveObj.getTexture();

        for(GridPoint2 point: points) {
            x = ((point.x + shiftX) * width) + paddingLeft;
            y = ((point.y + shiftY - mOffset) * height + delta) + paddingBottom;
            batch.draw(texture, x, y, width, height);
        }
    }
    
    private void drawModel(SpriteBatch batch) {
        float paddingLeft = mConfig.getHorizontalPadding();
        float paddingBottom = mConfig.getVerticalPadding();
        BlockItem field[][] = mModel.getField();
        float width = mConfig.mCellUnitWidth;
        float height = mConfig.mCellUnitHeight;

        for(int j = 1; j <= mConfig.getFieldBlockHeight(); j++) {
            for(int i = 0; i < mConfig.getFieldBlockWidth(); i++) {
                if(field[i][j].mValue == true){
                    batch.draw(mTextures[field[i][j].mType], i * width + paddingLeft, ((j - mOffset) * height) + paddingBottom, width, height);
                }
            }
        }
    }
}