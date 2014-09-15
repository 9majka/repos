package com.mygdx.sample;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.sample.object.Object;

public class Model {
    private boolean m_Field[][];
    Texture m_Texture;
    
    public Model() {
        m_Field = new boolean[GameConfig.WIDTH][GameConfig.HEIGHT];
        m_Texture = new Texture(Gdx.files.internal("drop.png"));
    }
    
    public boolean watchObject (Object obj) {
        Array<GridPoint2> points = obj.getAbsolutePoints();
        
        for(GridPoint2 point : points) {
            if(interact(point.x, point.y)) {
                receiveObject(points);
                return true;
            }
        }
        return false;
    }
    
    private void receiveObject(Array<GridPoint2> points) {
        for(GridPoint2 point : points) {
            if(point.x< GameConfig.WIDTH && point.y < GameConfig.HEIGHT) {
                m_Field[point.x][point.y] = true;
            }
        }
    }
    
    private boolean interact(int x, int y) {
        y++;
        if(x < GameConfig.WIDTH && y < GameConfig.HEIGHT && m_Field[x][y] == true) {
            return true;
        }
        if(y == GameConfig.HEIGHT) {
            return true;
        }
        
        return false;
    }

    public void draw(SpriteBatch batch) {
        
        int cell = GameConfig.CELLSIZE;
        for(int j = 0; j < GameConfig.HEIGHT; j++) {
            for(int i = 0; i < GameConfig.WIDTH; i++) {
                
                if(m_Field[i][j] == true) {
                    batch.draw(m_Texture, i*cell, j*cell, cell, cell);
                }
            }
        }
    }
}