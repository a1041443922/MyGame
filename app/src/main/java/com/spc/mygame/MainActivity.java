package com.spc.mygame;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ruihan.loading.LoadingDialog;
import com.ruihan.systempic.PicGetter;
import com.ruihan.systempic.util.PicUtil;
import com.spc.mygame.base.BaseActivity;
import com.spc.mygame.model.MsgDetail;
import com.spc.mygame.model.TestModel;
import com.spc.mygame.util.HttpUtil;
import com.spc.mygame.view.BannerAdapter;
import com.spc.mygame.view.CardTransformer;

import java.io.File;
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
    }

    private void initViewPager() {
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
//        Intent intent = new Intent(MainActivity.this, LoadingActivity.class);
//        startActivity(intent);

        PicGetter.instance().getPicFromAlbum(this);
//        PicGetter.instance().getPicFromCamara(this, Environment.getExternalStorageDirectory() + "/ruihan/pics/");
    }

    @Override
    public void update(Observable o, Object arg) {
        TestModel testModel = (TestModel) o;
        if (testModel.object instanceof MsgDetail) {
            MsgDetail msgDetail = (MsgDetail) testModel.object;
            changeTv.setText(msgDetail.linfo.content);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        File file = PicGetter.instance().getPic(this, requestCode, resultCode, data);
        Toast.makeText(this, file.getPath(), Toast.LENGTH_SHORT).show();
        Log.d("", "");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (PicGetter.instance().permitionSet(this, "", requestCode, grantResults)) return;
        Toast.makeText(this, "打开权限", Toast.LENGTH_SHORT).show();
    }
}
