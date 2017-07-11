package timi.inpassing_android.base;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.ButterKnife;
import timi.inpassing_android.R;
import timi.inpassing_android.utils.Constants;
import timi.inpassing_android.utils.statusutils.StatusBarUtil;

/**
 * Created by timi on 2017/5/13.
 * ainm : Activity的基类
 */

public abstract class SuperActivity extends AutoLayoutActivity {
    public Activity currentActivity;
    public String TAG="";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayoutId());
        ButterKnife.bind(this);
        currentActivity = this;
        TAG=currentActivity.getClass().getSimpleName()+"_";
        //返回事件
        if (null != findViewById(R.id.iv_back)) {
            findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
        }
        //设置状态栏颜色 默认
        StatusBarUtil.setColor(this, Color.parseColor(Constants.StatusColorStr));
        initBundle(savedInstanceState);
        initView();
        initData();
    }

    /**
     * 设置标题的title
     *
     * @param title
     */
    public void setActivityTitle(String title) {
        if (null != findViewById(R.id.tv_title) && null != title && !TextUtils.isEmpty(title)) {
            //设置标题  如果titlebar存在的话
            TextView tvActivityTitle = (TextView) findViewById(R.id.tv_title);
            tvActivityTitle.setText(title);
        }
    }
    /**
     * 设置右侧的文字
     *
     * @param rightText
     */
    public void setRightText(String rightText, final View.OnClickListener listener) {
        if (null != findViewById(R.id.tv_right) && null != rightText && !TextUtils.isEmpty(rightText)) {
            //设置标题  如果titlebar存在的话
            TextView tvRightText= (TextView) findViewById(R.id.tv_right);
            tvRightText.setText(rightText);
            tvRightText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClick(view);
                }
            });
        }
    }
    /**
     * 设置右侧的图片及点击事件
     *
     * @param rightImgId 右侧图片的资源id
     */
    public void setRightImg(@DrawableRes int rightImgId, final View.OnClickListener listener) {
        if (null != findViewById(R.id.iv_right)) {
            //设置标题  如果titlebar存在的话
            ImageView ivRightImg= (ImageView) findViewById(R.id.iv_right);
            ivRightImg.setImageResource(rightImgId);
            ivRightImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClick(view);
                }
            });
        }
    }
    //设置布局id
    public abstract int setLayoutId();
    //初始化bundle
    public abstract void initBundle(Bundle savedInstanceState);
    //初始化View
    public abstract void initView();
    //初始化数据
    public abstract void initData();
}
