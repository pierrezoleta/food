package com.example.yttest;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;

import androidx.core.content.ContextCompat;
import com.google.android.material.progressindicator.CircularProgressIndicator;

public class GradientCircularProgressIndicator extends CircularProgressIndicator {

    private Paint gradientPaint;
    private static final int START_ANGLE = -90; // Start angle for the progress (top)
    private RectF rectF;

    public GradientCircularProgressIndicator(Context context) {
        super(context);
        init();
    }

    public GradientCircularProgressIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GradientCircularProgressIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        gradientPaint = new Paint();
        gradientPaint.setStyle(Paint.Style.STROKE);
        gradientPaint.setAntiAlias(true);

        rectF = new RectF(); // Initialize the rectF
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        float padding = getTrackThickness() / 2.0f;
        rectF.set(padding, padding, w - padding, h - padding);

        Shader shader = new LinearGradient(
                0, 0, w, h,
                ContextCompat.getColor(getContext(), R.color.gradient_start),
                ContextCompat.getColor(getContext(), R.color.gradient_end),
                Shader.TileMode.CLAMP
        );
        gradientPaint.setShader(shader);
        gradientPaint.setStrokeWidth(getTrackThickness());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d("GradientProgress", "onDraw called");

        float sweepAngle = getProgress() * 360f / getMax();
        canvas.drawArc(rectF, START_ANGLE, sweepAngle, false, gradientPaint);
    }


}
