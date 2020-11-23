# QRCodeScanDemo
Android 关于二维码扫描的 demo，可以自定义扫描界面样式。不同的分支使用了不同的方式来实现扫描功能。下面分别总结各自的特点和接入方式。

### master 分支

1. 导入依赖

```groovy
implementation 'com.journeyapps:zxing-android-embedded:3.5.0'
```

2. 自定义二维码样式类 CustomViewfinderView 

   让该类继承自 ViewfinderView。在这个类里面可以自定义扫描框的样式。具体类看源码。

3. 定义扫描 Activity -> CustomCaptureActivity

   该类继承自 Activity，里面包含了条形码扫描管理器（CaptureManager）、条形码扫描视图（CustomViewfinderView ）、权限处理回调、按键回调。具体看源码类 CustomCaptureActivity。在该类的布局文件中，自定义与业务相关的扫码 UI。记得在 AndroidMainfest.xml 中注册它。

   

   ```xml
   <?xml version="1.0" encoding="utf-8"?>
   <FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
       xmlns:app="http://schemas.android.com/apk/res-auto"
       android:layout_width="match_parent"
       android:layout_height="match_parent">
   
       <com.journeyapps.barcodescanner.DecoratedBarcodeView
           android:id="@+id/zxing_barcode_scanner"
           android:layout_width="match_parent"
           android:layout_height="match_parent"
           app:zxing_preview_scaling_strategy="centerCrop"
           app:zxing_use_texture_view="true"
           app:zxing_scanner_layout="@layout/custom_barcode_scanner"
           app:zxing_framing_rect_width="200dp"
           app:zxing_framing_rect_height="200dp"/>
   
       <androidx.appcompat.widget.Toolbar
           android:id="@+id/toolbar"
           android:layout_width="match_parent"
           android:layout_height="wrap_content"
           android:minHeight="?attr/actionBarSize"
           android:fitsSystemWindows="true"
           app:theme="@style/ThemeOverlay.AppCompat.Dark.ActionBar"
           app:popupTheme="@style/ThemeOverlay.AppCompat.Light"/>
   
   </FrameLayout>
   ```

   这里使用 app:zxing_scanner_layout = "@layout/custom_barcode_scanner" 引入扫描框的样式：

   ```xml
   <?xml version="1.0" encoding="utf-8"?>
   <FrameLayout
       xmlns:android="http://schemas.android.com/apk/res/android"
       android:layout_width="match_parent"
       android:layout_height="match_parent">
   
       <com.journeyapps.barcodescanner.BarcodeView
           android:layout_width="match_parent"
           android:layout_height="match_parent"
           android:id="@+id/zxing_barcode_surface"/>
   
       <com.xzy.scan.CustomViewfinderView
           android:layout_width="match_parent"
           android:layout_height="match_parent"
           android:id="@+id/zxing_viewfinder_view"/>
   
       <TextView android:id="@+id/zxing_status_view"
           android:layout_width="wrap_content"
           android:layout_height="wrap_content"
           android:layout_gravity="center"
           android:layout_marginTop="120dp"
           android:background="@color/zxing_transparent"
           android:text="@string/zxing_msg_default_status"
           android:textColor="@color/zxing_status_text"/>
   
   </FrameLayout>
   ```

4. 跳转到扫描页面

   从 MainActivity  -> CustomCaptureActivity 的启动方式：

   ```java
    IntentIntegrator intentIntegrator = new IntentIntegrator(MainActivity.this);
    // 设置自定义扫描Activity
    intentIntegrator.setCaptureActivity(CustomCaptureActivity.class);
    intentIntegrator.initiateScan();
   ```

5. 处理扫描结果回调

   如果是从 MainActivity  -> CustomCaptureActivity，那么需要在 MainActivity  中处理回调，重写 onActivityResult 方法。

   ```java
   	@Override
       protected void onActivityResult(int requestCode, int resultCode, Intent data) {
           // 获取解析结果
           IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
           if (result != null) {
               if (result.getContents() == null) {
                   Toast.makeText(this, "取消扫描", Toast.LENGTH_LONG).show();
               } else {
                   Toast.makeText(this, "扫描内容:" + result.getContents(), Toast.LENGTH_LONG).show();
               }
           } else {
               super.onActivityResult(requestCode, resultCode, data);
           }
       }
   ```

 CustomCaptureActivity 在扫描获取到内容后，自动关闭。

**如果获取到扫描内容后，手动控制扫描页面的关闭，还需要在扫描页面做业务逻辑交互的，可以使用下面这个库。**

[ZXing 精简版，扫码识别速度快如微信](https://github.com/jenly1314/ZXingLite)

# 参考

1. [Android进阶 - 二维码扫描](https://www.jianshu.com/p/b85812b6f7c1)