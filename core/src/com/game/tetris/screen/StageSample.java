package com.game.tetris.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class StageSample implements Screen {
    private final Tetris game;
    OrthographicCamera camera;
    
    private Stage stageField;
    private Image imageField;
    
    private Label label;

    public StageSample(final Tetris gam) {
        game = gam;
        camera = new OrthographicCamera();
        Viewport view = new FitViewport(320, 480, camera);
        stageField = new Stage(view);

        imageField = new Image(new Texture(Gdx.files.internal("field_bg.jpg")));
        imageField.setSize(100, 100);
        imageField.setPosition(100, 100);
        stageField.addActor(imageField);
        
        Skin skin = new Skin(Gdx.files.internal("data/uiskin.json"));
        label = new Label("00001", skin);
        label.setSize(100, 100);
        label.setPosition(100, 100);
        label.setAlignment(Align.right);
        stageField.addActor(label);
    }
    
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stageField.act(delta);
        stageField.draw();
        //camera.
        //stagePanel.draw();
    }

    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub
       stageField.getViewport().update(width, height, false);
    }

    @Override
    public void show() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub
        
    }

}
