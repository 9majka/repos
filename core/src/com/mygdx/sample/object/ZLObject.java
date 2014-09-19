package com.mygdx.sample.object;

public class ZLObject extends GameObject{
    private final int celsCount = 4;
    private final int objectSize = 3;
    private static int m_RotationStates[][] = {
        {7,8,7,8},
        {4,7,4,7},
        {5,4,5,4},
        {2,3,2,3}
    };

    public ZLObject(final int blocSize) {
        super(ObjectType.OT_ZLObject, "drop.png", blocSize);
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