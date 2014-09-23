package com.mygdx.sample;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class Gametetr implements Screen {
    private final Drop game;
    private Controller controller;
    private OrthographicCamera camera;
    private GameConfig mConfig;

    public Gametetr(final Drop gam, GameConfig config){
        this.game = gam;
        mConfig = config;
        controller = new Controller(config);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, config.getScreenUnitWidth(), config.getScreenUnitHeight());
    }

    @Override
    public void render (float delta) {
        Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
       
        game.batch.setProjectionMatrix(camera.combined);
        controller.updateGame();
        game.batch.begin();
        controller.draw(game.batch);
        game.batch.flush();
        game.batch.end();
    }
    
    @Override
    public void resize(int width, int height) {
        mConfig.setScreeSize(width, height);
        camera.setToOrtho(false, mConfig.getScreenUnitWidth(), mConfig.getScreenUnitHeight());
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
