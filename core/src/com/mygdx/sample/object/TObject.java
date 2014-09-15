package com.mygdx.sample.object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.sample.GameConfig;

public class TObject extends Object{
    private final int celsCount = 5;
    private int rotationStates[][] = {
        {0,2,8,6},
        {1,5,7,3},
        {2,8,6,0},
        {4,4,4,4},
        {7,3,1,5}
    };

    public TObject() {
        initPoints();
        initTextures();
    }

    private void initPoints() {
        points = new Array<GridPoint2>();
        int i = 0;
        int j = 0;
        for(int n = 0; n < celsCount; n++) {
            GridPoint2 point = new GridPoint2();
            
            int value = rotationStates[n][ObjectRotation.toInt(state)];
            i = value%3;
            j = value/3;
            point.x = i + super.shiftX;
            point.y = j + super.shiftY;
            points.add(point);
        }
    }
    
    private void initTextures() {
        textures = new Array<Texture>();
        for(int n = 0; n < celsCount; n++) {
            Texture texture = new Texture(Gdx.files.internal("drop.png"));
            textures.add(texture);
        }
    }

    @Override
    public void dispose() {

    }

    private void rotateRects() {
        int number = 0;
        int i = 0;
        int j = 0;
        for(GridPoint2 point: points) {
            int value = rotationStates[number][ObjectRotation.toInt(state)];
            i = value%3;
            j = value/3;
            point.x = i + shiftX;
            point.y = j + shiftY;
            number++;
        }
    }
    
    @Override
    public void rotate(ObjectRotation rot) {
        super.rotate(rot);
        rotateRects();
    }
    
    @Override
    public void rotate() {
        super.rotate();
        rotateRects();
    }
    
    @Override
    public void shiftTo(int step)
    {
        super.shiftTo(step);
        shiftPosX();
    }
    
    private void movePosY() {
        int j = 0;
        int n = 0;
        shiftY = (int)posY/GameConfig.CELLSIZE;
        for(GridPoint2 point: points) {
            int value = rotationStates[n][ObjectRotation.toInt(state)];
            j = value/3;
            point.y = j + shiftY;
            n++;
        }
    }
    
    private void shiftPosX() {
        int i = 0;
        int n = 0;
        for(GridPoint2 point: points) {
            int value = rotationStates[n][ObjectRotation.toInt(state)];
            i = value%3;
            point.x = (i + shiftX);
            n++;
        }
    }
    
    @Override
    public void moveDown(float pos) {
        super.moveDown(pos);
        movePosY();
    }
    
    @Override
    public void moveDownDelta(float delta) {
        super.moveDownDelta(delta);
        movePosY();
    }

}