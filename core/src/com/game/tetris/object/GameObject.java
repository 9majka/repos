package com.game.tetris.object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.utils.Array;

public abstract class GameObject {
   
    public static final int ROTATION_STATES = 4;
    
    private int m_ShiftX = 0;//GameConfig.WIDTH / 2;
    private int m_ShiftY = 0;//GameConfig.HEIGHT - getObjectSize();
    private float m_UnitY = 0;
    private final float m_BlockUnitHeight;
    private ObjectRotation state = ObjectRotation.OR_0;
    
    private Array<GridPoint2> m_Points;
    private Array<GridPoint2> mRotatePoints;

    private final ObjectType m_Type;
    private FileHandle m_TexureFile;
    private Texture m_Texture;
    
    public GameObject(final ObjectType type, final float blockSize) {
        m_Type = type;
        m_BlockUnitHeight = blockSize;
        m_TexureFile = Gdx.files.internal(getTextureFileRath(type));
        m_UnitY = m_ShiftY * m_BlockUnitHeight;
        initPoints();
        initTextures();
    }
    
    public FileHandle getFile() {
        return m_TexureFile;
    }
    
    public Texture getTexture() {
        return m_Texture;
    }
    
    public ObjectType getType() {
        return m_Type;
    }

    private void initTextures() {
        m_Texture = new Texture(m_TexureFile);
    }
    
    private void initPoints() {
        int celsCount = getCellsCount();
        int objectSize = getObjectSize();
        int rotationStates[][] = getRotationStates();
        m_Points = new Array<GridPoint2>();
        mRotatePoints = new Array<GridPoint2>();
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
            mRotatePoints.add(new GridPoint2());
        }
    }
    
    public void dispose() {
    }
    
    abstract protected int[][] getRotationStates();
    abstract public int getObjectSize();
    abstract public int getCellsCount();
    
    public Array<GridPoint2> getAbsolutePoints() {
        return m_Points;
    }
    
    public Array<GridPoint2> getNextRotationPoints() {
        int number = 0;
        int objectSize = getObjectSize();
        int rotationStates[][] = getRotationStates();
        int i = 0;
        int j = 0;
        int value = 0;
        for(GridPoint2 point: mRotatePoints) {
            value = rotationStates[number][ObjectRotation.nextRotInt(state)];
            i = value%objectSize;
            j = value/objectSize;
            point.x = i;
            point.y = j;
            number++;
        }
        
        return mRotatePoints;
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
        state = ObjectRotation.nextRot(state);
        rotateRects();
    }

    public int getShiftX() {
        return m_ShiftX;
    }
    
    public int getShiftY() {
        return m_ShiftY;
    }
    
    public float getPosY() {
        return m_UnitY;
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
        m_UnitY = m_BlockUnitHeight * m_ShiftY;
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
        m_ShiftY = (int)(m_UnitY / m_BlockUnitHeight);
        if(m_UnitY < 0) {
            m_ShiftY = m_ShiftY - 1;
        }
    }
    
    public static String getPreviewObjectFileRath(int type) {
        ObjectType objType = ObjectType.fromOrdinal(type);
        return getPreviewObjectFileRath(objType);
    }
    
    public static String getPreviewObjectFileRath(ObjectType type) {
        switch (type) {
            case OT_TObject:
                return "preview/tob.png";
            case OT_GLObject:
                return "preview/glob.png";
            case OT_GRObject:
                return "preview/grob.png";
            case OT_SQObject:
                return "preview/sqob.png";
            case OT_ZLObject:
                return "preview/zlob.png";
            case OT_ZRObject:
                return "preview/zrob.png";
            case OT_STObject:
                return "preview/stob.png";
            default: 
                return "item/drop_gray.png";
        }
    }
    
    public static String getTextureFileRath(int type) {
        ObjectType objType = ObjectType.fromOrdinal(type);
        return getTextureFileRath(objType);
    }
    
    public static String getTextureFileRath(ObjectType type) {
        switch (type) {
            case OT_TObject:
                return "item/drop_red.png";
            case OT_GLObject:
                return "item/drop_blue.png";
            case OT_GRObject:
                return "item/drop_bir.png";
            case OT_SQObject:
                return "item/drop_green.png";
            case OT_ZLObject:
                return "item/drop.png";
            case OT_ZRObject:
                return "item/drop_pink.png";
            case OT_STObject:
                return "item/drop_yellow.png";
            default: 
                return "item/drop_gray.png";
        }
    }
    
    public enum ObjectRotation{
        OR_0(0),
        OR_90(1),
        OR_180(2),
        OR_270(3);
        private final int m_Value;
        ObjectRotation(int value) {
            m_Value = value;
        }
        
        private static ObjectRotation[] allValues = values();
        
        public static ObjectRotation toRot(int n) {
            return allValues[n];
        }
        
        public static ObjectRotation nextRot(ObjectRotation currentRot) {
            int current = toInt(currentRot);
            current++;
            if(current > 3) {
                current = 0;
            }
            return allValues[current];
        }
        
        public static int nextRotInt(ObjectRotation currentRot) {
            int current = toInt(currentRot);
            current++;
            if(current > 3) {
                current = 0;
            }
            return current;
        }
        
        public static ObjectRotation nextRot(int current) {
            current++;
            if(current > 3) {
                current = 0;
            }
            return allValues[current];
        }
        
        public static int toInt(ObjectRotation type) {
            return type.m_Value;
        }
    }
    
    public enum ObjectType {
        OT_None(0),
        OT_GLObject(1),
        OT_GRObject(2),
        OT_SQObject(3),
        OT_ZLObject(4),
        OT_ZRObject(5),
        OT_STObject(6),
        OT_TObject(7),
        OT_MAXObject(8);
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