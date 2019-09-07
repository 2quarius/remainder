package com.example.trail.Setting.NoInterrupt;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


public class CustomSwitchView extends View {

    // 开关控件底部窗体灰色背景的画笔
    private Paint mBottomPaint;
    // 开关控件底部窗体绿色背景的画笔
    private Paint mBottomPaint_Left;
    // 顶部白色滑块的画笔
    private Paint mTopPaint;
    // 提示字体及验证完成的画笔
    private Paint mTextPaint;
    private String textHint = "滑动解锁";
    //private String textCompleted = "验证成功";
    private float widthSlide = 0; // 滑块的宽度，设置为view宽的 1/5
    private float viewWidth;// 窗体宽度
    private float viewHeight;//窗体高度
    private float mTextHintWidth;// 提示字体的宽度
    private float mTextCompletedWidth;// 验证完成的宽度

    private float currentX = 0; // 当前手指所在位置
    private boolean isSlidding = false; // 是否正在滑动
    private boolean isCompelted = false; // 是否滑动完成
    // 自定义滑动完成的回掉接口，处理相应的逻辑
    private ScrollCompletedListener_interface scrollCompletedListenner;

    public CustomSwitchView(Context context) {
        this(context, null);
    }

    public CustomSwitchView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomSwitchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 初始化一些基本的属性
     */
    private void init() {
        mBottomPaint = new Paint();
        mBottomPaint.setAntiAlias(true);
        mBottomPaint.setColor(Color.GREEN);//后半段
        mTopPaint = new Paint();
        mTopPaint.setAntiAlias(true);
        mTopPaint.setColor(Color.WHITE);//按钮
        mBottomPaint_Left = new Paint();
        mBottomPaint_Left.setAntiAlias(true);
        mBottomPaint_Left.setColor(Color.GREEN);//前半段
        mTextPaint = new TextPaint();
        mTextPaint.setColor(Color.GRAY);//字体
        mTextPaint.setTextSize(45);//字体大小
        mTextPaint.setAntiAlias(true);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureSize(widthMeasureSpec),
                measureSize(heightMeasureSpec));
    }

    /**
     * 重写该方法  为了测量自定义view的大小，调用时可以使用wrap_content
     * @param measureSpec
     * @return
     */
    private int measureSize(int measureSpec) {
        int size = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            size = specSize;
        } else {
            size = 200;
            if (specMode == MeasureSpec.AT_MOST) {
                size = Math.min(size, specSize);
            }
        }
        return size;
    }

    /**
     * 为了获取窗体的大小，必须在测量之后才能获取，否则为0
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewWidth = getWidth();
        viewHeight = getHeight();
        widthSlide = viewWidth/5;
        mTextHintWidth = mTextPaint.measureText(textHint);
        //mTextCompletedWidth = mTextPaint.measureText(textCompleted);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 画出底部的灰色矩形背景
        canvas.drawRect(0, 0, viewWidth, viewHeight, mBottomPaint);
        // 当滑块右侧位置滑动到提示字体左侧时去除掉（不再画出）提示字体
        if ((currentX + widthSlide) <= (viewWidth / 2 - mTextHintWidth / 2)) {
            canvas.drawText(textHint, (viewWidth/2 - mTextHintWidth/2),
                    viewHeight/2 + 15, mTextPaint);
        }

        // 根据滑块的坐标位置，画出白色滑块，滑块的坐标根据手触摸到屏幕上坐标位置计算
        canvas.drawRect(currentX, 0, (currentX + widthSlide),
                viewHeight, mTopPaint);
        // 滑块滑向右侧的过程中，滑块左侧显示出绿色
        canvas.drawRect(0, 0, currentX, viewHeight, mBottomPaint_Left);

        if (isSlidding) {
            if (scrollCompletedListenner != null) {
                scrollCompletedListenner.scrollCompleted();
            }
        }

    }

    /**
     * 滑动时动态画方块
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 加判断是为了滑动完成时 防止滑块回滑
        if (!isCompelted) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_MOVE:
                    currentX = event.getX() < 0 ? 0 : event.getX();
                    if (currentX >= (viewWidth - widthSlide)) {
                        currentX = viewWidth - widthSlide;
                    }
                    invalidate();
                    break;
                case MotionEvent.ACTION_UP:
                    if (currentX < (viewWidth - widthSlide - 10)) {
                        currentX = 0;
                        isSlidding = false;
                    } else {
                        currentX = viewWidth - widthSlide;
                        isSlidding = true;
                    }
                    invalidate();
                    break;
            }
        }
        return true;
    }

    public interface ScrollCompletedListener_interface {
        void scrollCompleted();
    }

    /**
     * 设置监听
     * @param scrollCompletedListener
     */
    public void setOnScrollCompletedListener(ScrollCompletedListener_interface
                                                     scrollCompletedListener) {
        this.scrollCompletedListenner = scrollCompletedListener;
    }

}
