package com.xiwu.openglsum.glsurfaceview;

import android.content.Context;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import com.xiwu.openglsum.object.Circle;
import com.xiwu.openglsum.object.SixPointedStar;
import com.xiwu.openglsum.object.Triangle;
import com.xiwu.openglsum.utils.MatrixState;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class GraphicalSurfaceView extends GLSurfaceView {
    final float ANGLE_SPAN = 0.375f;

    SceneRenderer mRenderer;//自定义渲染器的引用

    public GraphicalSurfaceView(Context context) {
        super(context);
        this.setEGLContextClientVersion(3);
        mRenderer = new SceneRenderer();
        this.setRenderer(mRenderer);
        this.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }

    private class SceneRenderer implements GLSurfaceView.Renderer {
        Triangle triangle;
        Circle circle;
        SixPointedStar sixPointedStar;

        public void onDrawFrame(GL10 gl) {
            //清除深度缓冲与颜色缓冲
            GLES30.glClear(GLES30.GL_DEPTH_BUFFER_BIT | GLES30.GL_COLOR_BUFFER_BIT);
            //绘制三角形对象
            triangle.drawSelf();
            //绘制圆对象
            circle.drawSelf();
            //sixPointedStar.drawSelf();
        }

        public void onSurfaceChanged(GL10 gl, int width, int height) {
            //设置视窗大小及位置 
            GLES30.glViewport(0, 0, width, height);
            //计算GLSurfaceView的宽高比
            float ratio = (float) width / height;
            //调用此方法计算产生透视投影矩阵
            // Matrix.frustumM(Triangle.mProjMatrix, 0, -ratio, ratio, -1, 1, 1, 10);
            //调用此方法产生摄像机9参数位置矩阵
            // Matrix.setLookAtM(Triangle.mVMatrix, 0, 0, 0, 3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

            //设置透视投影
            MatrixState.setProjectFrustum(-ratio * 0.4f, ratio * 0.4f, -1 * 0.4f, 1 * 0.4f, 1, 50);

            //调用此方法产生摄像机矩阵
            MatrixState.setCamera(0, 0, 6, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

        }

        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            //设置屏幕背景色RGBA
            GLES30.glClearColor(0, 0, 0, 1.0f);
            //创建三角形对象
            triangle = new Triangle(GraphicalSurfaceView.this);
            //创建圆对象
            circle = new Circle(GraphicalSurfaceView.this);
            sixPointedStar = new SixPointedStar(GraphicalSurfaceView.this, 0.4f, 1.0f, 0, new float[]{1f, 0f, 0.1f});
            //打开深度检测
            GLES30.glEnable(GLES30.GL_DEPTH_TEST);
        }
    }

}