package com.xiwu.openglsum.activity;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.xiwu.openglsum.R;
import com.xiwu.openglsum.glsurfaceview.GraphicalSurfaceView;
import com.xiwu.openglsum.glsurfaceview.MySurfaceView;

public class GraphicalActivity extends AppCompatActivity {
    GraphicalSurfaceView mSurfaceView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置为全屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN ,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mSurfaceView = new GraphicalSurfaceView(this);//创建MyTDView类的对象
        mSurfaceView.requestFocus();//获取焦点
        mSurfaceView.setFocusableInTouchMode(true);//设置为可触控
        setContentView(mSurfaceView);
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
