package com.spc.mygame.model;


import java.util.Observable;

/**
 * 观察者
 */
public class TestModel extends Observable {
    public Object object;

    private static TestModel mSingleton = null;

    private TestModel() {
    }

    public static TestModel instance() {
        if (mSingleton == null) {
            synchronized (TestModel.class) {
                if (mSingleton == null) {
                    mSingleton = new TestModel();
                }
            }
        }
        return mSingleton;
    }

    public TestModel setObject(Object i) {
        object = i;
        return mSingleton;
    }

    public void update() {
        setChanged();
        notifyObservers();
    }
}
