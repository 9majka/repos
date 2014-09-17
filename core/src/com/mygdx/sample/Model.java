package com.mygdx.sample;

import java.util.HashSet;
import java.util.Set;

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
        m_Texture = new Texture(Gdx.files.internal("drop_gray.png"));
        for(int i = 0;i<GameConfig.WIDTH; i++) {
            m_Field[i][0] = true;
        }
    }
    
    public boolean watchObject (Object obj) {
        Array<GridPoint2> points = obj.getAbsolutePoints();
        int shiftX = obj.getShiftX();
        int shiftY = obj.getShiftY();
        for(GridPoint2 point : points) {
            if(interact(point.x + shiftX, point.y + shiftY)) {
                receiveObject(points, shiftX, shiftY);
                updateModel();
                return true;
            }
        }
        return false;
    }

    private boolean interact(int x, int y) {
        if(m_Field[x][y]) {
            return true;
        }
        return false;
    }
    
    private void receiveObject(Array<GridPoint2> points, int shiftX, int shiftY) {
        for(GridPoint2 point : points) {
            if(point.x< GameConfig.WIDTH && point.y < GameConfig.HEIGHT) {
                m_Field[point.x + shiftX][point.y + 1 + shiftY] = true;
            }
        }
    }
    
    private void updateModel() {
        Set<Integer> selectedRows = new HashSet<Integer>();
        for(int j = 1; j < GameConfig.HEIGHT; j++) {
            int count = 0;
            for(int i = 0; i < GameConfig.WIDTH; i++) {
                if(m_Field[i][j] == true) {
                    count++;
                } else {
                    break;
                }
            }
            if(count == GameConfig.WIDTH) {
                selectedRows.add(j);
            }
        }
        
        if(!selectedRows.isEmpty()) {
            boolean field[][] = new boolean [GameConfig.WIDTH][GameConfig.HEIGHT];
            int next_j = 0;
            for(int j = 0; j < GameConfig.HEIGHT; j++) {
                while(selectedRows.contains(next_j)) {
                    next_j++;
                }
                for(int i = 0; i < GameConfig.WIDTH; i++) {
                    if(next_j > GameConfig.WIDTH) {
                        field[i][j] = false;
                    }
                    else {
                        field[i][j] = m_Field[i][next_j];
                    }
                }
                next_j++;
            }
            m_Field = field;
        }
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