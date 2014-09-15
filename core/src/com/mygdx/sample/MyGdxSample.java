package com.mygdx.sample;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class MyGdxSample implements Screen {
    final Drop game;
    
    private OrthographicCamera camera;
    Texture img;
    int w_width;
    int w_height;
    
    private Texture dropImage;
    private Texture faceImage;
    private Sound dropSound;
    private Music rainMusic;
    
    public Rectangle face;
    private Array<Rectangle> raindrops;
    private long lastDropTime;
    int dropsGathered;
    
    boolean freek;
    
    public MyGdxSample(final Drop gam){
        this.game = gam;
        freek = false;
        //Gdx.input.setInputProcessor(new GestureDetector(new DropGestureListener(this)));
        
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 480, 800);
        
        img = new Texture("sampleimg.jpg");
        dropImage = new Texture(Gdx.files.internal("drop.png"));
        faceImage = new Texture(Gdx.files.internal("tomato.png"));

        dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
        rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
        
        rainMusic.setLooping(true);
        
        face = new Rectangle();
        face.x = 480 / 2 - 64 / 2;
        face.y = 20;
        face.width = 64;
        face.height = 64;
        
        raindrops = new Array<Rectangle>();
        spawnRaindrop();
    }

    private void spawnRaindrop() {
        Rectangle raindrop = new Rectangle();
        raindrop.x = MathUtils.random(0, 480-64);
        raindrop.y = 800;
        raindrop.width = 64;
        raindrop.height = 64;
        raindrops.add(raindrop);
        lastDropTime = TimeUtils.nanoTime();
     }

    @Override
    public void render (float delta) {
       Gdx.gl.glClearColor(0, 0, 0.2f, 1);
       Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        
        if(Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            face.x = touchPos.x - 64 / 2;
         }
       //Gdx.graphics.setContinuousRendering(false);
        

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(img, 0, 0, 480, 800);
        game.font.draw(game.batch, "Drops Collected: " + dropsGathered, 0, 480);
        game.batch.draw(faceImage, face.x, face.y);
        
        for(Rectangle raindrop: raindrops) {
            game.batch.draw(dropImage, raindrop.x, raindrop.y);
         }
        game.batch.end();
        
        if(TimeUtils.nanoTime() - lastDropTime > 1000000000) spawnRaindrop();
        
        Iterator<Rectangle> iter = raindrops.iterator();
        while(iter.hasNext()) {
           Rectangle raindrop = iter.next();
           raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
           if(raindrop.y + 64 < 0) iter.remove();
           if(raindrop.overlaps(face)) {
               dropsGathered++;
               dropSound.play();
               iter.remove();
            }
        }
        
        if(dropsGathered > 10 && freek == false){
            faceImage.dispose();
            faceImage = new Texture(Gdx.files.internal("tomato_freek1.png"));
        }
           
    }
    
    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        rainMusic.play();
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
        dropImage.dispose();
        faceImage.dispose();
        dropSound.dispose();
        rainMusic.dispose();
    }
}
