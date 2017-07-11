package timi.inpassing_android;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.library.viewspread.helper.BaseViewHelper;

import timi.inpassing_android.base.SuperActivity;

/**
 * 派单
 *
 * @autor timi
 * create at 2017/7/7 13:31
 */
public class PublishOrderActivity extends SuperActivity {
    //Activity切换动画的工具类
    private BaseViewHelper helper;

    @Override
    public int setLayoutId() {
        return R.layout.activity_publish_order;
    }

    @Override
    public void initBundle(Bundle savedInstanceState) {
        setActivityTitle("派单");
        startTranslation(findViewById(R.id.mcf_publish_order_bt));
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    /**
     * 开始切换动画
     *
     * @param v
     */
    public void startTranslation(View v) {
        helper = new BaseViewHelper
                .Builder(PublishOrderActivity.this)
                //.setEndView()//如果是两个切换的视图  这里设定最终显示的视图
                .setTranslationView(v)//设置过渡视图
                .isFullWindow(true)//是否全屏显示
                .isShowTransition(true)//是否显示过渡动画
                .setDimColor(Color.WHITE)//遮罩颜色
                .setDimAlpha(200)//遮罩透明度

                .create();//开始动画
    }

    /**
     * 返回键 捕捉
     */
    @Override
    public void onBackPressed() {
        if (helper != null && helper.isShowing()) {
            helper.backActivity(this);
        } else {
            super.onBackPressed();
        }
    }


}
