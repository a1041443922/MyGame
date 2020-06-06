package com.spc.mygame;

import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ruihan.loading.LoadingDialog;
import com.spc.mygame.base.BaseActivity;
import com.spc.mygame.model.MsgDetail;
import com.spc.mygame.model.TestModel;
import com.spc.mygame.util.HttpUtil;
import com.spc.mygame.view.BannerAdapter;
import com.spc.mygame.view.CardTransformer;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private TextView changeTv;
    private ViewPager mViewpager;
    private BannerAdapter mBannerAdapter;
    //向导界面的图片
    private int[] mPics = new int[]{R.mipmap.huan1, R.mipmap.huan2, R.mipmap.huan3, R.mipmap.huan4, R.mipmap.huan5};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int resId() {
        return R.layout.activity_main;
    }

    @Override
    public void afterView() {
        changeTv = findViewById(R.id.changeTv);
        changeTv.setOnClickListener(this);
        mViewpager = findViewById(R.id.viewPager);

        TestModel.instance().addObserver(this);
        String url = "https://eapi.sdrhup.com/index/message/detail";
        Map<String, String> params = new HashMap<>();
        params.put("mid", "24");
        HttpUtil.sendHttpPost(this, url, params, MsgDetail.class);
        initViewPager();
        new LoadingDialog(this).show();
    }
    private void initViewPager(){
        mBannerAdapter = new BannerAdapter(this, mPics);
        mViewpager.setAdapter(mBannerAdapter);
        mViewpager.setPageMargin(10);
        mViewpager.setOffscreenPageLimit(mPics.length);
        mViewpager.setClipChildren(false);
        mViewpager.setPageTransformer(true, new CardTransformer());
        mViewpager.setCurrentItem(1);
    }
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this, LoadingActivity.class);
        startActivity(intent);
    }

    @Override
    public void update(Observable o, Object arg) {
        TestModel testModel = (TestModel) o;
        if (testModel.object instanceof MsgDetail) {
            MsgDetail msgDetail = (MsgDetail) testModel.object;
            changeTv.setText(msgDetail.linfo.content);
        }
    }
}
