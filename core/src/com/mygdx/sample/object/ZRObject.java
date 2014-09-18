package com.mygdx.sample.object;


public class ZRObject extends Object{
    private final int celsCount = 4;
    private final int objectSize = 3;
    private static int m_RotationStates[][] = {
        {2,8,6,0},
        {5,7,3,1},
        {4,4,4,4},
        {7,3,1,5}
    };

    public ZRObject(final int blocSize) {
        super(ObjectType.OT_ZRObject, "drop_pink.png", blocSize);
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