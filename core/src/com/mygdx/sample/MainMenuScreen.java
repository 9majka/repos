package com.mygdx.sample;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

public class MainMenuScreen implements Screen {

    private static final int FRAME_COL = 8;
    private static final int FRAME_ROWS = 8; 
    final Drop game;
    private GameConfig m_Config;

    OrthographicCamera camera;
    
    Animation           walkAnimation;
    Texture             walkSheet;
    Texture             mLogo;
    TextureRegion[]         walkFrames;
    TextureRegion           currentFrame;

    float stateTime;   

    public MainMenuScreen(final Drop gam) {
        game = gam;
        m_Config = new GameConfig();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 480, 800);
        
        mLogo = new Texture(Gdx.files.internal("logo.png"));
        
        walkSheet = new Texture(Gdx.files.internal("drop.png"));
        TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth()/FRAME_COL, walkSheet.getHeight()/FRAME_ROWS);
        walkFrames = new TextureRegion[FRAME_COL * FRAME_ROWS];
        
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COL; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }
        walkAnimation = new Animation(0.05f, walkFrames);
        stateTime = 0f;
    }
    
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        stateTime += delta;
        currentFrame = walkAnimation.getKeyFrame(stateTime, true);
        
        game.font.setScale(2, 2);
        game.font.setColor(MathUtils.random(), MathUtils.random(), MathUtils.random(), 1f);
        game.batch.begin();
        game.batch.draw(mLogo, 40, 400, 400, 100);
        
        game.font.draw(game.batch, "Click to Play", 150, 100);
        //game.batch.draw(currentFrame, 200, 0, 300, 200);
        game.batch.end();

        if (Gdx.input.isTouched()) {
            game.setScreen(new Gametetr(game, m_Config));
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {
        m_Config.setScreeSize(width, height);
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }
}