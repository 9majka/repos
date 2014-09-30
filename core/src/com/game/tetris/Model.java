package com.game.tetris;

import java.util.HashSet;
import java.util.Set;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.utils.Array;

public class Model {
    private final int m_BlockWidth;
    private final int m_BlockHeight;
    private short m_Field[][];
    private ModelListener mListener = null;
    private Set<Integer> mSelectedRows = new HashSet<Integer>();
    private int mScore = 0;
    private int mLevel = 1;
    private int mLevelStep;

    public Model(final int blockWidth, final int blocHeight) {
        m_BlockWidth = blockWidth;
        m_BlockHeight = blocHeight;
        m_Field = new short[m_BlockWidth][m_BlockHeight + 1];
        for(int i = 0; i < m_BlockWidth; i++) {
            m_Field[i][0] = 1;
        }
    }
    
    public void setLevelStep(int step) {
        mLevelStep = step;
    }
    
    
    public void setListener(ModelListener listener) {
        mListener = listener;
    }

    public final short[][] getField() {
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
                if(m_Field[i][j] != 0) {
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
            if(valid(i, j) && m_Field[i][j] != 0) {
                return false;
            }
        }
        return true;
    }

    public boolean proceessPoints (Array<GridPoint2> points, int shiftX, int shiftY, short type) {
        for(GridPoint2 point : points) {
            if(interact(point.x + shiftX, point.y + shiftY)) {
                receivePoints(points, shiftX, shiftY, type);
                return true;
            }
        }
        return false;
    }

    private boolean interact(int i, int j) {
        if(valid(i, j) && m_Field[i][j] != 0) {
            return true;
        }
        return false;
    }
    
    private void receivePoints(Array<GridPoint2> points, int shiftX, int shiftY, short type) {
        int i = 0;
        int j = 0;
        boolean gameover = false;
        for(GridPoint2 point : points) {
            i = point.x + shiftX;
            j = point.y + 1 + shiftY;
            if(valid(i, j)) {
                m_Field[i][j] = type;
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
        mSelectedRows.clear();
        int count = 0;
        int i = 0;
        int j;
        for(j = 1; j <= m_BlockHeight; j++) {
            count = 0;
            for(i = 0; i < m_BlockWidth; i++) {
                if(m_Field[i][j] != 0) {
                    count++;
                } else {
                    break;
                }
            }
            if(count == m_BlockWidth) {
                mSelectedRows.add(j);
            }
        }
       
        if(!mSelectedRows.isEmpty()) {
            int next_j = 0;
            for(j = 0; j <= m_BlockHeight; j++) {
                while(mSelectedRows.contains(next_j)) {
                    next_j++;
                }
                for(i = 0; i < m_BlockWidth; i++) {
                    if(next_j > m_BlockHeight) {
                        m_Field[i][j] = 0;
                    } else {
                        m_Field[i][j] = m_Field[i][next_j];
                    }
                }
                next_j++;
            }
            mScore += mSelectedRows.size();
            int level = (mScore / mLevelStep) + 1;
            if(level != mLevel) {
                mLevel = level;
                mListener.onLevelChange(mLevel);
            }
            mListener.onScoreChange(mScore);
        }
    }
}