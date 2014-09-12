package com.mygdx.sample;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.sample.object.Object.ObjectRotation;

public class DropGestureListener implements GestureListener{
    final Screen gamescreen;
    int rot = 0;
    public DropGestureListener(final Screen screen) {
        gamescreen = screen;
        
    }
    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {

        System.out.println("touchDown");
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {

        Gametetr game = (Gametetr)gamescreen;
        game.obj.rotate(ObjectRotation.toRot(rot));
        rot++;
        if(rot > 3) {
            rot = 0;
        }
        System.out.println("tap");
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {

        System.out.println("longPress");
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {

        System.out.println("fling");
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {

       // gamescreen.face.x = x - 64 / 2;
        
        Gametetr game = (Gametetr)gamescreen;
        int step = (int)x/50;
        System.out.println(step);
        game.obj.shiftTo(step);

        System.out.println("pan");
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {

        System.out.println("panStop");
        return false;
    }

    @Override
    public boolean zoom (float originalDistance, float currentDistance){

       System.out.println("zoom");
       return false;
    }

    @Override
    public boolean pinch (Vector2 initialFirstPointer, Vector2 initialSecondPointer, Vector2 firstPointer, Vector2 secondPointer){

        System.out.println("pinch");
       return false;
    }
}