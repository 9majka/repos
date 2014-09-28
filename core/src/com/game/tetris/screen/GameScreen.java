package com.game.tetris.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.game.tetris.Controller;

public class GameScreen implements Screen {
    private final Tetris game;
    private Controller controller;
    private OrthographicCamera camera;
    private GameConfig mConfig;

    public GameScreen(final Tetris gam, GameConfig config){
        this.game = gam;
        mConfig = config;
        controller = new Controller(config);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, config.getScreenUnitWidth(), config.getScreenUnitHeight());
        //Gdx.graphics.setContinuousRendering(false);
    }

    private void update() {
        Gdx.gl.glClearColor(0, 0.2f, 0.2f, 1);
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
    public void render (float delta) {
        update();
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
        controller.dispose();
    }
}
