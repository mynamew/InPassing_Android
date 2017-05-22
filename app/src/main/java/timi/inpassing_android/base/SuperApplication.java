package timi.inpassing_android.base;

import android.app.Application;
import android.support.multidex.MultiDexApplication;

/**
 * Created by timi on 2017/5/13.
 * ainm :  Application 基类
 */

public class SuperApplication extends MultiDexApplication {
    static SuperApplication instance;
    @Override
    public void onCreate() {
        super.onCreate();
    }
    static public SuperApplication getInstance() {
        return instance;
    }

}
