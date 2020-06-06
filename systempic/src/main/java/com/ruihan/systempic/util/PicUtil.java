package com.ruihan.systempic.util;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * 图片工具 包括开相机 图片
 */
public class PicUtil {

    public static final int OPEN_CAMARA = 101;
    public static final int OPEN_ALBUM = 102;
    //    public static final String CAMARA_PATH_1 = "/storage/emulated/0/Pictures/pic1.jpg";
//    public static final String CAMARA_PATH_2 = "/storage/emulated/0/Pictures/pic2.jpg";
    public static File tempFile;

    public static void openCamara(AppCompatActivity context) {//用于保存调用相机拍照后所生成的文件
        tempFile = new File(Environment.getExternalStorageDirectory().getPath(), System.currentTimeMillis() + ".jpg");
        //跳转到调用系统相机
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //判断版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {   //如果在Android7.0以上,使用FileProvider获取Uri
            openCameraIntent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            String filePro=context.getPackageName()+".FileProvider";
            Uri contentUri = FileProvider.getUriForFile(context, filePro, tempFile);
            openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
        } else {    //否则使用Uri.fromFile(file)方法获取Uri
            openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        }
        context.startActivityForResult(openCameraIntent, OPEN_CAMARA);
    }

    /**
     * 从相册获取图片
     */
    public static void openAlbum(AppCompatActivity context) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        context.startActivityForResult(photoPickerIntent, OPEN_ALBUM);
    }


    /**
     * uri 获取绝对路径
     * @param context
     * @param uri
     * @return
     */
    public static String getFilePathForN(Context context, Uri uri) {
        try {
            Cursor returnCursor = context.getContentResolver().query(uri, null, null, null, null);
            int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            returnCursor.moveToFirst();
            String name = (returnCursor.getString(nameIndex));
            File file = new File(context.getFilesDir(), name);
            InputStream inputStream = context.getContentResolver().openInputStream(uri);
            FileOutputStream outputStream = new FileOutputStream(file);
            int read = 0;
            int maxBufferSize = 1 * 1024 * 1024;
            int bytesAvailable = inputStream.available();
            int bufferSize = Math.min(bytesAvailable, maxBufferSize);
            final byte[] buffers = new byte[bufferSize];
            while ((read = inputStream.read(buffers)) != -1) {
                outputStream.write(buffers, 0, read);
            }
            returnCursor.close();
            inputStream.close();
            outputStream.close();
            return file.getPath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
