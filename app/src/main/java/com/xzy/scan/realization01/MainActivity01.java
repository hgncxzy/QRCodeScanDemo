package com.xzy.scan.realization01;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.xzy.scan.R;


/**
 * 使用库 com.journeyapps:zxing-android-embedded:3.5.0 实现的扫描，
 * 扫描到结果后，立即关闭扫描界面。扫描结果在上一个页面处理。
 * @Class: MainActivity
 * @Description: 主界面
 * @Author: xzy
 */
public class MainActivity01 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main01);
        // 开启扫描
        findViewById(R.id.btn).setOnClickListener(v -> {
            IntentIntegrator intentIntegrator = new IntentIntegrator(MainActivity01.this);
            // 设置自定义扫描Activity
            intentIntegrator.setCaptureActivity(CustomCaptureActivity01.class);
            intentIntegrator.initiateScan();
        });
    }


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
}
