package com.mygdx.sample.object;


public class STObject extends Object{
    private final int celsCount = 4;
    private final int objectSize = 4;
    private static int m_RotationStates[][] = {
        {1,7,14,8},
        {5,6,10,9},
        {9,5,6,10},
        {13,4,2,11}
    };

    public STObject() {
        super(ObjectType.OT_STObject);
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