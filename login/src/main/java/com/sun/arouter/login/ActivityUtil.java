package com.sun.arouter.login;

import sun.com.arouter.ARouter;
import sun.com.arouter.IRouter;


public class ActivityUtil implements IRouter {
    @Override
    public void pushActivity() {
        ARouter.getInstance().putActivity("login/login",LoginActivity.class);
    }
}
