# MyGame
自己的资源包


引用 gradle
allprojects {
    repositories {
       ...
        maven { url 'https://jitpack.io' }
    }
}


 implementation 'com.github.a1041443922:MyGame:v102'
 
1、转菊花加载LoadingDialog
    new LoadingDialog(this).show(); dismiss；
2、调用系统相册
     String path=Environment.getExternalStorageDirectory() + "/ruihan/pics/";//图片保存路径主要用于相机获取
     PicGetter.instance().getPicFromAlbum(this);//相册获取
     PicGetter.instance().getPicFromCamara(this, path);//相机获取
     @Override
     protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        File file = PicGetter.instance().getPic(this, requestCode, resultCode, data);
        Toast.makeText(this, file.getPath(), Toast.LENGTH_SHORT).show();
     }

     //权限获取监听
     @Override
     public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (PicGetter.instance().permitionSet(this, path, requestCode, grantResults)) return;
            Toast.makeText(this, "打开权限", Toast.LENGTH_SHORT).show();
        }
     }