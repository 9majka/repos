package com.mygdx.sample.object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.sample.GameConfig;

public abstract class Object {
   
    public static final int ROTATION_STATES = 4;
    
    private int shiftX = GameConfig.WIDTH / 2;
    private int shiftY = GameConfig.HEIGHT - getObjectSize();
    private float posY = shiftY * GameConfig.CELLSIZE;
    private float deltaY = 0;
    private ObjectRotation state = ObjectRotation.OR_0;
    
    protected Array<GridPoint2> m_Points;
    protected Array<Texture> textures;
    private final ObjectType m_Type;
    protected FileHandle m_TexureFile;
    
    public Object(final ObjectType type, String file) {
        m_Type = type;
        m_TexureFile = Gdx.files.internal(file);
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
    
    public int getShiftX() {
        return shiftX;
    }
    
    public int getShiftY() {
        return shiftY;
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
        float cell = GameConfig.CELLSIZE;

        int number = 0;
        for(Texture texture: textures) {
            GridPoint2 point = m_Points.get(number);
            x = (point.x + shiftX) * cell;
            y = (point.y + shiftY) * cell + deltaY;
            Color color = batch.getColor();
            batch.setColor ( 1f, 1f, 1f, 1f);
            batch.draw(texture, x, y, cell, cell);
            batch.setColor (color);
            number++;
        }
    }
    
    public void shiftTo(int step)
    {
        shiftX = step;
    }

    public void moveDown(float pos) {
        posY = pos;
        movePosY();
    }
    
    public void moveDownDelta(float delta) {
        posY -= delta;
        movePosY();
    }
    
    private void movePosY() {
        int j = 0;
        int n = 0;
        int objectSize = getObjectSize();
        int rotationStates[][] = getRotationStates();
        shiftY = (int)posY/GameConfig.CELLSIZE;
        if(posY < 0) {
            shiftY = shiftY - 1;
        }
        deltaY = posY - shiftY*GameConfig.CELLSIZE;

        for(GridPoint2 point: m_Points) {
            int value = rotationStates[n][ObjectRotation.toInt(state)];
            j = value/objectSize;
            point.y = j;
            n++;
        }
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