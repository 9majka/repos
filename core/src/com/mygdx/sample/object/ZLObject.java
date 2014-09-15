package com.mygdx.sample.object;


public class ZLObject extends Object{
    private final int celsCount = 4;
    private final int objectSize = 3;
    private static int m_RotationStates[][] = {
        {0,2,8,6},
        {3,1,5,7},
        {4,4,4,4},
        {7,3,1,5}
    };

    public ZLObject() {
        super(ObjectType.OT_ZLObject);
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