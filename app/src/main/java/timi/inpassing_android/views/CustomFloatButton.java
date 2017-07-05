package timi.inpassing_android.views;

import android.content.Context;
import android.graphics.PixelFormat;
import android.support.annotation.DrawableRes;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import timi.inpassing_android.R;

/**
 * 悬浮按钮（派单 接单）
 *
 * @autor timi
 * create at 2017/7/5 16:37
 */

public class CustomFloatButton {
    private boolean isShowing = false;//是否显示
    private WindowManager mWindowManager;//窗口管理器
    private WindowManager.LayoutParams mWindowParams;//窗口params
    private Context mContext;
    //屏幕的宽高
    private int mScreenWidth = 0;
    private int mScreenHeight = 0;
    private int mStatusBarHeight = 0;
    private static CustomFloatButton instance = null;
    //view
    private View mContentView;
    private Button mButton;

    /**
     * 初始化
     *
     * @param builder
     */
    public CustomFloatButton(Builder builder) {
        mContext = builder.getContext();
        //获取宽高
        mScreenWidth = mContext.getResources().getDisplayMetrics().widthPixels;
        mScreenHeight = mContext.getResources().getDisplayMetrics().heightPixels;
        mWindowManager = (WindowManager)
                mContext.getSystemService(Context.WINDOW_SERVICE);
        mWindowParams = new WindowManager.LayoutParams();
        mWindowParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_PANEL;
        mWindowParams.gravity = Gravity.LEFT | Gravity.TOP;
        mWindowParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mWindowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mWindowParams.flags =
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |
                        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
        mWindowParams.format = PixelFormat.RGBA_8888; //设置图片格式，效果为背景透明
        // TODO: 2017/7/5 动画
//        mWindowParams.windowAnimations = R.style.TopNoticeAnim;
        mWindowParams.x = 0;
        mWindowParams.y = 400;
        setContentView(mContext, builder);

    }

    /***
     * 设置内容视图
     *
     * @param context
     */
    private void setContentView(Context context, Builder builder) {
        mContentView = LayoutInflater.from(context).inflate(R.layout.view_custom_float_button, null);
        mButton = (Button) mContentView.findViewById(R.id.bt_custom_float);
        setIcon(builder.imgRes);
        setTitle(builder.title);
        /**
         * touch 事件
         */
        mContentView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_MOVE) {//如果是移动
                    //坐标
                    float x = event.getX();
                    float y = event.getY();
                    //更改按钮的位置
                    updateWindowLocation(x,y);
                }
                return true;
            }
        });
    }

    /**
     * 设置文字
     */
    public void setTitle(String title) {
        if (!TextUtils.isEmpty(title)) {
            mButton.setVisibility(View.VISIBLE);
            mButton.setText(title);
        }
    }

    /**
     * 按钮背景
     *
     * @param usericon
     */
    public void setIcon(@DrawableRes int usericon) {
        mButton.setVisibility(View.VISIBLE);
        mButton.setBackgroundResource(usericon);
    }

    public void updateWindowLocation(float x, float y) {
        if (isShowing) {
            mWindowParams.x = (int) x;
            mWindowParams.y = (int) y;
            mWindowManager.updateViewLayout(mContentView, mWindowParams);
        }

    }

    public void show() {
        if (!isShowing) {
            isShowing = true;
            mWindowManager.addView(mContentView, mWindowParams);
        }
    }

    public void dismiss() {
        if (isShowing) {
            resetState();
            mWindowManager.removeView(mContentView);
        }
    }

    /**
     * 重置状态
     */
    private void resetState() {
        isShowing = false;
        mWindowParams.x = 0;
        mWindowParams.y = -mStatusBarHeight;
    }

    /**
     * build
     */
    public static class Builder {

        private Context context;
        private int imgRes;
        private String title;


        public Context getContext() {
            return context;
        }

        public Builder setContext(Context context) {
            this.context = context;
            return this;
        }

        public Builder setImgRes(int imgRes) {
            this.imgRes = imgRes;
            return this;
        }


        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }


        public CustomFloatButton build() {

            if (null == context)
                throw new IllegalArgumentException("the context is required.");
            if (instance == null) {
                instance = new CustomFloatButton(this);
            }
            return instance;
        }


    }
}
