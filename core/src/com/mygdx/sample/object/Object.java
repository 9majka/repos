package com.mygdx.sample.object;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Object {
   
    public static final int ROTATION_STATES = 4;
    public static final int CELLSIZE = 50;
    
    public int shiftX = 0;
    public int shiftY = 0;
    public ObjectRotation state = ObjectRotation.OR_0;
    public float posY = 0;
    
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

    public void dispose() {

    }
    
    public void rotate(ObjectRotation rot) {
        state = rot;
    }
    
    public void draw(SpriteBatch batch) {
        
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

}