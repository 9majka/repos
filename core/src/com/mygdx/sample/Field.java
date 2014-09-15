package com.mygdx.sample;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Field {
    Texture FieldBg;
    
    public Field() {
        FieldBg = new Texture(Gdx.files.internal("field_bg.png"));
    }
    
    public void draw(SpriteBatch batch) {
        batch.draw(FieldBg, 0, 0, GameConfig.WIDTH_X, GameConfig.HEIGHT_Y);
    }
}