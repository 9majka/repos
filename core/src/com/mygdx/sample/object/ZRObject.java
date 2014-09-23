package com.mygdx.sample.object;


public class ZRObject extends GameObject{
    private final int celsCount = 4;
    private final int objectSize = 3;
    private static int m_RotationStates[][] = {
        {7,5,7,5},
        {4,4,4,4},
        {3,7,3,7},
        {0,6,0,6}
    };

    public ZRObject(final float blocSize) {
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