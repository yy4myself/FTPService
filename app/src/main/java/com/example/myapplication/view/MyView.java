package com.example.myapplication.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

public class MyView extends View {

    private static final String TAG = "MyView";

    private Paint mPaint;

    private Path mBezierPath;

    private ValueAnimator translationAnimator;
    private ValueAnimator scaleAnimator;

    public MyView(Context context) {
        this(context, null);
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);

        mBezierPath = new Path();

        circleCenter1 = new Point();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mBezierPath.reset();
        mPaint.setColor(Color.GRAY);
        RectF rectF = new RectF(0, 0, width / 2, height);
        mBezierPath.arcTo(rectF, 90, 180);
        mBezierPath.moveTo(width / 4, 0);
        mBezierPath.lineTo(width / 4 * 3, 0);
        mBezierPath.lineTo(width / 4 * 3, height);
        mBezierPath.lineTo(width / 4, height);
        mBezierPath.moveTo(width / 4, 0);
        rectF = new RectF(width / 2, 0, width, height);
        mBezierPath.arcTo(rectF, -90, 180);
        //绘制起始点、控制点、终点的连线
        canvas.drawPath(mBezierPath, mPaint);

        mPaint.setColor(Color.RED);
        canvas.drawCircle(circleCenter1.x, circleCenter1.y, radius, mPaint);
    }

    private int width, height;

    private Point circleCenter1, circleCenter2;
    private float radius;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        Log.d(TAG, "onMeasure: widthMode = " + widthMode + ";widthSize = " + widthSize);
        Log.d(TAG, "onMeasure: heightMode = " + heightMode + ";heightSize = " + heightSize);

        if (widthSize < heightSize * 2) {
            heightSize = widthSize / 2;
        } else {
            widthSize = heightSize * 2;
        }
        setMeasuredDimension(widthSize, heightSize);
        width = widthSize;
        height = heightSize;
        radius = height / 3;
        circleCenter1.set(width / 4, height / 2);
    }

    public void start() {
        translationAnimator = ValueAnimator.ofFloat(width / 4, width / 4 * 3);
        translationAnimator.setInterpolator(new LinearInterpolator());
        translationAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float x = (float) valueAnimator.getAnimatedValue();
                circleCenter1.set((int) x, height / 2);
                radius = (height / 3) + (x * 2 / width) * (height / 2 - height / 3);
                if (radius > height / 2) radius = height / 2;
                invalidate();
            }
        });
        translationAnimator.setRepeatCount(0);
        translationAnimator.setDuration(1000);
        translationAnimator.start();

        translationAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                Log.d(TAG, "onAnimationStart");
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                Log.d(TAG, "onAnimationEnd");
            }

            @Override
            public void onAnimationCancel(Animator animator) {

                Log.d(TAG, "onAnimationCancel");
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
                Log.d(TAG, "onAnimationRepeat");
            }
        });
    }
}
