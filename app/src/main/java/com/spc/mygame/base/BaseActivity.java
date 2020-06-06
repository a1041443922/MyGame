package com.spc.mygame.base;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


import java.util.Observable;
import java.util.Observer;

public abstract class BaseActivity extends AppCompatActivity implements Observer {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(resId());
        afterView();
    }

    public abstract int resId();


    public abstract void afterView();

    @Override
    public void update(Observable o, Object arg) {

    }
}
