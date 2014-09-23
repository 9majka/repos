package com.mygdx.sample;

import java.util.HashSet;
import java.util.Set;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.utils.Array;

public class Model {
    public class BlockItem {
        public boolean mValue = false;
        public int mType = 1;
    }
    private final int m_BlockWidth;
    private final int m_BlockHeight;
    private BlockItem m_Field[][];
    private ModelListener mListener = null;

    public Model(final int blockWidth, final int blocHeight) {
        m_BlockWidth = blockWidth;
        m_BlockHeight = blocHeight;
        m_Field = new BlockItem[m_BlockWidth][m_BlockHeight + 1];
        for(int i = 0; i < m_BlockWidth; i++) {
            for(int j = 0; j <= m_BlockHeight; j++) {
                m_Field[i][j] = new BlockItem();
            }
        }
        for(int i = 0; i < m_BlockWidth; i++) {
            m_Field[i][0].mValue = true;
        }
    }
    
    public void setListener(ModelListener listener) {
        mListener = listener;
    }

    public final BlockItem[][] getField() {
        return m_Field;
    }
    
    private boolean valid(int i, int j) {
        if( (i >= 0 && i < m_BlockWidth) &&
            (j >= 0 && j <= m_BlockHeight) ) {
            return true;
        }
        return false;
    }
    
    public boolean isShiftPossible(Array<GridPoint2> points, int shiftX, int shiftY) {
        int i = 0;
        int j = 0;
        for(GridPoint2 point : points) {
            i = point.x + shiftX;
            j = point.y + shiftY;
            if(valid(i, j)) {
                if(m_Field[i][j].mValue == true) {
                    return false;
                }
            } else {
                //return false;
            }
        }
        return true;
    }
    
    public boolean isRotatePossible(Array<GridPoint2> points, int shiftX, int shiftY) {
        int i = 0;
        int j = 0;
        for(GridPoint2 point : points) {
            i = point.x + shiftX;
            j = point.y + shiftY;
            if(valid(i, j) && m_Field[i][j].mValue == true) {
                return false;
            }
        }
        return true;
    }

    public boolean proceessPoints (Array<GridPoint2> points, int shiftX, int shiftY, int type) {
        for(GridPoint2 point : points) {
            if(interact(point.x + shiftX, point.y + shiftY)) {
                receivePoints(points, shiftX, shiftY, type);
                return true;
            }
        }
        return false;
    }

    private boolean interact(int i, int j) {
        if(valid(i, j) && m_Field[i][j].mValue == true) {
            return true;
        }
        return false;
    }
    
    private void receivePoints(Array<GridPoint2> points, int shiftX, int shiftY, int type) {
        int i = 0;
        int j = 0;
        boolean gameover = false;
        for(GridPoint2 point : points) {
            i = point.x + shiftX;
            j = point.y + 1 + shiftY;
            if(valid(i, j)) {
                m_Field[i][j].mValue = true;
                m_Field[i][j].mType = type;
            }
            if(!gameover && j > m_BlockHeight) {
                gameover = true;
            }
        }
        if(gameover) {
            mListener.onGameOver();
        }
    }
    
    public void updateModel() {
        Set<Integer> selectedRows = new HashSet<Integer>();
        for(int j = 1; j <= m_BlockHeight; j++) {
            int count = 0;
            for(int i = 0; i < m_BlockWidth; i++) {
                if(m_Field[i][j].mValue == true) {
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
            BlockItem field[][] = new BlockItem [m_BlockWidth][m_BlockHeight + 1];
            for(int i = 0; i < m_BlockWidth; i++) {
                for(int j = 0; j <= m_BlockHeight; j++) {
                    field[i][j] = new BlockItem();
                }
            }
            int next_j = 0;
            for(int j = 0; j <= m_BlockHeight; j++) {
                while(selectedRows.contains(next_j)) {
                    next_j++;
                }
                for(int i = 0; i < m_BlockWidth; i++) {
                    if(next_j > m_BlockHeight) {
                        field[i][j].mValue = false;
                    } else {
                        field[i][j].mValue = m_Field[i][next_j].mValue;
                        field[i][j].mType = m_Field[i][next_j].mType;
                    }
                }
                next_j++;
            }
            m_Field = field;
        }
    }
}