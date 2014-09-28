package com.game.tetris.object;


public class GRObject extends GameObject{
    private final int celsCount = 4;
    private final int objectSize = 3;
    private static int m_RotationStates[][] = {
        {7,5,1,3},
        {8,2,0,6},
        {4,4,4,4},
        {1,3,7,5}
    };

    public GRObject(final float blocSize) {
        super(ObjectType.OT_GRObject, "drop_bir.png", blocSize);
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