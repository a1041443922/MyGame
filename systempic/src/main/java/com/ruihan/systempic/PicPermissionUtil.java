package com.ruihan.systempic;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

/**
 * 权限请求
 */
public class PicPermissionUtil {

    public static final int CAMERA_CODE = 1001;

    public static final int STORAGE_CODE = 1002;

    public static final String[] STORAGES = {
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
    };


    /**
     * 获取权限
     *
     * @param context
     * @param requestCode
     */
    public static void requestPermission(Activity context, int requestCode) {
        ActivityCompat.requestPermissions(context, STORAGES, requestCode);
    }


    /**
     * 权限请求
     *
     * @return
     */
    public static boolean checkPermission(Context context) {
        for (String permiss : STORAGES) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(context, permiss) != PackageManager.PERMISSION_GRANTED)
                return false;
        }
        return true;
    }

    public static boolean isOpenPermission(Context context, int[] grantResults) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
