package com.mygdx.sample.object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.sample.GameConfig;

public abstract class Object {
   
    public static final int ROTATION_STATES = 4;
    
    public int shiftX = 0;
    public int shiftY = 0;
    public ObjectRotation state = ObjectRotation.OR_0;
    public float posY = 0;
    protected Array<GridPoint2> points;
    protected Array<Texture> textures;
    
    public Object() {
        initPoints();
        initTextures();
    }
    
    private void initTextures() {
        int celsCount = getCellsCount();
        System.out.println(celsCount);
        textures = new Array<Texture>();
        for(int n = 0; n < celsCount; n++) {
            Texture texture = new Texture(Gdx.files.internal("drop.png"));
            textures.add(texture);
        }
    }
    
    private void initPoints() {
        int celsCount = getCellsCount();
        int objectSize = getObjectSize();
        int rotationStates[][] = getRotationStates();
        points = new Array<GridPoint2>();
        int i = 0;
        int j = 0;
        for(int n = 0; n < celsCount; n++) {
            GridPoint2 point = new GridPoint2();
            
            int value = rotationStates[n][ObjectRotation.toInt(state)];
            i = value%objectSize;
            j = value/objectSize;
            point.x = i + shiftX;
            point.y = j + shiftY;
            points.add(point);
        }
    }
    
    protected int[][] getRotationStates() {
        return new int[100][100];
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
        return points;
    }
    
    private void rotateRects() {
        int number = 0;
        int objectSize = getObjectSize();
        int rotationStates[][] = getRotationStates();
        int i = 0;
        int j = 0;
        for(GridPoint2 point: points) {
            int value = rotationStates[number][ObjectRotation.toInt(state)];
            i = value%objectSize;
            j = value/objectSize;
            point.x = i + shiftX;
            point.y = j + shiftY;
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
        int height = (int)(posY/cell);
        float delta = posY - height*cell;
        int number = 0;
        for(Texture texture: textures) {
            GridPoint2 point = points.get(number);
            x = point.x * cell;
            y = point.y * cell + delta;
            batch.draw(texture, x, y, cell, cell);
            number++;
        }
    }
    
    public void shiftTo(int step)
    {
        shiftX = step;
        shiftPosX();
    }
    
    private void shiftPosX() {
        int i = 0;
        int n = 0;
        int objectSize = getObjectSize();
        int rotationStates[][] = getRotationStates();
        for(GridPoint2 point: points) {
            int value = rotationStates[n][ObjectRotation.toInt(state)];
            i = value%objectSize;
            point.x = (i + shiftX);
            n++;
        }
    }
    
    public void moveDown(float pos)
    {
        posY = pos;
        movePosY();
    }
    
    public void moveDownDelta(float delta)
    {
        posY += delta;
        movePosY();
    }
    
    private void movePosY() {
        int j = 0;
        int n = 0;
        int objectSize = getObjectSize();
        int rotationStates[][] = getRotationStates();
        shiftY = (int)posY/GameConfig.CELLSIZE;
        for(GridPoint2 point: points) {
            int value = rotationStates[n][ObjectRotation.toInt(state)];
            j = value/objectSize;
            point.y = j + shiftY;
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

}