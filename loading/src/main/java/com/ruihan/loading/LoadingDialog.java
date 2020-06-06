package com.ruihan.loading;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;


public class LoadingDialog extends ProgressDialog {

    private Context context;
    private ImageView ivAnim;
    private Animation anim;

    public LoadingDialog(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        this.context = context;
        anim = AnimationUtils.loadAnimation(context, R.anim.loading_animation);
        setCanceledOnTouchOutside(false);
    }

    @Override
    public void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        ivAnim.startAnimation(anim);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_anim_loading);
        ivAnim = (ImageView) findViewById(R.id.air_load_iv);
        Window window = this.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.alpha = 0.4f;// 透明度
        lp.dimAmount = 0f;// 黑暗度
        lp.width = getWidth((Activity) context) / 3;
        lp.height = getWidth((Activity) context) / 3;
        window.setAttributes(lp);
    }

    public static int getWidth(Activity context) {
        DisplayMetrics outMetrics = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        int widthPixels = outMetrics.widthPixels;
        int heightPixels = outMetrics.heightPixels;
        return widthPixels;
    }

    @Override
    public void dismiss() {
        // TODO Auto-generated method stub
        super.dismiss();
    }
}
