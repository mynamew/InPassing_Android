package timi.inpassing_android;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.library.viewspread.helper.BaseViewHelper;
import com.quhuanbei.quhuanbei.ali.Alipay;
import com.quhuanbei.quhuanbei.ali.PayItem;

import timi.inpassing_android.base.SuperActivity;
import timi.inpassing_android.config.Deployment;
import timi.inpassing_android.config.Gateway;
import timi.inpassing_android.utils.StringUtils;
import timi.inpassing_android.utils.ToastUtils;

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
     * @param view
     */
    public void WeChartPay(View view) {

    }

    public void AliPay(View view) {
        doAlipay();
    }
    /**
     * 支付宝支付
     */
    private void doAlipay() {
        PayItem item = new PayItem();
        item.setPartner(Deployment.ALIPAY_PARTNER).setSeller(Deployment.ALIPAY_SELLER).setRsakey(Deployment.ALIPAY_RSA_PRIVATE);
        item.setNotiURL(Gateway.getServerURL("") + Gateway.getPayOrder()).setOrderID( "123456").setMoney("1").setTitle("测试支付宝").setDesc("购买测试");

        Alipay.with(this).pay(item, new Alipay.PayCallback() {
                    @Override
                    public void onSuccess(String orderID) {
                        ToastUtils.showShort(PublishOrderActivity.this,"支付成功");
                    }

                    @Override
                    public void onFailture(String orderID) {
                        ToastUtils.showShort(PublishOrderActivity.this,"支付失败");
                    }

                    @Override
                    public void onUnknown(String orderID) {
                        ToastUtils.showShort(PublishOrderActivity.this,"结果未知，请刷新订单信息");
                    }
                }

        );
    }

}
