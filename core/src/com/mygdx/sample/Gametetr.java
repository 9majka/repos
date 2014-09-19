package com.mygdx.sample;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class Gametetr implements Screen {
    private final Drop game;
    private Controller controller;
    private OrthographicCamera camera;
    private GameConfig m_Config;

    public Gametetr(final Drop gam, GameConfig config){
        this.game = gam;
        m_Config = config;
        controller = new Controller(config);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, config.getScreenUnitWidth(), config.getScreenUnitHeight());
    }

    @Override
    public void render (float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
       
        game.batch.setProjectionMatrix(camera.combined);
        
        controller.updateGame();
        game.batch.begin();
        controller.draw(game.batch);
        game.batch.end();
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
