package com.xiwu.openglsum.natives;

import android.content.res.AssetManager;

public class TriangleLib {
    static {
        System.loadLibrary("triangle-lib");
    }

    //读取Asset
    public static native void readShaderFile(AssetManager assetManager);

    //初始化本地GLES
    public static native boolean init();

    //为本地GLES设置宽和高
    public static native void resize(int width, int height);

    //绘制三角形
    public static native void drawTrangle();
}
