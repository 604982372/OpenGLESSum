package com.xiwu.openglsum.object;

import android.annotation.SuppressLint;
import android.opengl.GLES30;
import android.opengl.Matrix;

import com.xiwu.openglsum.glsurfaceview.GraphicalSurfaceView;
import com.xiwu.openglsum.utils.MatrixState;
import com.xiwu.openglsum.utils.ShaderUtil;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static com.xiwu.openglsum.object.Contants.UNIT_SIZE;

//矩形
public class Rectangle {

    int mProgram;//自定义渲染管线程序id
    int muMVPMatrixHandle;//总变换矩阵引用
    int maPositionHandle; //顶点位置属性引用
    int maColorHandle; //顶点颜色属性引用
    String mVertexShader;//顶点着色器代码脚本
    String mFragmentShader;//片元着色器代码脚本
    static float[] mMMatrix = new float[16];//具体物体的移动旋转矩阵，包括旋转、平移、缩放

    FloatBuffer mVertexBuffer;//顶点坐标数据缓冲
    FloatBuffer mColorBuffer;//顶点着色数据缓冲
    int vCount = 0;
    float xAngle = 0;//绕x轴旋转的角度

    public Rectangle(GraphicalSurfaceView mv) {
        //调用初始化顶点数据的initVertexData方法
        initVertexData();
        //调用初始化着色器的intShader方法
        initShader(mv);
    }

    public void initVertexData()//初始化顶点数据的方法
    {
        //顶点坐标数据的初始化
        vCount = 4;
        float vertices[] = new float[]//顶点坐标数组
                {
                        -1 * UNIT_SIZE, 1 * UNIT_SIZE, 0,
                        -1 * UNIT_SIZE, -1 * UNIT_SIZE, 0,
                        1 * UNIT_SIZE, 1 * UNIT_SIZE, 0,
                        1 * UNIT_SIZE, -1 * UNIT_SIZE, 0,
                };

        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
        vbb.order(ByteOrder.nativeOrder());//设置字节顺序为本地操作系统顺序
        mVertexBuffer = vbb.asFloatBuffer();//转换为浮点(Float)型缓冲
        mVertexBuffer.put(vertices);//在缓冲区内写入数据
        mVertexBuffer.position(0);//设置缓冲区起始位置


        float colors[] = new float[]//顶点颜色数组
                {
                        0.8f, 0.5f, 0, 0,//白色
                        0.8f, 0.5f, 0, 0,//蓝
                        0.8f, 0.5f, 0, 0,//绿
                        0.8f, 0.5f, 0, 0//绿
                };

        ByteBuffer cbb = ByteBuffer.allocateDirect(colors.length * 4);
        cbb.order(ByteOrder.nativeOrder());//设置字节顺序为本地操作系统顺序
        mColorBuffer = cbb.asFloatBuffer();//转换为浮点(Float)型缓冲
        mColorBuffer.put(colors);//在缓冲区内写入数据
        mColorBuffer.position(0);//设置缓冲区起始位置
    }

    //初始化着色器的方法
    @SuppressLint("NewApi")
    public void initShader(GraphicalSurfaceView mv) {
        //加载顶点着色器的脚本内容
        mVertexShader = ShaderUtil.loadFromAssetsFile("vertex.sh", mv.getResources());
        //加载片元着色器的脚本内容
        mFragmentShader = ShaderUtil.loadFromAssetsFile("frag.sh", mv.getResources());
        //基于顶点着色器与片元着色器创建程序
        mProgram = ShaderUtil.createProgram(mVertexShader, mFragmentShader);
        //获取程序中顶点位置属性引用
        maPositionHandle = GLES30.glGetAttribLocation(mProgram, "aPosition");
        //获取程序中顶点颜色属性引用
        maColorHandle = GLES30.glGetAttribLocation(mProgram, "aColor");
        //获取程序中总变换矩阵引用
        muMVPMatrixHandle = GLES30.glGetUniformLocation(mProgram, "uMVPMatrix");
    }

    @SuppressLint("NewApi")
    public void drawSelf() {
        //指定使用某套shader程序
        GLES30.glUseProgram(mProgram);
        //初始化变换矩阵
        Matrix.setRotateM(mMMatrix, 0, 0, 0, 1, 0);
        //设置沿Z轴正向位移1
        Matrix.translateM(mMMatrix, 0, 0, 0, 1);
        //设置绕x轴旋转
        Matrix.rotateM(mMMatrix, 0, xAngle, 1, 0, 0);
        //将变换矩阵传入渲染管线
        GLES30.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, MatrixState.getFinalMatrix(mMMatrix), 0);
        //将顶点位置数据传送进渲染管线
        GLES30.glVertexAttribPointer(
                maPositionHandle,
                3,
                GLES30.GL_FLOAT,
                false,
                3 * 4,
                mVertexBuffer
        );
        //将顶点颜色数据传送进渲染管线
        GLES30.glVertexAttribPointer
                (
                        maColorHandle,
                        4,
                        GLES30.GL_FLOAT,
                        false,
                        4 * 4,
                        mColorBuffer
                );
        GLES30.glEnableVertexAttribArray(maPositionHandle);//启用顶点位置数据
        GLES30.glEnableVertexAttribArray(maColorHandle);//启用顶点着色数据
        //绘制矩形
        GLES30.glDrawArrays(GLES30.GL_TRIANGLE_STRIP, 0, vCount);
    }
}