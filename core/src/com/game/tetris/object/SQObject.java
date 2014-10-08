package com.game.tetris.object;

public class SQObject extends GameObject{
    private final int celsCount = 4;
    private final int objectSize = 2;
    private static int m_RotationStates[][] = {
        {0,0,0,0},
        {1,1,1,1},
        {2,2,2,2},
        {3,3,3,3}
    };

    public SQObject(final float blocSize) {
        super(ObjectType.OT_SQObject, blocSize);
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