package com.example.myapplication.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

@SuppressLint("AppCompatCustomView")
public class HeadImageView extends ImageView {
    public HeadImageView(Context context) {
        super(context);
    }

    public HeadImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HeadImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable drawable = getDrawable();
        Bitmap b = ((BitmapDrawable) drawable).getBitmap();
        canvas.save();
        Path path = new Path();
        path.addCircle(300, 300, 100, Path.Direction.CCW);
        //裁剪圆形区域，也就是说之后绘制的内容只有在圆形区域内的内容才会显示出来
        canvas.clipPath(path);
        canvas.drawBitmap(b, 0, 0, null);

        canvas.restore();
    }
}
