package com.mygdx.sample.object;

public class STObject extends Object{
    private final int celsCount = 4;
    private final int objectSize = 4;
    private static int m_RotationStates[][] = {
        {13,11,2,4},
        {9,10,6,5},
        {5,9,10,6},
        {1,8,14,7}
    };

    public STObject() {
        super(ObjectType.OT_STObject, "drop_yellow.png");
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