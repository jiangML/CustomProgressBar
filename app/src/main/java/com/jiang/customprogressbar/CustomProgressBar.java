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

    //默认进度的颜色
    private int progressColor=Color.RED;

    //默认进度值字体的颜色
    private int progressTextColor=progressColor;

    //测量字体大小的Rect
    private    Rect bound;

    //当时水平进度条时 百分值左右的padding
    private int offset=dp2px(5);


    public CustomProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public CustomProgressBar(Context context) {
        super(context);

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
        progressColor=array.getColor(R.styleable.CustomProgressBar_progress_color, progressColor);
        progressTextColor=array.getColor(R.styleable.CustomProgressBar_progress_text_color, progressTextColor);
        progress=array.getInteger(R.styleable.CustomProgressBar_progress_default_value,progress);
        array.recycle();
        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        bound=new Rect();
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

        mPaint.getTextBounds(text, 0, text.length(), bound);

        if(widthMode==MeasureSpec.EXACTLY)// 精确值或者是match_parent
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

        if (barMode==PROGRESS_BAR_MODE_RING)
        {
            setMeasuredDimension(Math.min(width,height), Math.min(width,height));
        }else {
            setMeasuredDimension(width, height);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
       super.onDraw(canvas);
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

    /**
     * 画水平进度条
     * @param canvas
     */
    private void drawHorizontal(Canvas canvas) {

         String text=getProgress()+"%";
         float textWidth=mPaint.measureText(text);
         float textHeight=mPaint.descent()+mPaint.ascent();

         //画当前进度值
          mPaint.setColor(progressColor);
          mPaint.setStyle(Paint.Style.STROKE);
          mPaint.setStrokeWidth(paintWidth);
          if(progress<progressMax)
          {
              canvas.drawLine(0,getHeight()/2,(getWidth()-2*offset-textWidth)*progress/progressMax,getHeight()/2,mPaint);
          }
          if(progress>=progressMax)
          {
              canvas.drawLine(0,getHeight()/2,getWidth()-2*offset-textWidth,getHeight()/2,mPaint);
          }


          //画当前百分比进度值
          mPaint.setTextSize(progressTextSize);
          mPaint.setStrokeWidth(0);
          mPaint.setStyle(Paint.Style.FILL);
          mPaint.setColor(progressTextColor);
          if(progress<progressMax) {
              canvas.drawText(text, (getWidth()-2*offset-textWidth) * progress / progressMax + offset, getHeight() / 2 - textHeight / 2, mPaint);
          }

          if(progress>=progressMax)
          {
              canvas.drawText(text, getWidth()-offset-textWidth, getHeight() / 2 - textHeight / 2, mPaint);
          }


        //画未到达的进度条
          mPaint.setColor(paintColor);
          mPaint.setStrokeWidth(paintWidth);
          mPaint.setStyle(Paint.Style.STROKE);
          if (progress < progressMax) {
            canvas.drawLine((getWidth()-2*offset-textWidth) * progress / progressMax + 2 * offset + textWidth, getHeight() / 2, getWidth(), getHeight() / 2, mPaint);
          }


    }

    /**
     * 画环型进度条
     * @param canvas
     */
    private void drawRing(Canvas canvas)
    {

        mPaint.setColor(paintColor);
        mPaint.setStrokeWidth(1);
        mPaint.setStyle(Paint.Style.STROKE);

        //画外圆
        canvas.drawCircle(getWidth()/2,getHeight()/2,Math.min(getWidth(),getHeight())/2,mPaint);
        //画内圆
        canvas.drawCircle(getWidth()/2,getHeight()/2,(Math.min(getWidth(),getHeight())-2*paintWidth)/2,mPaint);


        //画进度百分比
        String text=progress+"%";
        float textWdth=mPaint.measureText(text);
        float textHeight=mPaint.descent()+mPaint.ascent();
        mPaint.setTextSize(progressTextSize);
        mPaint.setColor(progressTextColor);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawText(text, getWidth() / 2 - textWdth / 2, getHeight() / 2 - textHeight/2, mPaint);

        //画进度值
        mPaint.setColor(progressColor);
        mPaint.setStrokeWidth(paintWidth);
        mPaint.setStyle(Paint.Style.STROKE);
        float sweepAngle=progress*360*1.0f/progressMax;
        canvas.drawArc(new RectF(paintWidth / 2, paintWidth / 2, getWidth() - paintWidth / 2, getHeight() - paintWidth / 2), 0, sweepAngle, false, mPaint);
    }

    /**
     * 设置进度值
     * @param progress
     */
    public synchronized  void setProgress(int progress)
    {
        this.progress=progress;
        if(getProgress()>getProgressMax())
        {
            return;
        }
        invalidate();
    }

    /**
     *获取进度值
     * @return
     */
    public synchronized int getProgress()
    {
        return this.progress;
    }

    /**
     * 获取进度最大值
     * @return
     */
    public synchronized int getProgressMax()
    {
        return this.progressMax;
    }

    /**
     * 设置进度最大值
     * @param progressMax
     */
    public synchronized void  setProgressMax(int progressMax)
    {
        this.progressMax=progressMax;
    }

    /**
     * 设置进度值字体颜色
     * @param textColor
     */
   public synchronized  void setProgressTextColor(int textColor)
   {
       this.progressTextColor=textColor;
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
