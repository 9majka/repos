package com.mygdx.sample.object;

public class GLObject extends Object{
    private final int celsCount = 4;
    private final int objectSize = 3;
    private static int m_RotationStates[][] = {
        {6,8,2,0},
        {7,5,1,3},
        {4,4,4,4},
        {1,3,7,5}
    };

    public GLObject() {
        super(ObjectType.OT_GLObject);
    }

    @Override
    public int getObjectSize() {
        return objectSize;
    }
    
    @Override
    public int getCellsCount() {
        return celsCount;
    }
    
    @Override
    protected int[][] getRotationStates() {
        return m_RotationStates;
    }
}