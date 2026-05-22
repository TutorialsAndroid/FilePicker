package com.developer.filepicker.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import com.developer.filepicker.R;

/**
 * Lightweight custom checkbox used by the file picker rows.
 */
public class MaterialCheckbox extends View {

    private int minDim;
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final RectF bounds = new RectF();
    private final Path tick = new Path();
    private boolean checked;
    private OnCheckedChangeListener onCheckedChangeListener;

    public MaterialCheckbox(Context context) {
        super(context);
        initView();
    }

    public MaterialCheckbox(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MaterialCheckbox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        checked = false;
        setClickable(true);
        setFocusable(true);
        setOnClickListener(v -> toggle());
        updateAccessibilityState();
    }

    private void toggle() {
        setChecked(!checked);
        if (onCheckedChangeListener != null) {
            onCheckedChangeListener.onCheckedChanged(MaterialCheckbox.this, checked);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (minDim <= 0) {
            return;
        }

        paint.reset();
        paint.setAntiAlias(true);
        bounds.set(minDim / 10f, minDim / 10f, minDim - (minDim / 10f), minDim - (minDim / 10f));

        if (checked) {
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(getColorCompat(R.color.colorAccent));
            canvas.drawRoundRect(bounds, minDim / 8f, minDim / 8f, paint);

            paint.setColor(Color.WHITE);
            paint.setStrokeWidth(Math.max(2f, minDim / 10f));
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeCap(Paint.Cap.ROUND);
            paint.setStrokeJoin(Paint.Join.ROUND);
            canvas.drawPath(tick, paint);
        } else {
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.parseColor("#C1C1C1"));
            canvas.drawRoundRect(bounds, minDim / 8f, minDim / 8f, paint);

            bounds.set(minDim / 5f, minDim / 5f, minDim - (minDim / 5f), minDim - (minDim / 5f));
            paint.setColor(Color.WHITE);
            canvas.drawRect(bounds, paint);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        minDim = Math.min(width, height);

        tick.reset();
        if (minDim > 0) {
            tick.moveTo(minDim * 0.25f, minDim * 0.52f);
            tick.lineTo(minDim * 0.43f, minDim * 0.70f);
            tick.lineTo(minDim * 0.76f, minDim * 0.34f);
        }

        setMeasuredDimension(width, height);
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        if (this.checked == checked) {
            return;
        }
        this.checked = checked;
        updateAccessibilityState();
        invalidate();
    }

    public void setOnCheckedChangedListener(OnCheckedChangeListener onCheckedChangeListener) {
        this.onCheckedChangeListener = onCheckedChangeListener;
    }

    private int getColorCompat(int colorRes) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return getResources().getColor(colorRes, getContext().getTheme());
        }
        return getResources().getColor(colorRes);
    }

    private void updateAccessibilityState() {
        setSelected(checked);
        setContentDescription(checked ? "Checked" : "Unchecked");
    }
}
