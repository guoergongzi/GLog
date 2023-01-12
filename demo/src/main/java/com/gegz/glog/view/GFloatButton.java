package com.gegz.glog.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.gegz.glog.R;

/**
 * 悬浮按钮控件
 */
public class GFloatButton extends RelativeLayout {

    private final static int LEFT = 0;
    private final static int RIGHT = 1;
    private final static int TOP = 3;
    private final static int BUTTOM = 4;

    private int dpi;
    private int screenHeight;
    private int screenWidth;
    private WindowManager.LayoutParams wmParams;
    private WindowManager wm;
    private float x, y;
    private float mTouchStartX;
    private float mTouchStartY;
    private int lastX;
    private int lastY;
    private boolean isScroll;
    private boolean isDrag;// 判断是否能够点击

    /**
     * 初始化悬浮按钮的方法
     */
    @SuppressWarnings("deprecation")
    private void init(Activity activity) {
        if (wm == null) {
            wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
            LayoutInflater.from(activity).inflate(R.layout.layout_button_gfloat, this);
            DisplayMetrics dm = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
            dpi = dpi(dm.densityDpi);
            screenWidth = wm.getDefaultDisplay().getWidth();
            screenHeight = wm.getDefaultDisplay().getHeight();
            wmParams = new WindowManager.LayoutParams();
            wmParams.type = WindowManager.LayoutParams.TYPE_APPLICATION;
            wmParams.format = PixelFormat.RGBA_8888;
            wmParams.gravity = Gravity.START | Gravity.TOP;
            wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
            wmParams.width = dpi;
            wmParams.height = dpi;
            wmParams.x = (screenWidth - dpi);
            wmParams.y = (screenHeight - dpi * 6);
            wm.addView(this, wmParams);
            hide();
        }
    }

    /**
     * 根据密度选择控件大小
     */
    private int dpi(int densityDpi) {
        if (densityDpi <= 120) {
            return 36;
        } else if (densityDpi <= 160) {
            return 48;
        } else if (densityDpi <= 240) {
            return 72;
        } else if (densityDpi <= 320) {
            return 96;
        }
        return 108;
    }

    /**
     * 显示按钮
     */
    public void show() {
        if (isShown()) {
            return;
        }
        setVisibility(View.VISIBLE);
    }

    /**
     * 隐藏按钮
     */
    public void hide() {
        setVisibility(View.GONE);
    }

    /**
     * 销毁按钮
     */
    public void destory() {
        hide();
        wm.removeViewImmediate(this);
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        x = event.getRawX();
        y = event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                setPressed(true);
                isDrag = false;
                getParent().requestDisallowInterceptTouchEvent(true);
                lastX = (int) x;
                lastY = (int) y;
                mTouchStartX = event.getX();
                mTouchStartY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (isScroll) {
                    int dx = (int) (x - lastX);
                    int dy = (int) (y - lastY);
                    int distance = (int) Math.sqrt(dx * dx + dy * dy);
                    isDrag = distance > 0;
                    if (!isDrag) break;
                    updateViewPosition();
                } else {
                    if (Math.abs(mTouchStartX - event.getX()) > dpi / 3F
                            || Math.abs(mTouchStartY - event.getY()) > dpi / 3F) {
                        updateViewPosition();
                    } else {
                        break;
                    }
                }
                isScroll = true;
                break;
            case MotionEvent.ACTION_UP:
                if (isScroll) {
                    setPressed(!isDrag);
                    autoView();
                }
                isScroll = false;
                mTouchStartX = mTouchStartY = 0;
                break;
        }
        //如果不是拖拽，那么就不消费这个事件，以免影响点击事件的处理
        //拖拽事件要自己消费
        return isDrag || super.onTouchEvent(event);
    }


    /**
     * 自动移动位置
     */
    private void autoView() {
        int[] location = new int[2];
        getLocationOnScreen(location);
        if (location[0] < screenWidth / 2 - getWidth() / 2) {
            updateViewPosition(LEFT);
        } else {
            updateViewPosition(RIGHT);
        }
    }

    /**
     * 手指释放更新悬浮窗位置
     */
    private void updateViewPosition(int l) {
        switch (l) {
            case LEFT:
                wmParams.x = 0;
                break;
            case RIGHT:
                wmParams.x = screenWidth - dpi;
                break;
            case TOP:
                wmParams.y = 0;
                break;
            case BUTTOM:
                wmParams.y = screenHeight - dpi;
                break;
            default:
                break;
        }
        wm.updateViewLayout(this, wmParams);
    }

    /**
     * 更新浮动按钮位置参数
     */
    private void updateViewPosition() {
        wmParams.x = (int) (x - mTouchStartX);
        wmParams.y = (int) (y - mTouchStartY - screenHeight / 25);
        wm.updateViewLayout(this, wmParams);
    }

    /**
     * 构造方法
     */
    public GFloatButton(Activity activity) {
        super(activity);
        init(activity);
    }

    public GFloatButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GFloatButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}