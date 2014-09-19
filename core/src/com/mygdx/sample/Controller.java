package com.mygdx.sample;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.sample.object.GameObject;
import com.mygdx.sample.object.ObjectFactory;

public class Controller implements ControllerListener, ModelListener{
    
    private enum GameState{Playing, GameOver};
    
    private GameObject m_ActiveObj;
    private Model m_Model;
    private FieldViewer m_FieldView;
    private ObjectFactory m_ObjectFactory;
    private int m_Speed = 1;
    private boolean mAcceleration = false;
    private final GameConfig m_Config;
    GameState mState = GameState.Playing;
    
    public Controller(final GameConfig config) {
        m_Config = config;
        Gdx.input.setInputProcessor(new GestureDetector(new EventController(this, m_Config)));
        m_Model = new Model(config.getFieldBlockWidth(), config.getFieldBlockHeight());
        m_Model.setListener(this);
        m_FieldView = new FieldViewer(config, config.getFieldUnitWidth(), config.getFieldUnitHeight());
        m_ObjectFactory = new ObjectFactory(config);
        
        m_ActiveObj = m_ObjectFactory.getNextObject();
        int shiftX = config.getFieldBlockWidth()/2;
        int shiftY = config.getFieldBlockHeight();
        m_ActiveObj.shiftTo(shiftX, shiftY);
        m_FieldView.setActiveObject(m_ActiveObj);
        m_FieldView.setModel(m_Model);
        //Gdx.graphics.setContinuousRendering(false);
    }
    
    @Override
    public void onShiftToDeltaX(int delta) {
        if(mAcceleration == true) {
            return;
        }
        int shiftX = m_ActiveObj.getShiftX() + delta;
        Array<GridPoint2> points = m_ActiveObj.getAbsolutePoints();

        for(GridPoint2 point : points) {
            if(point.x + shiftX < 0) {
                return;
            }
            if(point.x + shiftX >= m_Config.getFieldBlockWidth()) {
                return;
            }
        }
        if(m_Model.isShiftPossible(points, shiftX, m_ActiveObj.getShiftY())) {
            m_ActiveObj.shiftXTo(shiftX);
        }
    }
    
    @Override
    public void onAccelarate() {
        mAcceleration = true;
    }

    @Override
    public void onRotate() {
        if(mAcceleration == true) {
            return;
        }
        
        int shiftX = m_ActiveObj.getShiftX();
        int shiftY = m_ActiveObj.getShiftY();
        
        Array<GridPoint2> rotPoints = m_ActiveObj.getNextRotationPoints();
        if(!m_Model.isRotatePossible(rotPoints, shiftX, shiftY)) {
            return;
        }
        
        int offset = 0;
        int min = 0;
        int max = 0;
        int curPoint = 0;
        for(GridPoint2 point : rotPoints) {
            curPoint = point.x + shiftX;
            if(curPoint > max) {
                max = curPoint;
            }
            if(curPoint < min) {
                min = curPoint;
            }
        }
        if(min < 0) {
            offset = min;
        } else if(max >= m_Config.getFieldBlockWidth()) {
            offset = max - m_Config.getFieldBlockWidth() + 1;
        }
        if(offset != 0) {
            if(!m_Model.isRotatePossible(rotPoints, shiftX - offset, shiftY)) {
                return;
            }
            m_ActiveObj.shiftXTo(shiftX - offset);
        }
        m_ActiveObj.rotate();
    }
    
    private void continuePlay() {
        if(mState == GameState.Playing) {
            mAcceleration = false;
            m_ActiveObj = m_ObjectFactory.getNextObject();
            
            int shiftX = m_Config.getFieldBlockWidth()/2;
            int shiftY = m_Config.getFieldBlockHeight();
            m_ActiveObj.shiftTo(shiftX, shiftY);
            m_FieldView.setActiveObject(m_ActiveObj);
        }
    }
    
    public void updateGame() {
        if(mState != GameState.GameOver) {
            int speed = m_Speed;
            if(mAcceleration) {
                speed = m_Config.getAccelerationSpeed();
            }
            m_ActiveObj.moveDownDelta(speed);
            if(m_Model.watchObject(m_ActiveObj)) {
                m_Model.updateModel();
                m_ActiveObj.dispose();
                m_ActiveObj = null;
                continuePlay();
            }
        }
    }

    public GameObject getActiveObject() {
        return m_ActiveObj;
    }
    
    @Override
    public void onLevelChange(int level) {
        
    }
    
    @Override
    public void onGameOver() {
        mState = GameState.GameOver;
    }
    
    public void draw(SpriteBatch batch) {
        if(mState != GameState.GameOver) {
            m_FieldView.draw(batch);
        }
    }
}