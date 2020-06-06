package com.ruihan.systempic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.ruihan.systempic.util.PicUtil;

import java.io.File;

import static android.app.Activity.RESULT_OK;
import static com.ruihan.systempic.util.PicUtil.tempFile;

public class PicGetter {

    public static final String PIC_PATH = Environment.getExternalStorageDirectory() + "/ruihan/pics/";
    private volatile static PicGetter instance = null;

    public static PicGetter instance() {
        if (instance == null) {
            synchronized (PicGetter.class) {
                if (instance == null) {
                    instance = new PicGetter();
                }
            }

        }
        return instance;
    }

    /**
     * 获取图片
     *
     * @param context
     */
    public void getPicFromCamara(AppCompatActivity context, String path) {
        if (!PicPermissionUtil.checkPermission(context)) {
            PicPermissionUtil.requestPermission(context, PicPermissionUtil.CAMERA_CODE);
            return;
        }
        openCamara(context, path);
    }

    /**
     * 相册获取图片
     *
     * @param context
     */
    public void getPicFromAlbum(AppCompatActivity context) {
        if (!PicPermissionUtil.checkPermission(context)) {
            PicPermissionUtil.requestPermission(context, PicPermissionUtil.STORAGE_CODE);
            return;
        }
        PicUtil.openAlbum(context);
    }

    public File getPic(AppCompatActivity context, int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == PicUtil.OPEN_CAMARA && resultCode == RESULT_OK) {//拍照成功
            //用相机返回的照片去调用剪裁也需要对Uri进行处理
            File imgFile = null;
            Uri contentUri;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                String filePro=context.getPackageName()+".FileProvider";
                contentUri = FileProvider.getUriForFile(context, filePro, tempFile);
                imgFile = new File(PicUtil.getFilePathForN(context, contentUri));
            } else {
                contentUri = Uri.fromFile(tempFile);
                imgFile = new File(contentUri.getPath());
            }
            return imgFile;
        }
        if (requestCode == PicUtil.OPEN_ALBUM && resultCode == RESULT_OK) {//拍照成功
            Uri uri = data.getData();
            if (uri == null) return null;
            return new File(PicUtil.getFilePathForN(context, uri));
        }
        return null;
    }

    public boolean permitionSet(AppCompatActivity context, String path, int requestCode, @NonNull int[] grantResults) {
        if (requestCode == PicPermissionUtil.CAMERA_CODE && PicPermissionUtil.isOpenPermission(context, grantResults)) {
            openCamara(context, path);
            return true;
        }
        if (requestCode == PicPermissionUtil.STORAGE_CODE && PicPermissionUtil.isOpenPermission(context, grantResults)) {
            PicUtil.openAlbum(context);
            return true;
        }
        return false;
    }

    /**
     * 开相机
     *
     * @param context
     * @param path
     */
    private void openCamara(AppCompatActivity context, String path) {
        createFile(path);
        PicUtil.openCamara(context);
    }

    /**
     * 路径创建下
     *
     * @param picPath
     */
    private void createFile(String picPath) {
        File path = new File(picPath);
        if (!path.exists()) {
            path.mkdirs();
        }
    }
}
