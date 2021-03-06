package com.game.tetris;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.utils.Array;
import com.game.tetris.object.GameObject;
import com.game.tetris.object.GameObject.ObjectType;
import com.game.tetris.object.ObjectFactory;
import com.game.tetris.screen.GameConfig;
import com.game.tetris.screen.GameScreen;
import com.game.tetris.view.FieldView;
import com.game.tetris.view.PanelView;

public class Controller implements ControllerListener, ModelListener{
    
    private enum GameState{Playing, Pause, GameOver};
    
    private GameObject m_ActiveObj;
    private Model m_Model;
    private FieldView m_FieldView;
    private PanelView mPanel;
    private ObjectFactory m_ObjectFactory;
    private float m_Speed = 1.0f;
    private boolean mAcceleration = false;
    private final GameConfig m_Config;
    GameState mState = GameState.Playing;
    final GameScreen mParentScreeen;

    public Controller(GameScreen parentScreen, final GameConfig config) {
        mParentScreeen = parentScreen;
        m_Config = config;
        Gdx.input.setInputProcessor(new GestureDetector(new EventController(this, m_Config)));
        m_Model = new Model(config.getFieldBlockWidth(), config.getFieldBlockHeight());
        m_Model.setListener(this);
        m_Model.setLevelStep(config.mLevelStep);
        m_FieldView = new FieldView(config);
        mPanel = new PanelView(config);
        m_ObjectFactory = new ObjectFactory(config);
        
        m_ActiveObj = m_ObjectFactory.getNextObject();
        int shiftX = config.getFieldBlockWidth()/2 - 1;
        int shiftY = config.getFieldBlockHeight();
        m_ActiveObj.shiftTo(shiftX, shiftY);
        m_FieldView.setActiveObject(m_ActiveObj);
        mPanel.setNextObject(ObjectType.toInt(m_ObjectFactory.getNextObjectType()));
        m_FieldView.setModel(m_Model);
    }
    
    public void close() {
        m_Model.setListener(null);
        Gdx.input.setInputProcessor(null);
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
    public void onAccelarateStart() {
        mAcceleration = true;
    }
    @Override
    public void onAccelarateFinish() {
        mAcceleration = false;
    }
    
    private void rotate() {
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

    @Override
    public void onTap() {
        if(mState == GameState.GameOver) {
            mParentScreeen.gameOver();
        }else if(mState == GameState.Playing) {
            rotate();
        }
    }
    
    private void continuePlay() {
        if(mState == GameState.Playing) {
            mAcceleration = false;
            m_ActiveObj = m_ObjectFactory.getNextObject();
            int shiftX = m_Config.getFieldBlockWidth()/2 - 1;
            int shiftY = m_Config.getFieldBlockHeight();
            m_ActiveObj.shiftTo(shiftX, shiftY);
            m_FieldView.setActiveObject(m_ActiveObj);
            mPanel.setNextObject(ObjectType.toInt(m_ObjectFactory.getNextObjectType()));
        }
    }
    
    public void updateGame() {
        if(mState == GameState.Playing) {
            process();
        }
    }
    
    private void process() {
        float speed = m_Speed;
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
        mPanel.newLevel(level);
        m_Speed += m_Config.mSpeedStep;
    }
    
    public void onScoreChange(int score) {
        mPanel.newScore(score);
    }
    
    @Override
    public void onGameOver() {
        mState = GameState.GameOver;
    }
    
    public void draw(SpriteBatch batch) {
        if(mState != GameState.GameOver) {
            m_FieldView.draw(batch);
        } else {
            m_FieldView.drawGameOver(batch);
        }
    }
    
    public void updatePanel() {
       mPanel.update();
    }
    
    public void dispose() {
        mPanel.dispose();
        m_FieldView.dispose();
        m_ObjectFactory.dispose();
    }
}