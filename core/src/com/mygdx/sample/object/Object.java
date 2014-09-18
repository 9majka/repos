package com.mygdx.sample.object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.utils.Array;

public abstract class Object {
   
    public static final int ROTATION_STATES = 4;
    
    private int m_ShiftX = 0;//GameConfig.WIDTH / 2;
    private int m_ShiftY = 0;//GameConfig.HEIGHT - getObjectSize();
    private float m_UnitY = 0;
    private float m_DeltaY = 0;
    private final int m_BlockUnitSize;
    private ObjectRotation state = ObjectRotation.OR_0;
    
    protected Array<GridPoint2> m_Points;
    protected Array<Texture> textures;
    private final ObjectType m_Type;
    protected FileHandle m_TexureFile;
    
    public Object(final ObjectType type, String file, final int blockSize) {
        m_Type = type;
        m_BlockUnitSize = blockSize;
        m_TexureFile = Gdx.files.internal(file);
        m_UnitY = m_ShiftY * m_BlockUnitSize;
        initPoints();
        initTextures();
    }
    
    public FileHandle getFile() {
        return m_TexureFile;
    }
    
    public ObjectType getType() {
        return m_Type;
    }

    private void initTextures() {
        int celsCount = getCellsCount();
        textures = new Array<Texture>();
        for(int n = 0; n < celsCount; n++) {
            Texture texture = new Texture(m_TexureFile);
            textures.add(texture);
        }
    }
    
    private void initPoints() {
        int celsCount = getCellsCount();
        int objectSize = getObjectSize();
        int rotationStates[][] = getRotationStates();
        m_Points = new Array<GridPoint2>();
        int i = 0;
        int j = 0;
        int value = 0;
        for(int n = 0; n < celsCount; n++) {
            GridPoint2 point = new GridPoint2();
            
            value = rotationStates[n][ObjectRotation.toInt(state)];
            i = value%objectSize;
            j = value/objectSize;
            point.x = i;
            point.y = j;
            m_Points.add(point);
        }
    }

    protected int[][] getRotationStates() {
        return new int[0][0];
    }
    
    public void dispose() {

    }
    
    public int getObjectSize() {
        return 0;
    }
    
    public int getCellsCount() {
        return 0;
    }
    
    public Array<GridPoint2> getAbsolutePoints() {
        return m_Points;
    }
    
    private void rotateRects() {
        int number = 0;
        int objectSize = getObjectSize();
        int rotationStates[][] = getRotationStates();
        int i = 0;
        int j = 0;
        int value = 0;
        for(GridPoint2 point: m_Points) {
            value = rotationStates[number][ObjectRotation.toInt(state)];
            i = value%objectSize;
            j = value/objectSize;
            point.x = i;
            point.y = j;
            number++;
        }
    }
    
    public void rotate(ObjectRotation rot) {
        state = rot;
        rotateRects();
    }
    
    public void rotate() {
        int x = ObjectRotation.toInt(state);
        x++;
        state = ObjectRotation.toRot(x);
        rotateRects();
    }
    
    public void draw(SpriteBatch batch) {
        float x = 0;
        float y = 0;

        int number = 0;
        for(Texture texture: textures) {
            GridPoint2 point = m_Points.get(number);
            x = (point.x + m_ShiftX) * m_BlockUnitSize;
            y = (point.y + m_ShiftY) * m_BlockUnitSize + m_DeltaY;
            batch.draw(texture, x, y, m_BlockUnitSize, m_BlockUnitSize);
            number++;
        }
    }
    
    public int getShiftX() {
        return m_ShiftX;
    }
    
    public int getShiftY() {
        return m_ShiftY;
    }
    
    public void shiftTo(int stepX, int stepY)
    {
        shiftXTo(stepX);
        shiftYTo(stepY);
    }
    
    public void shiftXTo(int step)
    {
        m_ShiftX = step;
    }
    
    public void shiftYTo(int step)
    {
        m_ShiftY = step;
        m_UnitY = m_BlockUnitSize * m_ShiftY;
        movePosY();
    }

    public void moveDown(float pos) {
        m_UnitY = pos;
        movePosY();
    }
    
    public void moveDownDelta(float delta) {
        m_UnitY -= delta;
        movePosY();
    }
    
    private void movePosY() {
//        int j = 0;
//        int n = 0;
//        int objectSize = getObjectSize();
//        int rotationStates[][] = getRotationStates();
        m_ShiftY = (int)m_UnitY/m_BlockUnitSize;
        if(m_UnitY < 0) {
            m_ShiftY = m_ShiftY - 1;
        }
        m_DeltaY = m_UnitY - m_ShiftY * m_BlockUnitSize;

//        for(GridPoint2 point: m_Points) {
//            int value = rotationStates[n][ObjectRotation.toInt(state)];
//            j = value/objectSize;
//            point.y = j;
//            n++;
//        }
    }
    
    public enum ObjectRotation{
        OR_0,
        OR_90,
        OR_180,
        OR_270;
        
        public static int toInt(ObjectRotation x) {
            switch(x) {
                case OR_0:
                    return 0;
                case OR_90:
                    return 1;
                case OR_180:
                    return 2;
                case OR_270:
                    return 3;
            }
            return -1;
        }
        
        public static ObjectRotation toRot(int x) {
            switch(x) {
                case 0:
                    return OR_0;
                case 1:
                    return OR_90;
                case 2:
                    return OR_180;
                case 3:
                    return OR_270;
            }
            return OR_0;
        }
    }
    
    public enum ObjectType {
        OT_TObject(0),
        OT_GLObject(1),
        OT_GRObject(2),
        OT_SQObject(3),
        OT_ZLObject(4),
        OT_ZRObject(5),
        OT_STObject(6),
        OT_MAXObject(7);
        private final int m_Value;
        ObjectType(int value) {
            m_Value = value;
        }
        private static ObjectType[] allValues = values();
        
        public static ObjectType fromOrdinal(int n) {
            return allValues[n];
        }
        public static int toInt(ObjectType type) {
            return type.m_Value;
        }
    }

}