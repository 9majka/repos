package com.game.tetris.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.utils.Array;
import com.game.tetris.Model;
import com.game.tetris.object.GameObject;
import com.game.tetris.object.GameObject.ObjectType;
import com.game.tetris.screen.GameConfig;

public class FieldView {
    private final GameConfig mConfig;
    private Texture FieldBg;

    private Texture mGameOverTexture = null;
    private GameObject mActiveObj = null;
    private Model mModel = null;
    private final int mOffset = 1;
    public float scale = 0f;
    private Array<Texture> mTextures;
    private PanelView mPanel;

    public FieldView(final GameConfig config) {
        mConfig = config;
        mPanel = new PanelView(config);
        
        FieldBg = new Texture(Gdx.files.internal("field_bg.jpg"));
        mGameOverTexture = new Texture(Gdx.files.internal("gameover.png"));
        int types = ObjectType.toInt(ObjectType.OT_MAXObject);
        mTextures = new Array<Texture>();
        for(int i = 0; i < types; i++) {
            mTextures.add(new Texture(Gdx.files.internal(GameObject.getTextureFileRath(i))));
        }
    }
    
    public void setActiveObject(GameObject obj) {
        mActiveObj = obj;
    }
    
    public void setModel(Model model) {
        mModel = model;
    }
    
    public void setNextObject(int type) {
        mPanel.setNextObject(type);
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

        mPanel.draw(batch);
    }

    public void drawGameOver(SpriteBatch batch) {
        float paddingLeft = mConfig.getHorizontalPadding();
        float paddingBottom = mConfig.getVerticalPadding();
        batch.draw(FieldBg, paddingLeft, paddingBottom, mConfig.getFieldUnitWidth(), mConfig.getFieldUnitHeight());
        drawModel(batch);
        mPanel.draw(batch);
        
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
        short field[][] = mModel.getField();
        float width = mConfig.mCellUnitWidth;
        float height = mConfig.mCellUnitHeight;

        int i = 0;
        short value = 0;
        for(int j = 1; j <= mConfig.getFieldBlockHeight(); j++) {
            for(i = 0; i < mConfig.getFieldBlockWidth(); i++) {
             // TODO Start /optimization find the problem 
                value = field[i][j];
             // TODO END /optimization find the problem 
                if(value != 0) {
                    batch.draw(mTextures.get(0), i * width + paddingLeft, ((j - mOffset) * height) + paddingBottom, width, height);
                }
            }
        }
    }
    
    public void dispose() {
        mPanel.dispose();
        mGameOverTexture.dispose();
        for(Texture text: mTextures) {
            text.dispose();
        }
    }
}