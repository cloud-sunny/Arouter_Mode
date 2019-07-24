package com.sun.mode;

import android.app.Application;

import sun.com.arouter.ARouter;

/**
 * @author sunxiaoyun
 * @description $
 * @time 19/7/18
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ARouter.getInstance().init(this);
    }
}
