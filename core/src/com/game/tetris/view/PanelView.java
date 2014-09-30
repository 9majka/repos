package com.game.tetris.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.game.tetris.object.GameObject;
import com.game.tetris.object.GameObject.ObjectType;
import com.game.tetris.screen.GameConfig;

public class PanelView {
    final GameConfig mCnfg;

    public class PreviewImage extends Actor {
        public Texture mTexture;
        
        @Override
        public void draw (Batch batch, float parentAlpha) {
            batch.draw(mTexture, getX(), getY(), getWidth(), getHeight());
        }
    }
    private PreviewImage mPreviewImage;
    private Label mLevelLabel;
    private Label mScoreLabel;
    private Array<Texture> mPreviewTextures;
    private int mNextObject = 0;
    private Stage mStage;

    public PanelView(final GameConfig config) {
        mCnfg = config;
        int types = ObjectType.toInt(ObjectType.OT_MAXObject);
        mPreviewTextures = new Array<Texture>();
        
        for(int i = 0; i < types; i++) {
            mPreviewTextures.add(new Texture(Gdx.files.internal(GameObject.getPreviewObjectFileRath(i))));
        }
        
        init();
    }

    private void init() {
        mStage = new Stage(new FitViewport(mCnfg.getScreenUnitWidth(), mCnfg.getScreenUnitHeight(), new OrthographicCamera()));
        float x = 0f;
        float y = 0f;
        float w = 0f;
        float h = 0f;
        
        Image panel = new Image(new Texture(Gdx.files.internal("panel.png")));
        x = 2* mCnfg.getHorizontalPadding() + mCnfg.getFieldUnitWidth();
        y = 0f;
        w = mCnfg.mPanelUnitWidth;
        h = mCnfg.getScreenUnitHeight();
        panel.setPosition(x, y);
        panel.setSize(w, h);
        
        mStage.addActor(panel);
        
        Image previewLayer = new Image(new Texture(Gdx.files.internal("layer.png")));
        float padding = 0f;
        x = x + padding;
        y = mCnfg.getScreenUnitHeight() - mCnfg.mPanelUnitWidth - mCnfg.getVerticalPadding() - padding;
        w = w - 2*padding;
        previewLayer.setPosition(x, y);
        previewLayer.setSize(w, w);
        mStage.addActor(previewLayer);
        
        mPreviewImage = new PreviewImage();
        mPreviewImage.setPosition(x, y);
        mPreviewImage.setSize(w, w);
        mStage.addActor(mPreviewImage);
        
        Image levelLayer = new Image(new Texture(Gdx.files.internal("layer.png")));
        levelLayer.setPosition(x, y - 200);
        levelLayer.setSize(w, w/2);
        mStage.addActor(levelLayer);
        
        Skin skin = new Skin(Gdx.files.internal("data/uiskin.json"));
        mLevelLabel = new Label("1", skin);
        mLevelLabel.setAlignment(Align.right);
        mLevelLabel.setPosition(x, y - 200);
        mLevelLabel.setSize(w - 5, w/2);
        mLevelLabel.setFontScale(2);
        mStage.addActor(mLevelLabel);
        
        Image level = new Image(new Texture(Gdx.files.internal("level.png")));
        level.setPosition(x, y - 200 + w/2);
        level.setSize(w, w/2);
        mStage.addActor(level);
        
        float scoreY = y - 200 - w/2 - 200;
        
        Image scoreLayer = new Image(new Texture(Gdx.files.internal("layer.png")));
        scoreLayer.setPosition(x, scoreY);
        scoreLayer.setSize(w, w/2);
        mStage.addActor(scoreLayer);
        
        mScoreLabel = new Label("0", skin);
        mScoreLabel.setAlignment(Align.right);
        mScoreLabel.setPosition(x, scoreY);
        mScoreLabel.setSize(w - 5, w/2);
        mScoreLabel.setFontScale(2);
        mStage.addActor(mScoreLabel);
        
        Image score = new Image(new Texture(Gdx.files.internal("score.png")));
        score.setPosition(x, scoreY + w/2);
        score.setSize(w, w/2);
        mStage.addActor(score);
    }
    
    public void update() {
        mStage.draw();
    }
    
    public void newLevel(int level) {
        String str = "" + level;
        mLevelLabel.setText(str);
    }
    
    public void newScore(int score) {
        String str = "" + score;
        mScoreLabel.setText(str);
    }
    
    public void setNextObject(int type) {
        mNextObject = type;
        mPreviewImage.mTexture = mPreviewTextures.get(mNextObject);
    }

    public void dispose() {
        for(Texture text: mPreviewTextures) {
            text.dispose();
        }
    }
}