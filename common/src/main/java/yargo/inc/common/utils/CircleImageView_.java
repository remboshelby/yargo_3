package yargo.inc.common.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;


import yargo.inc.common.R;

public class CircleImageView_ extends ImageView {

    private static final int DEF_PRESS_HIGHLIGHT_COLOR = 0x32000000;

    private Shader mBitmapShader;
    private Matrix mShaderMatrix;

    private RectF mBitmapDrawBounds;
    private RectF mStrokeBounds;

    private Bitmap mBitmap;

    private Paint mBitmapPaint;
    private Paint mStrokePaint;

    private boolean mInitialized;

    public CircleImageView_(Context context) {
        this(context, null);
    }

    public CircleImageView_(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        int strokeColor = Color.TRANSPARENT;
        float strokeWidth = 0;
        boolean highlightEnable = true;
        int highlightColor = DEF_PRESS_HIGHLIGHT_COLOR;

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView, 0, 0);

            strokeColor = a.getColor(R.styleable.CircleImageView_strokeColor, Color.RED);
            strokeWidth = a.getDimensionPixelSize(R.styleable.CircleImageView_strokeWidth, 5);
            highlightEnable = a.getBoolean(R.styleable.CircleImageView_highlightEnable, false);
            highlightColor = a.getColor(R.styleable.CircleImageView_highlightColor, DEF_PRESS_HIGHLIGHT_COLOR);

            a.recycle();
        }

        mShaderMatrix = new Matrix();
        mBitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mStrokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mStrokeBounds = new RectF();
        mBitmapDrawBounds = new RectF();
        mStrokePaint.setColor(strokeColor);
        mStrokePaint.setStyle(Paint.Style.STROKE);
        mStrokePaint.setStrokeWidth(strokeWidth);

        mInitialized = true;

        setupBitmap();
    }

    @Override
    public void setImageResource(@DrawableRes int resId) {
        super.setImageResource(resId);
        setupBitmap();
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        float halfStrokeWidth = mStrokePaint.getStrokeWidth() / 2f;
        updateCircleDrawBounds(mBitmapDrawBounds);
        mStrokeBounds.set(mBitmapDrawBounds);
        mStrokeBounds.inset(halfStrokeWidth, halfStrokeWidth);

        updateBitmapSize();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        drawBitmap(canvas);
        drawStroke(canvas);
    }


    protected void drawStroke(Canvas canvas) {
        if (mStrokePaint.getStrokeWidth() > 0f) {
            canvas.drawOval(mStrokeBounds, mStrokePaint);
        }
    }

    protected void drawBitmap(Canvas canvas) {
        canvas.drawOval(mBitmapDrawBounds, mBitmapPaint);
    }

    protected void updateCircleDrawBounds(RectF bounds) {
        float contentWidth = getWidth() - getPaddingLeft() - getPaddingRight();
        float contentHeight = getHeight() - getPaddingTop() - getPaddingBottom();

        float left = getPaddingLeft();
        float top = getPaddingTop();
        if (contentWidth > contentHeight) {
            left += (contentWidth - contentHeight) / 2f;
        } else {
            top += (contentHeight - contentWidth) / 2f;
        }

        float diameter = Math.min(contentWidth, contentHeight);
        bounds.set(left, top, left + diameter, top + diameter);
    }

    private void setupBitmap() {
        if (!mInitialized) {
            return;
        }
        mBitmap = getBitmapFromDrawable(getDrawable());
        if (mBitmap == null) {
            return;
        }

        mBitmapShader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        mBitmapPaint.setShader(mBitmapShader);

        updateBitmapSize();
    }

    private void updateBitmapSize() {
        if (mBitmap == null) return;

        float dx;
        float dy;
        float scale;

        if (mBitmap.getWidth() < mBitmap.getHeight()) {
            scale = mBitmapDrawBounds.width() / (float)mBitmap.getWidth();
            dx = mBitmapDrawBounds.left;
            dy = mBitmapDrawBounds.top - (mBitmap.getHeight() * scale / 2f) + (mBitmapDrawBounds.width() / 2f);
        } else {
            scale = mBitmapDrawBounds.height() / (float)mBitmap.getHeight();
            dx = mBitmapDrawBounds.left - (mBitmap.getWidth() * scale / 2f) + (mBitmapDrawBounds.width() / 2f);
            dy = mBitmapDrawBounds.top;
        }
        mShaderMatrix.setScale(scale, scale);
        mShaderMatrix.postTranslate(dx, dy);
        mBitmapShader.setLocalMatrix(mShaderMatrix);
    }

    private Bitmap getBitmapFromDrawable(Drawable drawable) { //Преобразуем вектор в растровое
        if (drawable == null) {
            return null;
        }

        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        Bitmap bitmap = Bitmap.createBitmap(
                drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }


}