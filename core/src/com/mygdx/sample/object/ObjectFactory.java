package com.mygdx.sample.object;

import com.badlogic.gdx.math.MathUtils;
import com.mygdx.sample.object.Object.ObjectType;

public class ObjectFactory {
    private ObjectType m_CurrentType;
    private ObjectType m_NextType;
    
    public ObjectFactory() {
        init();
    }
    
    private void init() {
        int rand = MathUtils.random(0, ObjectType.toInt(ObjectType.OT_MAXObject));
        m_CurrentType = ObjectType.fromOrdinal(rand);
        rand = MathUtils.random(0, ObjectType.toInt(ObjectType.OT_MAXObject));
        m_NextType = ObjectType.fromOrdinal(rand);
    }
    
    private void generateNext() {
        m_CurrentType = m_NextType;
        int rand = MathUtils.random(0, ObjectType.toInt(ObjectType.OT_MAXObject));
        m_NextType = ObjectType.fromOrdinal(rand);
    }
    
    private Object createObject(ObjectType type) {
        switch (type) {
            case OT_TObject:
                return new TObject();
            case OT_GLObject:
                return new GLObject();
            case OT_GRObject:
                return new GRObject();
            case OT_SQObject:
                return new SQObject();
            case OT_ZLObject:
                return new ZLObject();
            case OT_ZRObject:
                return new ZRObject();
            case OT_STObject:
                return new STObject();
                default:
                return new TObject();
        }
    } 
    
    public Object getNextObject() {
        generateNext();
        Object object = createObject(m_CurrentType);
        return object;
    }
}