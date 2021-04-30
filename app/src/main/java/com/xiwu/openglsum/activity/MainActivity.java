package com.xiwu.openglsum.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.xiwu.openglsum.R;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //平面图形跟几何图形
        findViewById(R.id.btn_graphical).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GraphicalActivity.class);
                startActivity(intent);
            }
        });
        //投影及各种变换
        findViewById(R.id.btn_projection).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProjectionActivity.class);
                startActivity(intent);
            }
        });
        //光照使用
        findViewById(R.id.btn_illumination).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, IlluminationActivity.class);
                startActivity(intent);
            }
        });
        //纹理映射
        findViewById(R.id.btn_texture).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TextureActivity.class);
                startActivity(intent);
            }
        });
        //3D模型加载
        findViewById(R.id.btn_objmodel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ObjModelActivity.class);
                startActivity(intent);
            }
        });
        //纹理映射
        findViewById(R.id.btn_ndk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NdkActivity.class);
                startActivity(intent);
            }
        });

        //相机美颜
        findViewById(R.id.btn_camera_beauty).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CameraBeautyActivity.class);
                startActivity(intent);
            }
        });


    }


}
