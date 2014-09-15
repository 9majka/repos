package com.mygdx.sample;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class Gametetr implements Screen {
    final Drop game;
    private Controller controller;
    
    private OrthographicCamera camera;

    
    public Gametetr(final Drop gam){
        this.game = gam;
        
        controller = new Controller();
        
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 480, 800);
        
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
