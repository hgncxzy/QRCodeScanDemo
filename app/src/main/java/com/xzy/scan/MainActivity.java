package com.xzy.scan;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.xzy.scan.realization01.MainActivity01;
import com.xzy.scan.realization02.MainActivity02;


/**
 * 使用库 com.journeyapps:zxing-android-embedded:3.5.0 实现的扫描，
 * 扫描到结果后，立即关闭扫描界面。扫描结果在上一个页面处理。
 * @Class: MainActivity
 * @Description: 主界面
 * @Author: xzy
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 开启扫描方式一
        findViewById(R.id.btn01).setOnClickListener(v -> startActivity(new Intent(MainActivity.this, MainActivity01.class)));

        // 开启扫描方式二
        findViewById(R.id.btn02).setOnClickListener(v -> startActivity(new Intent(MainActivity.this, MainActivity02.class)));
    }
}
