package com.game.tetris;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.utils.Array;
import com.game.tetris.object.GameObject;
import com.game.tetris.object.ObjectFactory;
import com.game.tetris.object.GameObject.ObjectType;
import com.game.tetris.screen.GameConfig;
import com.game.tetris.view.FieldView;

public class Controller implements ControllerListener, ModelListener{
    
    private enum GameState{Playing, GameOver};
    
    private GameObject m_ActiveObj;
    private Model m_Model;
    private FieldView m_FieldView;
    private ObjectFactory m_ObjectFactory;
    private int m_Speed = 3;
    private boolean mAcceleration = false;
    private final GameConfig m_Config;
    GameState mState = GameState.Playing;
    
    public Controller(final GameConfig config) {
        m_Config = config;
        Gdx.input.setInputProcessor(new GestureDetector(new EventController(this, m_Config)));
        m_Model = new Model(config.getFieldBlockWidth(), config.getFieldBlockHeight());
        m_Model.setListener(this);
        m_FieldView = new FieldView(config);
        m_ObjectFactory = new ObjectFactory(config);
        
        m_ActiveObj = m_ObjectFactory.getNextObject();
        int shiftX = config.getFieldBlockWidth()/2;
        int shiftY = config.getFieldBlockHeight();
        m_ActiveObj.shiftTo(shiftX, shiftY);
        m_FieldView.setActiveObject(m_ActiveObj);
        m_FieldView.setNextObject(ObjectType.toInt(m_ObjectFactory.getNextObjectType()));
        m_FieldView.setModel(m_Model);
    }
    
    @Override
    public void onShiftToDeltaX(int delta) {
        if(mState != GameState.Playing) {
            return;
        }
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
        if(mState != GameState.Playing) {
            return;
        }
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
            m_FieldView.setNextObject(ObjectType.toInt(m_ObjectFactory.getNextObjectType()));
        }
    }
    
    public void updateGame() {
        if(mState == GameState.Playing) {
            process();
        }
    }
    
    private void process() {
        int speed = m_Speed;
        if(mAcceleration) {
            speed = m_Config.getAccelerationSpeed();
        }
        if(m_ActiveObj != null) {
            int shiftY = m_ActiveObj.getShiftY();
            
            m_ActiveObj.moveDownDelta(speed);
            int shiftYNew = m_ActiveObj.getShiftY();

            if(shiftY != shiftYNew) {
                int shiftX = m_ActiveObj.getShiftX();
                Array<GridPoint2> points = m_ActiveObj.getAbsolutePoints();
                short type = (short)ObjectType.toInt(m_ActiveObj.getType());
                if(m_Model.proceessPoints(points, shiftX, shiftYNew, type)) {
                    m_Model.updateModel();
                    m_ActiveObj = null;
                    continuePlay();
                }
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
        if(mState == GameState.Playing) {
            m_FieldView.draw(batch);
        } else if(mState == GameState.GameOver) {
            m_FieldView.drawGameOver(batch);
        }
    }
    
    public void dispose() {
        m_FieldView.dispose();
        m_ObjectFactory.dispose();
    }
}