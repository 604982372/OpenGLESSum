package com.xiwu.openglsum.activity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.xiwu.openglsum.R;
import com.xiwu.openglsum.glsurfaceview.GraphicalSurfaceView;
import com.xiwu.openglsum.object.Circle;

import static com.xiwu.openglsum.object.Contants.CIRCLE;
import static com.xiwu.openglsum.object.Contants.RECTANGLE;
import static com.xiwu.openglsum.object.Contants.TRANGLE;
import static com.xiwu.openglsum.object.Contants.TRAPEZOID;

public class GraphicalActivity extends AppCompatActivity {
    GraphicalSurfaceView mSurfaceView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置为全屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mSurfaceView = new GraphicalSurfaceView(this);//创建MyTDView类的对象
        mSurfaceView.requestFocus();//获取焦点
        mSurfaceView.setFocusableInTouchMode(true);//设置为可触控
        setContentView(R.layout.activity_graphical);
        LinearLayout viewById = findViewById(R.id.ll_sur);
        viewById.addView(mSurfaceView);

        findViewById(R.id.btn_triangle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSurfaceView.setFlag(TRANGLE);
            }
        });

        findViewById(R.id.btn_Rectangle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSurfaceView.setFlag(RECTANGLE);
            }
        });
        findViewById(R.id.btn_Trapezoid).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSurfaceView.setFlag(TRAPEZOID);
            }
        });
        findViewById(R.id.btn_circle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSurfaceView.setFlag(CIRCLE);
            }
        });
    }

    @Override
    public void onResume()//继承Activity后重写的onResume方法
    {
        super.onResume();
        mSurfaceView.onResume();//通过MyTDView类的对象调用onResume方法
    }

    @Override
    public void onPause()//继承Activity后重写的onPause方法
    {
        super.onPause();
        mSurfaceView.onPause();//通过MyTDView类的对象调用onPause方法
    }
}
