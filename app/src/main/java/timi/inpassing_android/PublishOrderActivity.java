package timi.inpassing_android;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.library.viewspread.helper.BaseViewHelper;
import com.quhuanbei.quhuanbei.weixin.pay.WXPay;

import timi.inpassing_android.base.SuperActivity;
import timi.inpassing_android.config.Gateway;
import timi.inpassing_android.utils.StringUtils;

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
    /**
     * 微信支付
     */
    private void dowxpay() {
        //微信支付
        WXPay.PayItem item = new WXPay.PayItem(String.valueOf("1"), "微信购买测试" ,   "", Gateway.getServerURL("") + Gateway.getWeChartPayOrder(), StringUtils.getWXTime(System.currentTimeMillis()), StringUtils.getWXTime(System.currentTimeMillis() + (5 * 60 * 1000)));
        WXPay.pay(PublishOrderActivity.this, item, new WXPay.PayListener() {
            @Override
            public void onPrepare() {
//                Log.e("MT", "支付-开始准备");
            }

            @Override
            public void onLaunchWX() {
//                Log.e("MT", "支付-跳转微信");
            }

            @SuppressLint("WrongConstant")
            @Override
            public void onSuccess() {
//                Log.e("MT", "支付-成功");
                Toast.makeText(PublishOrderActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
            }

            @SuppressLint("WrongConstant")
            @Override
            public void onFailture() {
//                Log.e("MT", "支付-失败");
                Toast.makeText(PublishOrderActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 微信支付
     * @param view
     */
    public void WeChartPay(View view) {
        dowxpay();
    }
}
