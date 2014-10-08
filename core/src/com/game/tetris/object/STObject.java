package com.game.tetris.object;

public class STObject extends GameObject{
    private final int celsCount = 4;
    private final int objectSize = 4;
    private static final int m_RotationStates[][] = {
        {13,11,13,11},
        {9,10,9,10},
        {5,9,5,9},
        {1,8,1,8}
    };

    public STObject(final float blocSize) {
        super(ObjectType.OT_STObject, blocSize);
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