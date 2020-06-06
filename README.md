# MyGame
自己的资源包


引用 gradle
allprojects {
    repositories {
       ...
        maven { url 'https://jitpack.io' }
    }
}


 implementation 'com.github.a1041443922:MyGame:v1.0.0'
 
 1、转菊花加载LoadingDialog
 new LoadingDialog(this).show(); dismiss；
