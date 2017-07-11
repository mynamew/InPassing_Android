package timi.inpassing_android.config;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;


/**
 * Created by Master on 2016/9/1...
 * 环境配置类
 */
public class Deployment {
    /*******************************************
     * 服务器地址
     ***************************************************/
    static public String SERVER_URL = null;
    static public String PAGE_URL = null;
    static public String FTP_URL = null;
    static public String FTP_USER_NAME = null;
    static public String FTP_USER_PSW = null;
    static public String FTP_USER_PORT = null;
    static public String PAY_ORDER = null;
    static public String WECHART_PAY_ORDER = null;
    static public String SERVER_TOKEN = null;
    static public String SOCKET_SERVER = null;
    static public String SOCKET_PORT = null;
    /*******************************************
     * 支付宝相关
     ***************************************************/
    // 商户PID
    static public final String ALIPAY_PARTNER = "2088421973909392";
    // 商户APPID
    static public final String ALIPAY_APPID = "2016102802383981";
    // 商户收款账号
    static public final String ALIPAY_SELLER = "pay@huanbay.com";
    // 商户私钥，pkcs8格式
    static public final String ALIPAY_RSA_PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALT1arXbhxZQon8o\n" +
            "OH9cqbdbovohkcp1I1H3nwzHiwJuyhtITrfz/yLAtJ8nmzSvAfp3KhuuFN/O+GjQ\n" +
            "dw3dCmC/TM6Y1vnEgaAmEZvfHLuRfgOLRAPkXT+OYtSsw3zNQqqBCPE8szkmegmf\n" +
            "r3GieO9mn7k/86G+6D3NoU1btg8XAgMBAAECgYEAnVem6WlwFB8Yq6e1OEjB6QRs\n" +
            "uUDnGzqu815zlREL9ZmPchl1GM23UehtJNWzRiPFfJqQqnC7Bb9Yjwpz+kHv+qbz\n" +
            "QdAaLIpeuF3IVuDUQhrNeUz7YVbdxagytJU2SddaV9G9/N9qDQ6i4ciD9SeJSprr\n" +
            "TgMjxx3GiL+ixHZ4+IECQQDvWpxpqP7koPtPuy2mXetWp2LihqBv+KzUzLZdK/vU\n" +
            "A60EwtQ9Gk1Gv9jbYG5Vi8/HWDjhhvdU1dfnYNbfIrGpAkEAwYsnIrn7JEGrW6vp\n" +
            "IYObcDYHlHwOqY1PBfUDpNQRGm+MeNVgQPDh1/btVArhm+3eX1DOQfO3vbbwBlSS\n" +
            "HmyyvwJAObGYpCtVDVz0VrqifUiEMoX0Yu/aekLxvfV+O6UfdXJRYSVpLTVKzTPv\n" +
            "HKn4zmv98GoGg+4ZbfyBp0cDZBOjyQJARKxxaHsBR64ddVucTw35m2kwa1lRgovn\n" +
            "XD/B2Tx+DRlDiKR9kM1n9U9iio2J2+AdJUjwxqoJqkE9DaH4bt821QJAGjctWtaU\n" +
            "HIGPDWAYS5eiJyZkUmi+xxKEEdYtHjqm9/FO6oHmbc/2gxVThpktYDcIDzKcBSED\n" +
            "eZ/6NGHm9xhH4A==";

    /*******************************************
     * 微博相关
     ***************************************************/
    static public String WEIBO_APPID = null;

    /*******************************************
     * 微信相关
     ***************************************************/
    static public String WEIXIN_APPID = null;
    static public String WEIXIN_MCHID = null;
    static public String WEIXIN_APIKEY = null;
    static public String WEIXIN_PAYURL = null;

    static public void readManifestMetaData(Context context) {
        try {
            @SuppressLint("WrongConstant") ApplicationInfo info = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            if (info != null && info.metaData != null) {
                SERVER_TOKEN = info.metaData.get("SERVER_TOKEN").toString();
                SERVER_URL = info.metaData.get("SERVER_URL").toString();
                PAGE_URL = info.metaData.get("PAGE_URL").toString();
                FTP_URL = info.metaData.get("FTP_URL").toString();
                FTP_USER_NAME = info.metaData.get("FTP_USER_NAME").toString();
                FTP_USER_PSW = info.metaData.get("FTP_USER_PSW").toString();
                FTP_USER_PORT = info.metaData.get("FTP_USER_PORT").toString();
                PAY_ORDER = info.metaData.get("PAY_ORDER").toString();
                WECHART_PAY_ORDER = info.metaData.get("WECHART_PAY_ORDER").toString();


                WEIBO_APPID = info.metaData.get("WEIBO_APPID").toString();

                WEIXIN_APPID = info.metaData.get("WEIXIN_APPID").toString();
                WEIXIN_MCHID = info.metaData.get("WEIXIN_MCHID").toString();
                WEIXIN_APIKEY = info.metaData.get("WEIXIN_APIKEY").toString();
                WEIXIN_PAYURL = info.metaData.get("WEIXIN_PAYURL").toString();



                SOCKET_SERVER = info.metaData.get("SOCKET_SERVER").toString();
                SOCKET_PORT = info.metaData.get("SOCKET_PORT").toString();




            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.d("配置-->","读取gradle配置错误:" + e.getMessage());
        }
    }




}
