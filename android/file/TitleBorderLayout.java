package com.github.kevinshen.customview;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.Layout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class TitleBorderLayout  extends LinearLayout {

    /**  默认情况下标题在总长度的1/10处显示  */
    private static float DEFAULT_TITLE_POSITION_SCALE = 0.1f;
    public static int DEFAULT_BORDER_SIZE = 1;
    public static int DEFAULT_BORDER_COLOR = Color.GRAY;
    public static int DEAFULT_TITLE_COLOR = Color.BLACK;

    /** 边框面板的高度 */
    private int mBorderPaneHeight ;

    private Paint mBorderPaint;
    private float mBorderSize;

    private TextPaint mTextPaint;
    private CharSequence mTitle;
    private int mTitlePosition;

    public TitleBorderLayout(Context context) {
        this(context, null);
    }

    /**
     * Construct a new TitleBorderLayout with default style, overriding specific style
     * attributes as requested.
     */
    public TitleBorderLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);

        mBorderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        Resources res = getResources();
        mTextPaint.density = res.getDisplayMetrics().density;

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TitleBorderLayout);

        mTitle = a.getText(R.styleable.TitleBorderLayout_titleText);
        int titleColor = a.getColor(R.styleable.TitleBorderLayout_titleTextColor, DEAFULT_TITLE_COLOR);
        mTextPaint.setColor(titleColor);

        float titleTextSize = a.getDimension(R.styleable.TitleBorderLayout_titleTextSize, 0);
        if (titleTextSize > 0) {
            mTextPaint.setTextSize(titleTextSize);
        }

        mTitlePosition = a.getDimensionPixelSize(R.styleable.TitleBorderLayout_titlePosition, -1);

        mBorderSize = a.getDimensionPixelSize(R.styleable.TitleBorderLayout_borderSize, DEFAULT_BORDER_SIZE);

        int borderColor = a.getColor(R.styleable.TitleBorderLayout_borderColor, DEFAULT_BORDER_COLOR);
        mBorderPaint.setColor(borderColor);

        a.recycle();
        int paddingStart = getPaddingStart();
        int paddingTop = getPaddingTop();
        int paddingEnd = getPaddingEnd();
        int paddingBottom = getPaddingBottom();
        int titleHeightPixel = (int) ((mTextPaint.getFontMetrics().bottom - mTextPaint.getFontMetrics().top));
        paddingTop = paddingTop +  titleHeightPixel;
        paddingStart = (int) (paddingStart + mBorderSize);
        paddingEnd = (int) (paddingEnd + mBorderSize);
        paddingBottom = (int) (paddingBottom + mBorderSize);
        setPadding(paddingStart, paddingTop, paddingEnd, paddingBottom);
    }

    /**
     * Get the color of border.
     */
    public int getBorderColor() {
        return mBorderPaint.getColor();
    }

    /**
     * Set the color of border.
     */
    public void setBorderColor(int borderColor) {
        mBorderPaint.setColor(borderColor);
        requestLayout();
    }

    /**
     * Get the size of border.
     */
    public float getBorderSize() {
        return mBorderSize;
    }

    /**
     * Set the size of border.
     */
    public void setBorderSize(float borderSize) {
        mBorderSize = borderSize;
        requestLayout();
    }

    /**
     * Get the color of title.
     */
    public int getTitleColor() {
        return mTextPaint.getColor();
    }

    /**
     * Set the color of title.
     */
    public void setTitleColor(int titleColor) {
        mTextPaint.setColor(titleColor);
        requestLayout();
    }

    /**
     * Get the size of title.
     */
    public float getTitleTextSize() {
        return mTextPaint.getTextSize();
    }

    /**
     * Set the size of title.
     */
    public void setTitleTextSize(float titleTextSize) {
        mTextPaint.setTextSize(titleTextSize);
        requestLayout();
    }

    /**
     * Get the title.
     */
    public CharSequence getTitle() {
        return mTitle;
    }

    /**
     * Set the title which will be shown on the top of border pane.
     */
    public void setTitle(CharSequence title) {
        mTitle = title;
        requestLayout();
    }

    /**
     * Get the position of title.
     */
    public int getTitlePosition() {
        return mTitlePosition;
    }

    /**
     * Set the position of title where the paint will start to draw.
     */
    public void setTitlePosition(int titlePosition) {
        mTitlePosition = titlePosition;
        requestLayout();
    }

    /**
     * Get the height of border pane, it's different from the layout height!
     */
    public int getBorderPaneHeight() {
        return mBorderPaneHeight;
    }

    /**
     * Draw the title border
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint.FontMetrics fm = mTextPaint.getFontMetrics();
        final float titleHeight =  fm.descent - fm.ascent;

        final CharSequence titleText = (mTitle == null) ? "" : mTitle;
        final float titleWidth = Layout.getDesiredWidth(titleText, mTextPaint);

        final int width = getWidth();
        final int height = getHeight();

        if (mTitlePosition <= 0 || mTitlePosition + titleWidth > width) {
            mTitlePosition = (int) (DEFAULT_TITLE_POSITION_SCALE * width);
        }

        final float topBorderStartY = titleHeight / 3f - mBorderSize / 2;

        mBorderPaneHeight = (int) Math.ceil(height - topBorderStartY);
        /*  画标题边框  */
        // 上
        canvas.drawRect(0, topBorderStartY, mTitlePosition, topBorderStartY + mBorderSize, mBorderPaint);
        canvas.drawText(titleText.toString(), mTitlePosition, titleHeight / 3 * 2f, mTextPaint); // 标题
        canvas.drawRect(mTitlePosition + titleWidth, topBorderStartY, width, topBorderStartY + mBorderSize, mBorderPaint);
        // 左
        canvas.drawRect(0, topBorderStartY, mBorderSize, height, mBorderPaint);
        // 右
        canvas.drawRect(width - mBorderSize, topBorderStartY, width, height, mBorderPaint);
        // 下
        canvas.drawRect(0, height - mBorderSize, width, height, mBorderPaint);
    }

}
