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
    private final int m_BlockWidth;
    private final int m_BlockHeight;
    private boolean m_Field[][];
    private ModelListener mListener = null;

    Texture m_Texture;
    
    public Model(final int blockWidth, final int blocHeight) {
        m_BlockWidth = blockWidth;
        m_BlockHeight = blocHeight;
        m_Field = new boolean[m_BlockWidth][m_BlockHeight];
        m_Texture = new Texture(Gdx.files.internal("drop_gray.png"));
        for(int i = 0; i < m_BlockWidth; i++) {
            m_Field[i][0] = true;
        }
    }
    
    public void setListener(ModelListener listener) {
        mListener = listener;
    }
    
    public boolean watchObject (Object obj) {
        Array<GridPoint2> points = obj.getAbsolutePoints();
        int shiftX = obj.getShiftX();
        int shiftY = obj.getShiftY();
        for(GridPoint2 point : points) {
            if(interact(point.x + shiftX, point.y + shiftY)) {
                receiveObject(points, shiftX, shiftY);
                return true;
            }
        }
        return false;
    }

    private boolean interact(int i, int j) {
        if(i < m_BlockWidth && j < m_BlockHeight ) {
            if(m_Field[i][j]) {
                return true;
            }
        }
        return false;
    }
    
    private void receiveObject(Array<GridPoint2> points, int shiftX, int shiftY) {
        int i = 0;
        int j = 0;
        boolean gameover = false;
        for(GridPoint2 point : points) {
            i = point.x + shiftX;
            j = point.y + 1 + shiftY;
            if(i < m_BlockWidth && j < m_BlockHeight) {
                m_Field[i][j] = true;
            }
            if(!gameover && j == m_BlockHeight - 1) {
                gameover = true;
            }
        }
        if(gameover) {
            mListener.onGameOver();
        }
    }
    
    public void updateModel() {
        Set<Integer> selectedRows = new HashSet<Integer>();
        for(int j = 1; j < m_BlockHeight; j++) {
            int count = 0;
            for(int i = 0; i < m_BlockWidth; i++) {
                if(m_Field[i][j] == true) {
                    count++;
                } else {
                    break;
                }
            }
            if(count == m_BlockWidth) {
                selectedRows.add(j);
            }
        }
        
        if(!selectedRows.isEmpty()) {
            boolean field[][] = new boolean [m_BlockWidth][m_BlockHeight];
            int next_j = 0;
            for(int j = 0; j < m_BlockHeight; j++) {
                while(selectedRows.contains(next_j)) {
                    next_j++;
                }
                for(int i = 0; i < m_BlockWidth; i++) {
                    if(next_j > m_BlockWidth) {
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

    public void draw(SpriteBatch batch, int blockSize) {
        for(int j = 0; j < m_BlockHeight; j++) {
            for(int i = 0; i < m_BlockWidth; i++) {
                
                if(m_Field[i][j] == true) {
                    batch.draw(m_Texture, i*blockSize, j*blockSize, blockSize, blockSize);
                }
            }
        }
    }
}