package com.mygdx.sample;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class Model {
    public static final int WIDTH = 10;
    public static final int HEIGHT = 20;
    
    private boolean field[][];
    private Array<Rectangle> rects;
    FileHandle file;
    
    public Model() {
        field = new boolean[WIDTH][HEIGHT];
        file = Gdx.files.internal("drop.png");
    }
    
    public void draw(SpriteBatch batch) {
        for(int j = 0; j < HEIGHT; j++) {
            for(int i = 0; i < WIDTH; i++) {
                Texture texture = new Texture(file);
                if(field[i][j] == true) {
                    batch.draw(texture, 100, 100);
                }
            }
        }
    }
}