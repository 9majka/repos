package com.mygdx.sample.object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;


public class TObject extends Object{
    private Array<Rectangle> rects;
    private final int celsCount = 5;
    
    private int rotationStates[][] = {
        {0,2,8,6},
        {1,5,7,3},
        {2,8,6,0},
        {4,4,4,4},
        {7,3,1,5}
    };
    
    private Array<Texture> textures;
    
    public TObject() {
        initPoints();
        initTextures();
    }

    private void initPoints() {
        rects = new Array<Rectangle>();
        int i = 0;
        int j = 0;
        for(int n = 0; n < celsCount; n++) {
            Rectangle rect = new Rectangle();
            int value = rotationStates[n][ObjectRotation.toInt(state)];
            i = value%3;
            j = value/3;
            rect.x = (i + shiftX) * CELLSIZE;
            rect.y = j * CELLSIZE + posY;
            rect.width = CELLSIZE;
            rect.height = CELLSIZE;
            rects.add(rect);
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
    
    @Override
    public void rotate(ObjectRotation rot) {
        super.rotate(rot);
        int number = 0;
        int i = 0;
        int j = 0;
        for(Rectangle rect: rects) {
            int value = rotationStates[number][ObjectRotation.toInt(state)];
            i = value%3;
            j = value/3;
            rect.x = (i + shiftX) * CELLSIZE;
            rect.y = j * CELLSIZE + posY;
            number++;
        }
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
        shiftY = (int)posY;
        for(Rectangle rect: rects) {
            int value = rotationStates[n][ObjectRotation.toInt(state)];
            j = value/3;
            rect.y = j * CELLSIZE + posY;
            n++;
        }
    }
    
    private void shiftPosX() {
        int i = 0;
        int n = 0;
        for(Rectangle rect: rects) {
            int value = rotationStates[n][ObjectRotation.toInt(state)];
            i = value%3;
            rect.x = (i + shiftX) * CELLSIZE;
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
    
    @Override
    public void draw(SpriteBatch batch) {
      
        for(Texture texture: textures) {
            Rectangle rect = rects.get(textures.indexOf(texture, true));
            batch.draw(texture, rect.x, rect.y, rect.width, rect.height);
        }
    }

}