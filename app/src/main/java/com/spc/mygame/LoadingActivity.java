package com.spc.mygame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.spc.mygame.base.BaseActivity;
import com.spc.mygame.model.MsgDetail;
import com.spc.mygame.model.TestModel;
import com.spc.mygame.model.Version;
import com.spc.mygame.util.HttpUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;


/**
 * 观察者测试
 */
public class LoadingActivity extends BaseActivity {
    private TextView changeTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public int resId() {
        return R.layout.activity_loading;
    }

    @Override
    public void afterView() {
        changeTv = findViewById(R.id.changeTv);

        TestModel.instance().addObserver(this);
        String url = "https://eapi.sdrhup.com/index/index/update";
        HttpUtil.sendHttpPost(this, url, null, Version.class);
    }

    public void onClick(View view) {
        Intent intent = new Intent(LoadingActivity.this, AddActivity.class);
        startActivity(intent);
    }

    @Override
    public void update(Observable o, Object arg) {
        TestModel testModel = (TestModel) o;
        if (testModel.object instanceof Version) {
            Version version= (Version) testModel.object;
            changeTv.setText(version.url+version.version);
        }
    }
}
