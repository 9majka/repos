package com.mygdx.sample;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.sample.object.Object;
import com.mygdx.sample.object.ObjectFactory;

public class Controller extends ControllerListener {
    
    private Object m_ActiveObj;
    private Model m_Model;
    private Field m_Field;
    private ObjectFactory m_ObjectFactory;
    
    public Controller() {
        Gdx.input.setInputProcessor(new GestureDetector(new DropGestureListener(this)));
        m_Model = new Model();
        m_Field = new Field();
        m_ObjectFactory = new ObjectFactory();
        m_ActiveObj = m_ObjectFactory.getNextObject();
        //Gdx.graphics.setContinuousRendering(false);
    }
    
    @Override
    public void onShiftTo(int step) {
        Array<GridPoint2> points = m_ActiveObj.getAbsolutePoints();

        for(GridPoint2 point : points) {
            if(point.x + step < 0) {
                return;
            }
            if(point.x + step >= GameConfig.WIDTH) {
                return;
            }
        }
        m_ActiveObj.shiftTo(step);
    }
    
    @Override
    public void onTap() {
        m_ActiveObj.rotate();
        int shiftX = m_ActiveObj.getShiftX();
        int offset = 0;
        Array<GridPoint2> points = m_ActiveObj.getAbsolutePoints();
        for(GridPoint2 point : points) {

        }
        if(offset != 0) {
            m_ActiveObj.shiftTo(shiftX - offset);
        }
    }
    
    public void updateGame() {
        m_ActiveObj.moveDownDelta(5);
        if(m_Model.watchObject(m_ActiveObj)) {
            m_ActiveObj.dispose();
            m_ActiveObj = m_ObjectFactory.getNextObject();
        }
    }
    
    public Object getActiveObject() {
        
        return m_ActiveObj;
    }
    
    public void draw(SpriteBatch batch) {
        m_Field.draw(batch);
        m_ActiveObj.draw(batch);
        m_Model.draw(batch);
    }
}