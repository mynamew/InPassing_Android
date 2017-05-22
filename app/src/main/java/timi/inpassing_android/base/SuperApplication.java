package timi.inpassing_android.base;

import android.app.Application;
import android.support.multidex.MultiDexApplication;

/**
 * Created by timi on 2017/5/13.
 * ainm :  Application 基类
 */

public class SuperApplication extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
    }
}
