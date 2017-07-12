package timi.inpassing_android.connection.message;

import org.greenrobot.eventbus.EventBus;

/** 
 * 注册 反注册的类
 * @autor timi
 * create at 2017/7/12 13:54
 */
public class MTMessage {

    static public void register(Object src){
      EventBus.getDefault().register(src);
    }

    static public void unregister(Object src){
        EventBus.getDefault().unregister(src);
    }

    static public void isRegister(Object src){
        EventBus.getDefault().isRegistered(src);
    }

    static public void post(MTEvent msg){
        EventBus.getDefault().post(msg);
    }

    static public void postSticky(MTEvent msg) {
        EventBus.getDefault().postSticky(msg);
    }

}
