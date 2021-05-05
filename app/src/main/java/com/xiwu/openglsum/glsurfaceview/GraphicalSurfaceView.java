package com.xiwu.openglsum.glsurfaceview;

import android.content.Context;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import com.xiwu.openglsum.object.Circle;
import com.xiwu.openglsum.object.Rectangle;
import com.xiwu.openglsum.object.SixPointedStar;
import com.xiwu.openglsum.object.Trapezoid;
import com.xiwu.openglsum.object.Triangle;
import com.xiwu.openglsum.utils.MatrixState;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.GL_CULL_FACE;
import static android.opengl.GLES20.GL_CULL_FACE_MODE;
import static android.opengl.GLES20.GL_DEPTH_BUFFER_BIT;
import static android.opengl.GLES20.GL_DEPTH_TEST;
import static android.opengl.GLES20.GL_DST_ALPHA;
import static android.opengl.GLES20.GL_ONE;
import static android.opengl.GLES20.GL_ONE_MINUS_CONSTANT_COLOR;
import static android.opengl.GLES20.GL_ONE_MINUS_DST_COLOR;
import static android.opengl.GLES20.GL_ONE_MINUS_SRC_COLOR;
import static android.opengl.GLES20.GL_SRC_ALPHA;
import static android.opengl.GLES20.GL_SRC_COLOR;
import static android.opengl.GLES20.GL_ZERO;
import static android.opengl.GLES20.glDisable;
import static android.opengl.GLES20.glEnable;
import static com.xiwu.openglsum.object.Contants.CIRCLE;
import static com.xiwu.openglsum.object.Contants.RECTANGLE;
import static com.xiwu.openglsum.object.Contants.TRANGLE;
import static com.xiwu.openglsum.object.Contants.TRAPEZOID;
import static javax.microedition.khronos.opengles.GL10.GL_DST_COLOR;
import static javax.microedition.khronos.opengles.GL10.GL_ONE_MINUS_DST_ALPHA;
import static javax.microedition.khronos.opengles.GL10.GL_ONE_MINUS_SRC_ALPHA;
import static javax.microedition.khronos.opengles.GL10.GL_SRC_ALPHA_SATURATE;

public class GraphicalSurfaceView extends GLSurfaceView {
    final float ANGLE_SPAN = 0.375f;

    SceneRenderer mRenderer;//自定义渲染器的引用
    int flag = TRANGLE;//
    float polygonOffsetFactor = -1.0f;
    float polygonOffsetUnits = -2.0f;

    public GraphicalSurfaceView(Context context) {
        super(context);
        this.setEGLContextClientVersion(3);
        mRenderer = new SceneRenderer();
        this.setRenderer(mRenderer);
        this.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    private class SceneRenderer implements GLSurfaceView.Renderer {
        Triangle triangle;
        Circle circle;
        SixPointedStar sixPointedStar;
        Rectangle rectangle;
        Trapezoid trapezoid;

        public void onDrawFrame(GL10 gl) {
            //清除深度缓冲与颜色缓冲
            GLES30.glClear(GL_DEPTH_BUFFER_BIT | GL_COLOR_BUFFER_BIT);
            glEnable(GL_DEPTH_TEST);//开启深度测试
            GLES30.glEnable(GLES30.GL_POLYGON_OFFSET_FILL); //启用多边形偏移
            GLES30.glPolygonOffset(polygonOffsetFactor, polygonOffsetUnits);//设置偏移参数
            switch (flag) {
                case TRANGLE:
                    //绘制三角形对象
                    triangle.drawSelf();
                    break;
                case RECTANGLE:
                    rectangle.drawSelf();
                    break;
                case TRAPEZOID:
                    trapezoid.drawSelf();
                    break;
                case CIRCLE:
                    //绘制圆对象
                    circle.drawSelf();
                    break;
            }

            GLES30.glDisable(GLES30.GL_POLYGON_OFFSET_FILL); //禁用多边形偏移
            glDisable(GL_DEPTH_TEST);//关闭深度测试
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
            MatrixState.setProjectFrustum(-ratio, ratio, -1, 1, 1, 10);

            //调用此方法产生摄像机矩阵
            MatrixState.setCamera(0, 0, 3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

        }

        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            //设置屏幕背景色RGBA
            GLES30.glClearColor(0, 0, 0, 1.0f);
            //创建三角形对象
            triangle = new Triangle(GraphicalSurfaceView.this);
            //创建圆对象
            circle = new Circle(GraphicalSurfaceView.this);
            rectangle = new Rectangle(GraphicalSurfaceView.this);
            trapezoid = new Trapezoid(GraphicalSurfaceView.this);
            sixPointedStar = new SixPointedStar(GraphicalSurfaceView.this, 0.4f, 1.0f, 0, new float[]{1f, 0f, 0.1f});
            //打开深度检测
            glEnable(GL_DEPTH_TEST);
        }
    }

}