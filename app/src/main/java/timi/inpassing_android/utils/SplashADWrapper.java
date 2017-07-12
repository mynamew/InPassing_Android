package timi.inpassing_android.utils;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks2;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.orhanobut.logger.Logger;


/**
 * 用于切换到后台 是否显示广告
 *
 * @autor timi
 * create at 2017/7/11 14:15
 */

public class SplashADWrapper implements Application.ActivityLifecycleCallbacks, ComponentCallbacks2 {
    //是否 处于后台
    private boolean mIsBackground = false;
    //application
    private Application mApplication;

    /**
     * 注册监听器
     * @param application
     */
    public SplashADWrapper(Application application){
        mApplication=application;
        //如果版本 是在4.0 以上则注册
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.ICE_CREAM_SANDWICH){
           application.registerActivityLifecycleCallbacks(this);
           application.registerComponentCallbacks(this);
        }
    }

    /**
     * 解除注册
     * @param application
     */
    public void unregistCallBacks(Application application){
        application.unregisterActivityLifecycleCallbacks(this);
        application.unregisterComponentCallbacks(this);
    }
    //Activity 创建
    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    //Activity 开始
    @Override
    public void onActivityStarted(Activity activity) {

    }

    //Activity resume
    @Override
    public void onActivityResumed(Activity activity) {
        if(mIsBackground){
             mIsBackground=false;
            // TODO: 2017/7/11 显示广告页
            Logger.d("显示广告页");
        }
    }

    //Activity pause
    @Override
    public void onActivityPaused(Activity activity) {

    }

    //Activity stop
    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }

    @Override
    public void onTrimMemory(int level) {
        if (level == TRIM_MEMORY_UI_HIDDEN) {//表示 应用切换到后台
            mIsBackground = true;
            Logger.d( "切换到后台");
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {

    }

    @Override
    public void onLowMemory() {

    }
}
