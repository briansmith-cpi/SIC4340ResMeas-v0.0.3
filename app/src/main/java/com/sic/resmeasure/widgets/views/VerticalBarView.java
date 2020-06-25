/*
 * Copyright (c) 2015 Silicon Craft Technology Co.,Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sic.resmeasure.widgets.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;


import com.sic.resmeasure.R;
import com.sic.resmeasure.widgets.states.BundleSavedState;

import java.util.Locale;

/**
 * @author Tanawat Hongthai - http://www.sic.co.th/
 * @version 1.0.0
 * @since 3/27/2015
 */
public class VerticalBarView extends View {

    private static final float MAX_RANGE = 1800.0f;
    private static final String BOOLEAN_HAND_INITIALIZED = "boolean_hand_initialized";
    private static final String FLOAT_HAND_POSITION = "float_hand_position";
    private static final String FLOAT_HAND_TARGET = "float_hand_target";
    private static final String FLOAT_HAND_VELOCITY = "float_hand_velocity";
    private static final String FLOAT_HAND_ACCELERATION = "float_hand_acceleration";
    private static final String LONG_LASTEST_HAND_MOVE_TIME = "float_last_hand_move_time";

    // drawing tools
    private RectF linRect;
    private Paint linPaint;
    private Paint linLinePaint;
    private Position linPos;
    private RectF linBufRect;
    private Paint linBufPaint;
    private Paint linBufLinePaint;
    private RectF satRect;
    private Paint satPaint;
    private Paint satLinePaint;
    private Position satPos;
    private RectF empRect;
    private Paint empPaint;
    private Paint vdacCtLinePaint;
    private Position vdacCtLinePos;
    private Paint vdacUpLinePaint;
    private Position vdacUpLinePos;
    private Paint vdacLoLinePaint;
    private Position vdacLoLinePos;
    private Paint zeroLinePaint;
    private Position zeroLinePos;
    private Paint vadcLinePaint;
    private Position vadcLinePos;
    private Paint vdacCtPaint;
    private Paint vdacPaint;
    private String vdacUpTitle = "VDAC + 0.2";
    private String vdacCtTitle = "VDAC";
    private String vdacLoTitle = "VDAC - 0.2";
    private String vadcTitle = "VADC";
    private String zeroTitle = " 0.00 ";
    private Paint rangePaint;
    private String linRangeTitle = "Linear";
    private String satRangeTitle = "Saturation";
    // hand dynamics
    private boolean handInitialized = true;
    private float handPosition = 1000;
    private float handTarget = 0;
    private float handVelocity = 0.0f;
    private float handAcceleration = 0.0f;
    private long lastHandMoveTime = -1L;
    private float valuePosition = -100;
    private float valueTarget = -100;
    private float maxRange = MAX_RANGE;
    private float rangeMinus = 250;
    private float rangePlus = 250;
    private boolean draw = false;
    private boolean drawValue = false;
    private boolean buffer = false;

    public VerticalBarView(Context context) {
        super(context);
        init();
    }

    public VerticalBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        initWithAttrs(attrs, 0, 0);
    }

    public VerticalBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        initWithAttrs(attrs, defStyleAttr, defStyleAttr);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        BundleSavedState savedState = new BundleSavedState(superState);
        Bundle bundle = new Bundle();

        bundle.putBoolean(BOOLEAN_HAND_INITIALIZED, handInitialized);
        bundle.putFloat(FLOAT_HAND_POSITION, handPosition);
        bundle.putFloat(FLOAT_HAND_TARGET, handTarget);
        bundle.putFloat(FLOAT_HAND_VELOCITY, handVelocity);
        bundle.putFloat(FLOAT_HAND_ACCELERATION, handAcceleration);
        bundle.putLong(LONG_LASTEST_HAND_MOVE_TIME, lastHandMoveTime);

        savedState.setBundle(bundle);
        return savedState;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        BundleSavedState savedState = (BundleSavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        Bundle bundle = savedState.getBundle();

        handInitialized = bundle.getBoolean(BOOLEAN_HAND_INITIALIZED);
        handPosition = bundle.getFloat(FLOAT_HAND_POSITION);
        handTarget = bundle.getFloat(FLOAT_HAND_TARGET);
        handVelocity = bundle.getFloat(FLOAT_HAND_VELOCITY);
        handAcceleration = bundle.getFloat(FLOAT_HAND_ACCELERATION);
        lastHandMoveTime = bundle.getLong(LONG_LASTEST_HAND_MOVE_TIME);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        if (draw) {
            setRange();
            drawBar(canvas);
            drawLine(canvas);
            drawTitle(canvas);
            drawValue(canvas);
            if (Build.VERSION.SDK_INT != Build.VERSION_CODES.M) {
                canvas.restore();
            }
            moveHand();
            moveValue();
        } else {
            setEmpRange();
            canvas.drawRect(empRect, empPaint);
            canvas.drawLine(zeroLinePos.startX, zeroLinePos.startY,
                    zeroLinePos.stopX, zeroLinePos.stopY, zeroLinePaint);
            canvas.drawText(zeroTitle, zeroLinePos.textX, zeroLinePos.textY,
                    vdacPaint);
            if (Build.VERSION.SDK_INT != Build.VERSION_CODES.M) {
                canvas.restore();
            }
        }
    }

    private void init() {
        setWillNotDraw(false);
        initDrawingTools();
    }

    private void initWithAttrs(AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.VerticalBarViewStyleable, defStyleAttr, defStyleRes);

        try {
            int range = a.getInteger(R.styleable.VerticalBarViewStyleable_range, 1200);
            boolean buffer = a.getBoolean(R.styleable.VerticalBarViewStyleable_buffer, false);
            float value = a.getFloat(R.styleable.VerticalBarViewStyleable_value, -100);
            int bias = a.getInteger(R.styleable.VerticalBarViewStyleable_bias, 200);

            setVDACText(handPosition);
            setBias(bias);
            setRange(range);
            setBuffer(buffer);
            setValue(value);

            handPosition = range;
        } finally {
            a.recycle();
        }
    }

    private void initDrawingTools() {
        float scale = getResources().getDisplayMetrics().density;

        linRect = new RectF();
        linPaint = new Paint();
        linPaint.setColor(0xff4caf50); // Green 500
        linPaint.setStrokeWidth(0.012f);
        linPaint.setAntiAlias(true);

        linLinePaint = new Paint();
        linLinePaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        linLinePaint.setColor(0xff00c853); // Green A700

        linBufRect = new RectF();
        linBufPaint = new Paint();
        linBufPaint.setColor(0xfff44336); // Red 500
        linBufPaint.setStrokeWidth(0.012f);
        linBufPaint.setAntiAlias(true);

        linBufLinePaint = new Paint();
        linBufLinePaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        linBufLinePaint.setColor(0xff00c853); // Green A700

        linPos = new Position();

        satRect = new RectF();

        satPaint = new Paint();
        satPaint.setColor(0xfff44336); // Red 500
        satPaint.setStrokeWidth(0.012f);
        satPaint.setAntiAlias(true);

        satLinePaint = new Paint();
        satLinePaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        satLinePaint.setColor(0xffd50000); // Red A700

        satPos = new Position();

        empRect = new RectF();

        empPaint = new Paint();
        empPaint.setColor(0x7f607d8b); // Blue Grey 500
        empPaint.setStrokeWidth(0.012f);
        empPaint.setAntiAlias(true);

        vdacCtLinePaint = new Paint();
        vdacCtLinePaint.setAntiAlias(true);
        vdacCtLinePaint.setStyle(Paint.Style.STROKE);
        vdacCtLinePaint.setColor(0xffff5722); // Deep Orange 500
        vdacCtLinePaint.setStrokeWidth(2);

        vdacCtLinePos = new Position();

        vdacUpLinePaint = new Paint();
        vdacUpLinePaint.setAntiAlias(true);
        vdacUpLinePaint.setStyle(Paint.Style.STROKE);
        vdacUpLinePaint.setColor(0xffff9800); // Orange 500
        vdacUpLinePaint.setStrokeWidth(1.5f);

        vdacUpLinePos = new Position();

        vdacLoLinePaint = new Paint();
        vdacLoLinePaint.setAntiAlias(true);
        vdacLoLinePaint.setStyle(Paint.Style.STROKE);
        vdacLoLinePaint.setColor(0xffffc107); // Amber 500
        vdacLoLinePaint.setStrokeWidth(1.5f);

        vdacLoLinePos = new Position();

        zeroLinePaint = new Paint();
        zeroLinePaint.setAntiAlias(true);
        zeroLinePaint.setStyle(Paint.Style.STROKE);
        zeroLinePaint.setColor(0xffffeb3b); // Yellow 500
        zeroLinePaint.setStrokeWidth(1.5f);

        zeroLinePos = new Position();

        vadcLinePaint = new Paint();
        vadcLinePaint.setAntiAlias(true);
        vadcLinePaint.setStyle(Paint.Style.STROKE);
        vadcLinePaint.setColor(0xff2196f3); // Blue 500
        vadcLinePaint.setStrokeWidth(5f);

        vadcLinePos = new Position();

        vdacPaint = new Paint();
        vdacPaint.setColor(Color.WHITE);
        vdacPaint.setAntiAlias(true);
        vdacPaint.setTypeface(Typeface.DEFAULT_BOLD);
        vdacPaint.setTextAlign(Paint.Align.LEFT);
        vdacPaint.setTextSize(scale * 8);

        vdacCtPaint = new Paint();
        vdacCtPaint.setColor(Color.GREEN);
        vdacCtPaint.setAntiAlias(true);
        vdacCtPaint.setTypeface(Typeface.DEFAULT_BOLD);
        vdacCtPaint.setTextAlign(Paint.Align.LEFT);
        vdacCtPaint.setTextSize(scale * 12);

        rangePaint = new Paint();
        rangePaint.setColor(Color.BLACK);
        rangePaint.setAntiAlias(true);
        rangePaint.setTypeface(Typeface.DEFAULT_BOLD);
        rangePaint.setTextAlign(Paint.Align.RIGHT);
        rangePaint.setTextSize(scale * 10);

    }

    private void setEmpRange() {
        int maxHeight = getHeight();
        int maxWidth = getWidth();

        int width = maxWidth / 8;
        int center = maxWidth / 2;

        empRect.set(center, 0, center + width, maxHeight);

        zeroLinePos.startX = (float) (center - (width * 0.5));
        zeroLinePos.startY = maxHeight;
        zeroLinePos.stopX = (float) (center + (width * 1.5));
        zeroLinePos.stopY = maxHeight;
        zeroLinePos.textX = (float) (center + (width * 0.1));
        zeroLinePos.textY = maxHeight - 8;
    }

    private void setRange() {
        int maxHeight = getHeight() - getPaddingTop() - getPaddingBottom();
        int maxWidth = getWidth() - getPaddingLeft() - getPaddingRight();

        int width = maxWidth / 14;
        int center = maxWidth / 2;
        int left = center - width;
        int right = center + width;
        int textCenter = center - 24;
        int textLeft = left - (width / 2);
        int textRight = right + (width / 2);

        int linearRange = maxHeight - (int) (((handPosition - rangeMinus) * maxHeight) / maxRange);
        int saturationRange = maxHeight - (int) (((handPosition + rangePlus) * maxHeight) / maxRange);
        int saturationCenter = maxHeight - (int) (((handPosition) * maxHeight) / maxRange);
        int avgRange = maxHeight - (int) (((valuePosition) * maxHeight) / maxRange);
        int linBuffer = maxHeight;
        if (buffer) {
            zeroTitle = "0.20";
            linBuffer = maxHeight - (int) ((200 * maxHeight) / maxRange);
        } else {
            zeroTitle = "0.00";
        }
        linRect.set(left, linearRange, right, linBuffer);
        linBufRect.set(left, linBuffer, right, maxHeight);

        satRect.set(left, saturationRange, right, linearRange);
        empRect.set(left, 0, right, saturationRange);

        linPos.startX = textLeft;
        linPos.startY = maxHeight - 4;
        linPos.stopX = textLeft;
        linPos.stopY = linearRange + 4;
        linPos.textX = textLeft - 8;
        linPos.textY = linearRange + ((maxHeight - linearRange) / 2) + (rangePaint.getTextSize() / 3);

        satPos.startX = textLeft;
        satPos.startY = linearRange - 4;
        satPos.stopX = textLeft;
        satPos.stopY = saturationRange + 4;
        satPos.textX = textLeft - 8;
        satPos.textY = saturationCenter + (rangePaint.getTextSize() / 3);

        vdacCtLinePos.startX = textLeft;
        vdacCtLinePos.startY = saturationCenter;
        vdacCtLinePos.stopX = textRight;
        vdacCtLinePos.stopY = saturationCenter;
        vdacCtLinePos.textX = textCenter;
        vdacCtLinePos.textY = saturationCenter - 8;

        vdacUpLinePos.startX = textLeft;
        vdacUpLinePos.startY = saturationRange;
        vdacUpLinePos.stopX = textRight;
        vdacUpLinePos.stopY = saturationRange;
        vdacUpLinePos.textX = textCenter;
        vdacUpLinePos.textY = saturationRange - 8;

        if (vdacUpLinePos.textY < vdacPaint.getTextSize()) {
            vdacUpLinePos.textY = vdacPaint.getTextSize() + 8;
        }

        vdacLoLinePos.startX = textLeft;
        vdacLoLinePos.startY = linearRange;
        vdacLoLinePos.stopX = textRight;
        vdacLoLinePos.stopY = linearRange;
        vdacLoLinePos.textX = textCenter;
        vdacLoLinePos.textY = linearRange - 8;

        zeroLinePos.startX = textLeft;
        zeroLinePos.startY = linBuffer;
        zeroLinePos.stopX = textRight;
        zeroLinePos.stopY = linBuffer;
        zeroLinePos.textX = textCenter;
        zeroLinePos.textY = linBuffer - 8;

        vadcLinePos.startX = textLeft;
        vadcLinePos.startY = avgRange;
        vadcLinePos.stopX = textRight;
        vadcLinePos.stopY = avgRange;
        vadcLinePos.textX = textRight;
        vadcLinePos.textY = avgRange + vdacCtPaint.getTextSize() / 3;

        if (vadcLinePos.textY < vdacCtPaint.getTextSize()) {
            vadcLinePos.textY = vdacCtPaint.getTextSize() + 8;
        } else if (vadcLinePos.textY > maxHeight - vdacCtPaint.getTextSize() / 3) {
            vadcLinePos.textY = maxHeight - 16;
        }

        if (avgRange > linearRange) {
            vdacCtPaint.setColor(linPaint.getColor());
        } else {
            vdacCtPaint.setColor(satPaint.getColor());
        }
    }

    private void drawBar(Canvas canvas) {
        canvas.drawRect(linRect, linPaint);
        canvas.drawRect(linBufRect, linBufPaint);
        canvas.drawRect(satRect, satPaint);
        canvas.drawRect(empRect, empPaint);
    }

    private void drawValue(Canvas canvas) {
        canvas.drawLine(vadcLinePos.startX, vadcLinePos.startY,
                vadcLinePos.stopX, vadcLinePos.stopY, vadcLinePaint);
        canvas.drawText(vadcTitle, vadcLinePos.textX, vadcLinePos.textY,
                vdacCtPaint);

    }

    private void drawLine(Canvas canvas) {
        canvas.drawLine(linPos.startX, linPos.startY, linPos.stopX,
                linPos.stopY, linLinePaint);
        canvas.drawLine(satPos.startX, satPos.startY, satPos.stopX,
                satPos.stopY, satLinePaint);
        canvas.drawLine(vdacCtLinePos.startX, vdacCtLinePos.startY,
                vdacCtLinePos.stopX, vdacCtLinePos.stopY, vdacCtLinePaint);
        canvas.drawLine(vdacUpLinePos.startX, vdacUpLinePos.startY,
                vdacUpLinePos.stopX, vdacUpLinePos.stopY, vdacUpLinePaint);
        canvas.drawLine(vdacLoLinePos.startX, vdacLoLinePos.startY,
                vdacLoLinePos.stopX, vdacLoLinePos.stopY, vdacLoLinePaint);
        canvas.drawLine(zeroLinePos.startX, zeroLinePos.startY,
                zeroLinePos.stopX, zeroLinePos.stopY, zeroLinePaint);
    }

    private void drawTitle(Canvas canvas) {
        canvas.drawText(vdacUpTitle, vdacUpLinePos.textX, vdacUpLinePos.textY, vdacPaint);
        canvas.drawText(vdacCtTitle, vdacCtLinePos.textX, vdacCtLinePos.textY, vdacPaint);
        canvas.drawText(vdacLoTitle, vdacLoLinePos.textX, vdacLoLinePos.textY, vdacPaint);
        canvas.drawText(zeroTitle, zeroLinePos.textX, zeroLinePos.textY, vdacPaint);
        canvas.drawText(linRangeTitle, linPos.textX, linPos.textY, rangePaint);
        canvas.drawText(satRangeTitle, satPos.textX, satPos.textY, rangePaint);
    }

    private boolean handNeedsToMove() {
        return Math.abs(handPosition - handTarget) > 0.01f;
    }

    private boolean valueNeedsToMove() {
        return (Math.abs(valuePosition - valueTarget) > 0.01f) && drawValue;
    }

    private void moveHand() {
        if (!handNeedsToMove()) {
            return;
        }
        if (lastHandMoveTime != -1L) {
            long currentTime = System.currentTimeMillis();
            float delta = (currentTime - lastHandMoveTime) / 250.0f;

            float direction = Math.signum(handVelocity);
            if (Math.abs(handVelocity) < 90.0f) {
                handAcceleration = 10.0f * (handTarget - handPosition);
            } else {
                handAcceleration = 0.0f;
            }
            handPosition += handVelocity * delta;
            handVelocity += handAcceleration * delta;
            if ((handTarget - handPosition) * direction < 0.001f * direction) {
                handPosition = handTarget;
                handVelocity = 0.0f;
                handAcceleration = 0.0f;
                lastHandMoveTime = -1L;
            } else {
                lastHandMoveTime = System.currentTimeMillis();
            }

            setVDACText(handPosition);

            invalidate();
        } else {
            lastHandMoveTime = System.currentTimeMillis();
            moveHand();
        }
    }

    private void moveValue() {
        if (!valueNeedsToMove()) {
            return;
        }
        if (lastHandMoveTime != -1L) {
            long currentTime = System.currentTimeMillis();
            float delta = (currentTime - lastHandMoveTime) / 250.0f;

            float direction = Math.signum(handVelocity);
            if (Math.abs(handVelocity) < 90.0f) {
                handAcceleration = 10.0f * (valueTarget - valuePosition);
            } else {
                handAcceleration = 0.0f;
            }
            valuePosition += handVelocity * delta;
            handVelocity += handAcceleration * delta;
            if ((valueTarget - valuePosition) * direction < 0.001f * direction) {
                valuePosition = valueTarget;
                handVelocity = 0.0f;
                handAcceleration = 0.0f;
                lastHandMoveTime = -1L;
            } else {
                lastHandMoveTime = System.currentTimeMillis();
            }

            setVADCText(valuePosition);

            invalidate();
        } else {
            lastHandMoveTime = System.currentTimeMillis();
            moveValue();
        }
    }

    private void setVADCText(float value) {
        vadcTitle = String.format(Locale.US, "%.4f", Math.abs(value / 1000.0)) + "V";
    }

    private void setVDACText(float value) {
        vdacUpTitle = String.format(Locale.US, "%.2f", (value + rangePlus) / 1000.0);
        vdacCtTitle = String.format(Locale.US, "%.2f", value / 1000.0);
        vdacLoTitle = String.format(Locale.US, "%.2f", (value - rangeMinus) / 1000.0);
    }

    public void setBias(int biasCurrentInuA) {
        // = -0.75x^2 + 0.775x + 0.065
        // x -> mA
        double mA = biasCurrentInuA / 1000.0;
        double rangeCal = (-0.75 * mA * mA) + (0.775 * mA) + 0.065;
        rangeCal *= 1000;
        if (rangeCal < 0) {
            rangeCal = 0;
        }
        this.maxRange = MAX_RANGE;
        this.rangePlus = 250;
        this.rangeMinus = Math.round(rangeCal);
        setVDACText(handPosition);
    }

    public void setRange(int current) {
        if (current < 0) {
            current = 0;
        } else if (current > maxRange - rangePlus) {
            current = (int) (maxRange - rangePlus);
        }
        draw = true;
        handTarget = current;
        invalidate();
    }

    public void setValue(float value) {
        if (value < 0) {
            value = 0;
        } else if (value > MAX_RANGE) {
            value = MAX_RANGE;
        }
        draw = true;
        drawValue = true;
        valueTarget = value;
        if (valueTarget == valuePosition) {
            valuePosition--;
        }
        invalidate();
    }

    public void setBuffer(boolean buffer) {
        this.buffer = buffer;
    }

    class Position {
        float startX;
        float startY;
        float stopX;
        float stopY;
        float textX;
        float textY;
    }
}
