package timi.inpassing_android.base;

import android.support.multidex.MultiDexApplication;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import timi.inpassing_android.utils.SplashADWrapper;

/**
 * Created by timi on 2017/5/13.
 * ainm :  Application 基类
 */

public class SuperApplication extends MultiDexApplication {
    static SuperApplication instance;
    @Override
    public void onCreate() {
        super.onCreate();
        new SplashADWrapper(this);
        Logger.addLogAdapter(new AndroidLogAdapter());
    }
    static public SuperApplication getInstance() {
        return instance;
    }
}
