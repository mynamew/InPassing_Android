package timi.inpassing_android.config;


import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import timi.inpassing_android.R;
import timi.inpassing_android.base.SuperApplication;

/**
 * Created by Master on 2016/9/1.
 */
public class Gateway {

    static final String KEY_PREFIX = "code_";
    static final String KEY_STRING = "string";

    static public String getErrorCode(int code_int) {
        String key = KEY_PREFIX + code_int;
        String code = null;
        try {
            Class<?>[] classes = R.class.getClasses();
            for (Class<?> c : classes) {
                if (c.getSimpleName().equals(KEY_STRING)) {
                    Field field = c.getField(key);
                    int rid = (int) field.get(c);
                    code = SuperApplication.getInstance().getApplicationContext().getResources().getString(rid);
                }
            }
        } catch (Exception e) {
            code = null;
        }
        if (code == null)
            code = SuperApplication.getInstance().getApplicationContext().getResources().getString(R.string.code_undefined) + ":" + code_int;
        return code;
    }


    static public String getServerMethod(int md_int) {
        String method = SuperApplication.getInstance().getApplicationContext().getResources().getString(md_int);
        return method;
    }

    static public String getServerURL(String page) {
        String url = Deployment.SERVER_URL + page;
        return url;
    }

    static public String getServerURL(int page_int) {
        return getServerURL(SuperApplication.getInstance().getApplicationContext().getResources().getString(page_int));
    }

    //    static public String getPageURL(String page) {
//        return getPageURL(page, null);
//    }
    static public String getPageURL(String page, Map<String, String> param) {
        if (param == null) param = new HashMap<>();
        param.put("sendtime", System.currentTimeMillis() + "");
//        int userid = UserInfoUtils.getUserId();
//        param.put("myid", userid + "");
//        Log.e("ddsdsa", param.get("myid"));
        HashMap<String, String> hashMap = new HashMap<>();
//        hashMap.put("每日签到", SuperApplication.getInstance().getApplicationContext().getResources().getString(R.string.sign_html));
//        hashMap.put("新手指南",SuperApplication.getInstance().getApplicationContext().getResources().getString(R.string.newAdvice_html));
//        hashMap.put("常见问题",SuperApplication.getInstance().getApplicationContext().getResources().getString(R.string.Problem_html));
//        hashMap.put("会员权益",SuperApplication.getInstance().getApplicationContext().getResources().getString(R.string.member_html));
//        hashMap.put("用户协议说明",SuperApplication.getInstance().getApplicationContext().getResources().getString(R.string.userDeal_html));
//        hashMap.put("换客条款",SuperApplication.getInstance().getApplicationContext().getResources().getString(R.string.clause_html));
//        hashMap.put("换呗信用分",SuperApplication.getInstance().getApplicationContext().getResources().getString(R.string.creditScore_html));
//        hashMap.put("关于我们",SuperApplication.getInstance().getApplicationContext().getResources().getString(R.string.contactUs_html));
//        hashMap.put("换出技巧",SuperApplication.getInstance().getApplicationContext().getResources().getString(R.string.swapOutSkill_html));
//        hashMap.put("我的信用",SuperApplication.getInstance().getApplicationContext().getResources().getString(R.string.creditDial_html));
//        hashMap.put("关于成长值",SuperApplication.getInstance().getApplicationContext().getResources().getString(R.string.growUp_html));
//        hashMap.put("邀请码",SuperApplication.getInstance().getApplicationContext().getResources().getString(R.string.invitationCode_html));
//        hashMap.put("大转盘",SuperApplication.getInstance().getApplicationContext().getResources().getString(R.string.lottery));
//        hashMap.put("签到说明",SuperApplication.getInstance().getApplicationContext().getResources().getString(R.string.signCaption_html));
//        hashMap.put("大转盘活动规则",SuperApplication.getInstance().getApplicationContext().getResources().getString(R.string.turnRule_html));
//        hashMap.put("炫耀一下",SuperApplication.getInstance().getApplicationContext().getResources().getString(R.string.lotteryShare_html));
//        hashMap.put("邀请码分享",SuperApplication.getInstance().getApplicationContext().getResources().getString(R.string.invitationShare_html));
//        hashMap.put("免费白拿说明",SuperApplication.getInstance().getApplicationContext().getResources().getString(R.string.freeCaption_html));
//        hashMap.put("限时捡漏说明",SuperApplication.getInstance().getApplicationContext().getResources().getString(R.string.pickeCaption_html));
//        hashMap.put("捐赠说明",SuperApplication.getInstance().getApplicationContext().getResources().getString(R.string.donateCaption_html));
//        hashMap.put("公益专区说明",SuperApplication.getInstance().getApplicationContext().getResources().getString(R.string.pubwelfareArea_html));
//        hashMap.put("公益排行说明",SuperApplication.getInstance().getApplicationContext().getResources().getString(R.string.pubwelfareRank_html));
//        hashMap.put("提现说明",SuperApplication.getInstance().getApplicationContext().getResources().getString(R.string.withdrawalCaption_html));
//        hashMap.put("拍卖规则",SuperApplication.getInstance().getApplicationContext().getResources().getString(R.string.auctionRule_html));
//        hashMap.put("保障金详情",SuperApplication.getInstance().getApplicationContext().getResources().getString(R.string.guaranteeDetial_html));

        String url = Deployment.PAGE_URL + hashMap.get(page);
        if (url.contains("?")) {
            for (String key : param.keySet()) {
                url += "&" + key + "=" + param.get(key);
            }
        } else {
            boolean isFirst = true;
            for (String key : param.keySet()) {
                if (isFirst) {
                    isFirst = false;
                    url += "?" + key + "=" + param.get(key);
                } else {
                    url += "&" + key + "=" + param.get(key);
                }
            }
        }
        return url;
    }

    static public String getPageURL(int page_int, Map<String, String> param) {
        return getPageURL(SuperApplication.getInstance().getApplicationContext().getResources().getString(page_int), param);
    }

    static public String getPageURL(int page_int) {
        return getPageURL(SuperApplication.getInstance().getApplicationContext().getResources().getString(page_int), null);
    }

    static public String getFtpURL() {
        String url = Deployment.FTP_URL;
        return url;
    }

    static public String getFtpUserName() {
        String url = Deployment.FTP_USER_NAME;
        return url;
    }

    static public String getFtpPSW() {
        String url = Deployment.FTP_USER_PSW;
        return url;
    }

    static public String getFtpPort() {
        String url = Deployment.FTP_USER_PORT;
        return url;
    }

    //下单
    static public String getPayOrder() {
        String url = Deployment.PAY_ORDER;
        return url;
    }



    //微信下单
    static public String getWeChartPayOrder() {
        String url = Deployment.WECHART_PAY_ORDER;
        return url;
    }



    //获取Token
    static public String getServerToken() {
        String url = Deployment.SERVER_TOKEN;
        return url;
    }


}
