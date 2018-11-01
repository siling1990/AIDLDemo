package com.stone.demoandroid.animation;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class MyRotateAnimation extends Animation {

    private final float mFromDegree;
    private final float mToDegree;
    private final float mCenterX;
    private final float mCenterY;
    private final float mDepthZ;
    private final boolean mReverse;
    private Camera mCamera;

    public MyRotateAnimation(float mFromDegree, float mToDegree, float mCenterX, float mCenterY,
                             float mDepthZ, boolean mReverse, Camera mCamera) {
        this.mFromDegree = mFromDegree;
        this.mToDegree = mToDegree;
        this.mCenterX = mCenterX;
        this.mCenterY = mCenterY;
        this.mDepthZ = mDepthZ;
        this.mReverse = mReverse;
        this.mCamera = mCamera;
    }
    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        mCamera = new Camera();//初始化镜头
    }
    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        final float degree = mFromDegree+(mToDegree-mFromDegree)*interpolatedTime;

        final Matrix matrix = t.getMatrix();
        mCamera.save();

        if(mReverse){//是否反转
            mCamera.translate(0.0f,0.0f,mDepthZ*interpolatedTime);
        }else{
            mCamera.translate(0.0f,0.0f,-mDepthZ*interpolatedTime);
        }
        mCamera.rotateY(degree);//Y轴旋转的角度
        mCamera.getMatrix(matrix);
        mCamera.restore();
        matrix.preTranslate(-mCenterX,-mCenterY);
        matrix.postTranslate(mCenterX,mCenterY);
    }
}
