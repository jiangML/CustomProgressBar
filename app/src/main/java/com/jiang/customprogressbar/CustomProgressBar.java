package com.jiang.customprogressbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by Administrator on 2016/5/27.
 */
public class CustomProgressBar extends View {

//horizontal
    //水平进度条
    public static  final int PROGRESS_BAR_MODE_HORIZONTAL=0x1000;
    //环型进度条
    public static final int  PROGRESS_BAR_MODE_RING=0x1001;
    //默认是环型进度条
    private int barMode=PROGRESS_BAR_MODE_RING;
    //画笔宽度
    private int paintWidth=dp2px(10);
    //画笔颜色 默认黑色
    private int paintColor= Color.BLACK;

    //画笔
    private Paint mPaint;
    //当前的进度值
    private int progress=0;
    //字体的大小
    private int progressTextSize=sp2px(20);

    //进度最大值 默认为100
    private int progressMax=100;

    private int progressColor=Color.RED;

    public CustomProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public CustomProgressBar(Context context) {
        super(context, null);

    }

    public CustomProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }


    private void init(Context context, AttributeSet attrs)
    {
        TypedArray array=context.obtainStyledAttributes(attrs,R.styleable.CustomProgressBar);
        barMode=array.getInt(R.styleable.CustomProgressBar_progress_mode, 1)==0?PROGRESS_BAR_MODE_HORIZONTAL:PROGRESS_BAR_MODE_RING;
        paintWidth=array.getDimensionPixelSize(R.styleable.CustomProgressBar_paint_width, paintWidth);
        paintColor=array.getColor(R.styleable.CustomProgressBar_paint_color, paintColor);
        progressTextSize=array.getDimensionPixelSize(R.styleable.CustomProgressBar_progress_text_size, progressTextSize);
        progressMax=array.getInteger(R.styleable.CustomProgressBar_progress_max, progressMax);
        progressColor=array.getColor(R.styleable.CustomProgressBar_progress_color,progressColor);
        array.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width=0;
        int height=0;

        int widthSize=MeasureSpec.getSize(widthMeasureSpec);
        int widthMode=MeasureSpec.getMode(widthMeasureSpec);
        int heightSize=MeasureSpec.getSize(heightMeasureSpec);
        int heightMode=MeasureSpec.getMode(heightMeasureSpec);

        String text="100%";
        Rect bound=new Rect();
        Paint paint=new Paint();
        paint.getTextBounds(text, 0, text.length(), bound);

        if(widthMode==MeasureSpec.EXACTLY)
        {
            width=widthSize;
        }else{
            width=getPaddingLeft()+bound.width()+getPaddingRight();
        }

        if(heightMode==MeasureSpec.EXACTLY)
        {
            height=heightSize;
        }else{
            height=getPaddingTop()+bound.height()+getPaddingBottom();
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
      // super.onDraw(canvas);
        Paint paint=new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(new Rect(0, 0, getWidth(), getHeight()), paint);
        switch (barMode)
        {
            case PROGRESS_BAR_MODE_HORIZONTAL:
                drawHorizontal(canvas);
                break;
            case PROGRESS_BAR_MODE_RING:
                drawRing(canvas);
                break;
        }
    }

    public void setProgress(int progress)
    {
        this.progress=progress;
         //postInvalidate();
    }


    private void drawHorizontal(Canvas canvas)
    {

    }

    private void drawRing(Canvas canvas)
    {
        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(paintColor);
        mPaint.setStrokeWidth(1);
       // mPaint.setTextSize(progressTextSize);
        mPaint.setStyle(Paint.Style.STROKE);

        //画外圆
        canvas.drawCircle(getWidth()/2,getHeight()/2,Math.min(getWidth(),getHeight())/2,mPaint);
        //画内圆
        canvas.drawCircle(getWidth()/2,getHeight()/2,(Math.min(getWidth(),getHeight())-2*paintWidth)/2,mPaint);

        //canvas.translate(getPaddingLeft(),getPaddingTop());
       // canvas.drawCircle(getWidth() / 2, getHeight() / 2, Math.min(getWidth(), getHeight()) / 2, mPaint);

        //画进度百分比
        String text=progress+"%";
        float textWdth=mPaint.measureText(text);
        float textHeight=(mPaint.descent()+mPaint.ascent())/2;
        mPaint.setTextSize(progressTextSize);
        mPaint.setColor(Color.YELLOW);
        canvas.drawText(text, getWidth() / 2 - textWdth / 2, getHeight() / 2 - textHeight / 2, mPaint);

        //画进度值
        mPaint.setColor(progressColor);
        mPaint.setStrokeWidth(paintWidth);
        float sweepAngle=progress*360*1.0f/progressMax;
        canvas.drawArc(new RectF(0, 0, getWidth()-paintWidth/2, getHeight()-paintWidth/2), 0, sweepAngle, false, mPaint);
    }

    /**
     * dp转px
     * @param dp
     * @return
     */
    private int dp2px(int dp)
    {
       return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,getResources().getDisplayMetrics());
    }

    /**
     * sp转px
     * @param sp
     * @return
     */
    private int sp2px(int sp)
    {
        return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,sp,getResources().getDisplayMetrics());
    }

}