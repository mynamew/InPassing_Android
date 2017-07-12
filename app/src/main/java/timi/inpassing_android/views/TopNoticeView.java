package timi.inpassing_android.views;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import timi.inpassing_android.R;
import timi.inpassing_android.utils.glideuils.ImageLoaderUtis;


/**
 * 置顶的飘窗view
 *
 * @autor timi
 * create at 2017/4/26 13:22
 */
public class TopNoticeView {


    private static final int DISMISS_INTERVAL = 3000;

    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mWindowParams;
    private View mContentView;
    private Context mContext;
    private int mScreenWidth = 0;
    private int mStatusBarHeight = 0;

    private boolean isShowing = false;
    private CircleImageView mIvIcon;
    private TextView mTvTitle;
    private TextView mTvContent;
    private String userid;
    private String userName;

    private static TopNoticeView instance = null;

    @SuppressLint("WrongConstant")
    public TopNoticeView(Builder builder) {
        mContext = builder.getContext();

        mStatusBarHeight = getStatusBarHeight();
        mScreenWidth = mContext.getResources().getDisplayMetrics().widthPixels;

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
        //设置进入和退出动画
        mWindowParams.windowAnimations = R.style.TopNoticeAnim;
        mWindowParams.x = 0;
        mWindowParams.y = 400;
        setContentView(mContext, builder);
    }


    private static final int HIDE_WINDOW = 0;

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case HIDE_WINDOW:
                    instance=null;
                    dismiss();
                    break;
            }
            return false;
        }
    });

    /***
     * 设置内容视图
     *
     * @param context
     */
    private void setContentView(Context context, Builder builder) {
        mContentView = LayoutInflater.from(context).inflate(R.layout.layout_top_notice, null);
        mIvIcon = (CircleImageView) mContentView.findViewById(R.id.civ_head_line);
        mTvTitle = (TextView) mContentView.findViewById(R.id.tv_head_line_name);
        mTvContent = (TextView) mContentView.findViewById(R.id.tv_head_line_content);
        setIcon(builder.imgRes);
        setTitle(builder.title);
        setContent(builder.content);
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
     * 自动隐藏通知
     */
    private void autoDismiss() {
        mHandler.removeMessages(HIDE_WINDOW);
        mHandler.sendEmptyMessageDelayed(HIDE_WINDOW, DISMISS_INTERVAL);
    }

    /**
     * 获取状态栏的高度
     */
    public int getStatusBarHeight() {
        int height = 0;
        int resId = mContext.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resId > 0) {
            height = mContext.getResources().getDimensionPixelSize(resId);
        }
        return height;
    }


    @SuppressLint("WrongConstant")
    public void setIcon(String usericon) {
        if (!TextUtils.isEmpty(usericon)) {
            mIvIcon.setVisibility(View.VISIBLE);
            // TODO: 2017/7/12 加入图片加载器
            ImageLoaderUtis.getInstance().showImageFromUrl(mContext,R.mipmap.ic_launcher_round,usericon,mIvIcon,false);
        }
    }

    @SuppressLint("WrongConstant")
    public void setTitle(String title) {
        if (!TextUtils.isEmpty(title)) {
            mTvTitle.setVisibility(View.VISIBLE);
            mTvTitle.setText(title);
            userName = title;
        }
    }

    public void setUserId(String UserId) {
        if (!TextUtils.isEmpty(UserId)) {
            userid = UserId;
        }
    }

    public void setContent(String content) {
        mTvContent.setText(content);
    }

    public void setTime(long time) {
        SimpleDateFormat formatDateTime = new SimpleDateFormat("HH:mm", Locale.getDefault());
    }

    public static class Builder {

        private Context context;
        private String imgRes = "";
        private String title;
        private String content = "none";
        private long time = System.currentTimeMillis();
        private String userID = "";


        public Context getContext() {
            return context;
        }

        public Builder setContext(Context context) {
            this.context = context;
            return this;
        }

        public Builder setImgRes(String imgRes) {
            this.imgRes = imgRes;
            return this;
        }


        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }


        public Builder setContent(String content) {
            this.content = content;
            return this;
        }

        public TopNoticeView build() {

            if (null == context)
                throw new IllegalArgumentException("the context is required.");
            if (instance == null) {
                instance = new TopNoticeView(this);
            }
            return instance;
        }


    }

}
