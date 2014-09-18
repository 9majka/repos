package com.mygdx.sample;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Field {
    private Texture FieldBg;
    private int m_UnitWidth;
    private int m_UnitHeight;
    
    public Field(int unitWidth, int unitHeight) {
        m_UnitWidth = unitWidth;
        m_UnitHeight = unitHeight;
        FieldBg = new Texture(Gdx.files.internal("field_bg.jpg"));
    }
    
    public void draw(SpriteBatch batch) {
        batch.draw(FieldBg, 0, 0, m_UnitWidth, m_UnitHeight);
    }
}