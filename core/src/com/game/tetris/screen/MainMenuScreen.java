package com.game.tetris.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

public class MainMenuScreen implements Screen {
    final Tetris game;
    private GameConfig m_Config;
    private enum LoadingState {None, Start, Loading, End};
    OrthographicCamera camera;
    Texture             mBg;
    Texture             mLogo;
    private LoadingState state = LoadingState.None;

    public MainMenuScreen(final Tetris gam) {
        game = gam;
        m_Config = new GameConfig();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, m_Config.getScreenUnitWidth(), m_Config.getScreenUnitHeight());
        
        mBg = new Texture(Gdx.files.internal("field_bg.jpg"));
        mLogo = new Texture(Gdx.files.internal("logo.png"));
    }
    
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        if(state == LoadingState.Loading) {
            game.setScreen(new GameScreen(game, m_Config));
            state = LoadingState.End;
        }

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.font.setScale(2, 2);
        game.batch.begin();
        game.batch.draw(mBg, 0, 0, m_Config.getScreenUnitWidth(), m_Config.getScreenUnitHeight());
        game.batch.draw(mLogo, 40, m_Config.getScreenUnitHeight()/2, m_Config.getScreenUnitWidth() - 80, 100);
        
        if(state == LoadingState.None) {
            game.font.draw(game.batch, "Click to Play", m_Config.getScreenUnitWidth() / 2 - 90, 100);
        } else {
            game.font.draw(game.batch, "Loading .....", m_Config.getScreenUnitWidth() / 2 - 90, 100);
        }
        game.batch.end();
        if(state == LoadingState.Start) {
            state = LoadingState.Loading;
        }
        if (Gdx.input.isTouched()) {
            state = LoadingState.Start;
        }
        if(state == LoadingState.End) {
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {
        m_Config.setScreeSize(width, height);
        camera.setToOrtho(false, m_Config.getScreenUnitWidth(), m_Config.getScreenUnitHeight());
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
        mLogo.dispose();
        mBg.dispose();
    }
}