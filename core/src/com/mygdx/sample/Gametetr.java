package com.mygdx.sample;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector;
import com.mygdx.sample.object.Object;
import com.mygdx.sample.object.TObject;

public class Gametetr implements Screen {
    final Drop game;
    public Object obj;
    
    private OrthographicCamera camera;

    
    public Gametetr(final Drop gam){
        this.game = gam;
        Gdx.input.setInputProcessor(new GestureDetector(new DropGestureListener(this)));
        obj = new TObject();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 480, 800);
    }

    @Override
    public void render (float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
       
        game.batch.setProjectionMatrix(camera.combined);
        obj.moveDownDelta(1);// -= 200 * Gdx.graphics.getDeltaTime();
        game.batch.begin();
        obj.draw(game.batch);
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
