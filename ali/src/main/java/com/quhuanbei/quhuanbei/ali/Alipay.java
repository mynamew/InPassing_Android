package com.quhuanbei.quhuanbei.ali;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.alipay.sdk.app.PayTask;

import android.app.Activity;
import android.text.TextUtils;

public class Alipay {

    private Activity activity;
    private PayCallback callback;


    public interface PayCallback {
        void onSuccess(String orderID);
        void onFailture(String orderID);
        void onUnknown(String orderID);
    }

    private Alipay(Activity act) {
        activity = act;
    }

    public static Alipay with(Activity act){
        return new Alipay(act);
    }

    public void pay(final PayItem item, PayCallback cb) {
        callback = cb;
        String orderInfo = getOrderInfo(item);

        /**
         * 特别注意，这里的签名逻辑需要放在服务端，切勿将私钥泄露在代码中！
         */
        String sign = sign(orderInfo, item);
        try {
            /**
             * 仅需对sign 做URL编码
             */
            sign = URLEncoder.encode(sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        /**
         * 完整的符合支付宝参数规范的订单信息
         */
        final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + getSignType();


        new Thread() {
            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(activity);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(payInfo, true);

                PayResult payResult = new PayResult(result);

                /**
                 * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                 * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                 * docType=1) 建议商户依赖异步通知
                 */
                //String resultInfo = payResult.getResult();// 同步返回需要验证的信息

//                9000	订单支付成功
//                8000	正在处理中，支付结果未知（有可能已经支付成功），请查询商户订单列表中订单的支付状态
//                4000	订单支付失败
//                5000	重复请求
//                6001	用户中途取消
//                6002	网络连接出错
//                6004	支付结果未知（有可能已经支付成功），请查询商户订单列表中订单的支付状态
//                其它	其它支付错误

                final String resultStatus = payResult.getResultStatus();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (TextUtils.equals(resultStatus, "9000")) {
                            if(callback!=null) callback.onSuccess(item.getOrderID());
                        } else if (TextUtils.equals(resultStatus, "8000")) {
                            if(callback!=null) callback.onUnknown(item.getOrderID());
                        } else {
                            if(callback!=null) callback.onFailture(item.getOrderID());
                        }

                    }
                });

            }
        }.start();
    }

    /**
     * create the order info. 创建订单信息
     */
    private String getOrderInfo(PayItem item) {

        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + item.getPartner() + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + item.getSeller() + "\"";

        // 商户网站唯一订单号
        //orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo() + "\"";
        orderInfo += "&out_trade_no=" + "\"" + item.getOrderID() + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + item.getTitle() + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + item.getDesc() + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + item.getMoney() + "\"";

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + item.getNotiURL() + "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"5m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"m.alipay.com\"";

        return orderInfo;
    }

//    /**
//     * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
//     */
//    private String getOutTradeNo() {
//        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
//        Date date = new Date();
//        String key = format.format(date);
//
//        Random r = new Random();
//        key = key + r.nextInt();
//        key = key.substring(0, 15);
//        return key;
//    }

    /**
     * sign the order info. 对订单信息进行签名
     *
     * @param content 待签名订单信息
     */
    private String sign(String content, PayItem item) {
        return SignUtils.sign(content, item.getRsakey());
    }

    /**
     * get the sign type we use. 获取签名方式
     */
    private String getSignType() {
        return "sign_type=\"RSA\"";
    }

}
