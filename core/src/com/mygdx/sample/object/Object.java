package com.mygdx.sample.object;

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
    
    public void dispose() {

    }
    
    public Array<GridPoint2> getAbsolutePoints() {
        return points;
    }
    
    public void rotate(ObjectRotation rot) {
        state = rot;
    }
    
    public void rotate() {
        int x = ObjectRotation.toInt(state);
        x++;
        state = ObjectRotation.toRot(x);
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
    }
    
    public void moveDown(float pos)
    {
        posY = pos;
    }
    
    public void moveDownDelta(float delta)
    {
        posY += delta;
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