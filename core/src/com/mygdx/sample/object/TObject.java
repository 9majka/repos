package com.mygdx.sample.object;

public class TObject extends Object{
    private final int celsCount = 4;
    private final int objectSize = 3;
    private static int m_RotationStates[][] = {
        {0,2,8,6},
        {1,5,7,3},
        {2,8,6,0},
        {4,4,4,4}
    };

    public TObject() {
        super(ObjectType.OT_TObject);
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