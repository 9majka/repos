package com.mygdx.sample.object;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.mygdx.sample.GameConfig;
import com.mygdx.sample.object.GameObject.ObjectType;

public class ObjectFactory {
    private ObjectType m_CurrentType;
    private ObjectType m_NextType;
    private final GameConfig m_Config;
    private Array<GameObject> mObjects;
    
    
    public ObjectFactory(final GameConfig config) {
        m_Config = config;
        init();
    }
    
    private void init() {
        mObjects = new Array<GameObject>();
        for(ObjectType type: ObjectType.values()) {
            mObjects.add(createObject(type));
        }

        int rand = MathUtils.random(0, ObjectType.toInt(ObjectType.OT_MAXObject));
        m_CurrentType = ObjectType.fromOrdinal(rand);
        rand = MathUtils.random(0, ObjectType.toInt(ObjectType.OT_MAXObject));
        m_NextType = ObjectType.fromOrdinal(rand);
    }
    
    private void generateNext() {
        m_CurrentType = m_NextType;
        int rand = MathUtils.random(0, ObjectType.toInt(ObjectType.OT_MAXObject) - 1);
        m_NextType = ObjectType.fromOrdinal(rand);
    }
    
    private GameObject createObject(ObjectType type) {
        //type = ObjectType.OT_STObject;
        float blockSize = m_Config.mCellUnitHeight;
        switch (type) {
            case OT_TObject:
                return new TObject(blockSize);
            case OT_GLObject:
                return new GLObject(blockSize);
            case OT_GRObject:
                return new GRObject(blockSize);
            case OT_SQObject:
                return new SQObject(blockSize);
            case OT_ZLObject:
                return new ZLObject(blockSize);
            case OT_ZRObject:
                return new ZRObject(blockSize);
            case OT_STObject:
                return new STObject(blockSize);
                default:
                return new TObject(blockSize);
        }
    } 
    
    public GameObject getNextObject() {
        generateNext();
        GameObject object = mObjects.get(ObjectType.toInt(m_CurrentType));//createObject();
        return object;
    }
    
    public ObjectType getNextObjectType() {
        return m_NextType;
    }
    
    public void dispose() {
        //TODO dispose created textures
    }
}